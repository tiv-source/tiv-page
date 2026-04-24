package de.tivsource.page.admin.actions.academy.registration;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.entity.administration.User;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.property.Property;
import de.tivsource.page.entity.registration.Registration;
import de.tivsource.page.exceptions.NoMailSessionCreatedException;
import de.tivsource.page.helper.MailSender;
import de.tivsource.page.helper.MailTemplate;
import de.tivsource.page.helper.pdf.CreateInvoicePDF;
import jakarta.mail.MessagingException;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="registrationPayError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/pay_error.jsp")
  }),
  @TilesDefinition(name="registrationPayForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/pay_form.jsp")
  })
})
public class PayAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2487163378178940692L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PayAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

    @InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

    private Registration registration;

    private Registration dbRegistration;

    private String redirection;

    /**
     * Benutzer der die Bestätigung verschickt.
     */
    private User user;

    private String number;

    private File file;

    @StrutsParameter(depth=3)
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getRedirection() {
        return redirection;
    }

    @Override
    @Actions({
        @Action(
            value = "pay",
            results = {
                @Result(name = "success",     type = "redirectAction", location = "%{redirection}"),
                @Result(name = "input",       type="tiles", location = "registrationPayForm"),
                @Result(name = "error",       type="tiles", location = "registrationPayError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
        // Hole die Benutzerdaten aus der Datenbank
        user = userDaoLocal.findByUsername(remoteUser);
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = simpleDateFormat.format(new Date());

    	if(registration != null) {
    	    dbRegistration = registrationDaoLocal.findByUuid(registration.getUuid());

    	    dbRegistration.setModified(new Date());
            dbRegistration.setModifiedBy(remoteUser);
            dbRegistration.setModifiedAddress(remoteAddress);

    	    if(dbRegistration.getStatus() == RegistrationStatus.CONFIRMED) {
                String prefix = getProperty("pdf.pay.document.number.prefix");
                number = prefix + getNumber().toString();
                dbRegistration.setInvoiceNumber(number);
    	        dbRegistration.setStatus(RegistrationStatus.PAID);
    	        registrationDaoLocal.merge(dbRegistration);
    	        // Zurück zu den zu bestätigenden Anmeldungen
    	        redirection = "confirmed.html";

                file = new File("/srv/tiv-page/documents/" + number + ".pdf");

                Calendar pdfCreationDate = Calendar.getInstance();
                Calendar pdfModificationDate = Calendar.getInstance();
                pdfCreationDate.setTime(new Date());
                pdfModificationDate.setTime(new Date());

                new CreateInvoicePDF(
                        file,
                        dbRegistration,
                        pdfCreationDate,
                        pdfModificationDate,
                        new File("/srv/tiv-page/templates/logo_small.png"),
                        new File("/srv/tiv-page/templates/fonts/arial.ttf"),
                        new File("/srv/tiv-page/templates/fonts/arialbd.ttf"),
                        generateDocumentInformation(),
                        generateDocumentData(date),
                        generateDocumentFooter(),
                        generateBankData()
                );

                SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
                String[] arguments = {
                        dbRegistration.getSalutation().getLongSalutation("de"),
                        dbRegistration.getFirstname(),
                        dbRegistration.getLastname(),
                        user.getFirstname() + " " + user.getLastname(),
                        dbRegistration.getEmail(),
                        dbRegistration.getTelephone(),
                        "https://academy.app.internal/public/registration/" + dbRegistration.getUserToken() + "/confirmForm.html",
                        "https://academy.app.internal/public/registration/" + dbRegistration.getUserToken() + "/cancelForm.html",
                        dbRegistration.getCourse().getName("de"),
                        dbRegistration.getCourse().getNumber(),
                        simpleDateFormatDate.format(dbRegistration.getCourse().getStartDate()),
                        simpleDateFormatTime.format(dbRegistration.getCourse().getStartDate())
                };

                MailSender mailSender = new MailSender(getProperties(), createMailTemplate());
                Boolean debugMailSession = getProperty("mail.course.predeclare.debug.session").equals("true") ? true : false;
                mailSender.createSession(debugMailSession);

                new Thread(new Runnable() {
                    public void run(){
                        try {
                            mailSender.send(arguments);
                        } catch (NoMailSessionCreatedException | MessagingException | UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return; // to stop the thread
                    }
                }).start();

    	        // TODO: Hier mus eine Bestätigungsmail mit der Info Geld erhalten erstellt werden
    	        // Es wird auch eine Rechnung (PDF) erstellt und verschickt werden 
    	        return SUCCESS;
    	    }// Ende if()
    	    return ERROR;
    	}
   		return ERROR;
    	
    }// Ende execute()

    private Properties getProperties() {
        LOGGER.info("getProperties() aufgerufen.");

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.transport.protocol", 
                getProperty("mail.transport.protocol"));
        props.put("mail.host", 
                getProperty("mail.host"));
        props.put("mail.port", 
                getProperty("mail.port"));
        props.put("mail.smtp.auth", 
                getProperty("mail.smtp.auth"));
        props.put("mail.smtp.tls", 
                getProperty("mail.smtp.tls"));
        props.put("mail.smtp.starttls.enable",
                getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.localhost", 
                getProperty("mail.smtp.localhost"));
        props.put("mail.user", 
                getProperty("mail.user"));
        props.put("mail.password", 
                getProperty("mail.password"));
        props.put("mail.mime.charset", 
                getProperty("mail.mime.charset"));
        props.put("mail.use8bit", 
                getProperty("mail.use8bit"));
        //TODO: Reihenfolge aufräumen
        props.put("mail.smtp.ssl.protocols", 
                getProperty("mail.smtp.ssl.protocols"));
        props.put("mail.smtp.ssl.trust", 
                getProperty("mail.smtp.ssl.trust"));
        return props;
    } // Ende getProperties()

    /**
     * Methode zum erstellen des Mail-Templates
     *
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws MessagingException
     */
    private MailTemplate createMailTemplate()
            throws UnsupportedEncodingException, IOException, MessagingException {
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setFrom(
                getProperty("mail.course.pay.from.personal"),
                getProperty("mail.course.pay.from.address")
                );
        mailTemplate.addTo(
                dbRegistration.getFirstname() + " " + dbRegistration.getLastname(),
                dbRegistration.getEmail()
                );
        mailTemplate.addBcc(
                getProperty("mail.course.pay.bcc.personal"),
                getProperty("mail.course.pay.bcc.address")
                );
        mailTemplate.addReplyTo(
                user.getFirstname() + " " + user.getLastname(),
                getProperty("mail.course.pay.replyTo.address")
                );
        if(getProperty("mail.course.pay.language").equals("en")) {
            mailTemplate.setSubject(
                    "Anmeldebestätigung für den Kurs " + 
                    dbRegistration.getCourse().getName("en") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")"
            );
            mailTemplate.setBody(getProperty("mail.course.pay.body.en"));
            mailTemplate.setHtml(getProperty("mail.course.pay.html.en"));
            mailTemplate.addImages(getProperty("mail.course.pay.images.en"));
        } else {
            mailTemplate.setSubject(
                    "Zahlungsbestätigung für den Kurs " + 
                    dbRegistration.getCourse().getName("de") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")"
            );
            mailTemplate.setBody(getProperty("mail.course.pay.body.de"));
            mailTemplate.setHtml(getProperty("mail.course.pay.html.de"));
            mailTemplate.addImages(getProperty("mail.course.pay.images.de"));
        }
        mailTemplate.addAttachment(file.getAbsolutePath(), number + ".pdf", "application/pdf");
        return mailTemplate;
    }

    private Integer getNumber() {
        Property property = propertyDaoLocal.findByKey("pdf.pay.document.number");
        Integer number = Integer.parseInt(property.getValue());
        number = number + 1;
        property.setValue(number.toString());
        propertyDaoLocal.merge(property);
        return number;
    }

    private String[] generateDocumentData(String date) {
        // DocumentData
        String[] documentData = new String[7];
        documentData[0] = getProperty("pdf.pay.document.data.addressfield.header");   // Adressfeld Überschrift
        documentData[1] = getProperty("pdf.pay.document.data.date.title");            // Überschrift Datum
        documentData[2] = date;                                                       // Datum
        documentData[3] = getProperty("pdf.confirmation.document.data.number.title"); // Überschrift Auftrags-Nummer
        documentData[4] = getProperty("pdf.pay.document.data.number.title");          // Überschrift Rechnungs-Nummer
        documentData[5] = getProperty("pdf.pay.document.data.page.title");            // Überschrift Seitennummer
        documentData[6] = getProperty("pdf.pay.document.data.page");                  // Seitennummer
        return documentData;
    }

    private String[] generateDocumentInformation() {
        // DocumentInformation
        String[] documentInformation = new String[6];
        documentInformation[0] = getProperty("pdf.pay.document.information.author");  // Author
        documentInformation[1] = getProperty("pdf.pay.document.information.creator"); // Creator
        documentInformation[2] = getProperty("pdf.pay.document.information.keywords");// Keywords
        documentInformation[3] = getProperty("pdf.pay.document.information.producer");// Producer
        documentInformation[4] = getProperty("pdf.pay.document.information.subject"); // Subject
        documentInformation[5] = getProperty("pdf.pay.document.information.title");   // Title
        return documentInformation;
    }

    private String[][] generateDocumentFooter() {
        // DocumentFooter
        String[][] documentFooter = new String[5][4];
        documentFooter[0][0] = getProperty("pdf.pay.document.footer.block1.field1");// Block 1 Feld 1
        documentFooter[0][1] = getProperty("pdf.pay.document.footer.block1.field2");// Block 1 Feld 2
        documentFooter[0][2] = getProperty("pdf.pay.document.footer.block1.field3");// Block 1 Feld 3
        documentFooter[0][3] = getProperty("pdf.pay.document.footer.block1.field4");// Block 1 Feld 4
        documentFooter[1][0] = getProperty("pdf.pay.document.footer.block2.field1");// Block 2 Feld 1
        documentFooter[1][1] = getProperty("pdf.pay.document.footer.block2.field2");// Block 2 Feld 2
        documentFooter[1][2] = getProperty("pdf.pay.document.footer.block2.field3");// Block 2 Feld 3
        documentFooter[1][3] = getProperty("pdf.pay.document.footer.block2.field4");// Block 2 Feld 4
        documentFooter[2][0] = getProperty("pdf.pay.document.footer.block3.field1");// Block 3 Feld 1
        documentFooter[2][1] = getProperty("pdf.pay.document.footer.block3.field2");// Block 3 Feld 2
        documentFooter[2][2] = getProperty("pdf.pay.document.footer.block3.field3");// Block 3 Feld 3
        documentFooter[2][3] = getProperty("pdf.pay.document.footer.block3.field4");// Block 3 Feld 4
        documentFooter[3][0] = getProperty("pdf.pay.document.footer.block4.field1");// Block 4 Feld 1
        documentFooter[3][1] = getProperty("pdf.pay.document.footer.block4.field2");// Block 4 Feld 2
        documentFooter[3][2] = getProperty("pdf.pay.document.footer.block4.field3");// Block 4 Feld 3
        documentFooter[3][3] = getProperty("pdf.pay.document.footer.block4.field4");// Block 4 Feld 4
        documentFooter[4][0] = getProperty("pdf.pay.document.footer.block5.field1");// Block 5 Feld 1
        documentFooter[4][1] = getProperty("pdf.pay.document.footer.block5.field2");// Block 5 Feld 2
        documentFooter[4][2] = getProperty("pdf.pay.document.footer.block5.field3");// Block 5 Feld 3
        documentFooter[4][3] = getProperty("pdf.pay.document.footer.block5.field4");// Block 5 Feld 4
        return documentFooter;
    }

    private String[] generateBankData() {
        // BankData
        String[] bankData = new String[10];
        bankData[0] = getProperty("pdf.confirmation.document.bankdata.title");          // Title
        bankData[1] = getProperty("pdf.confirmation.document.bankdata.iban.title");     // Überschrift IBAN
        bankData[2] = getProperty("pdf.confirmation.document.bankdata.iban");           // IBAN
        bankData[3] = getProperty("pdf.confirmation.document.bankdata.bic.title");      // Überschift BIC
        bankData[4] = getProperty("pdf.confirmation.document.bankdata.bic");            // BIC
        bankData[5] = getProperty("pdf.confirmation.document.bankdata.bank.title");     // Überschrift Bank
        bankData[6] = getProperty("pdf.confirmation.document.bankdata.bank");           // Bank
        bankData[7] = getProperty("pdf.confirmation.document.bankdata.recipient.title");// Empfänger überschrift
        bankData[8] = getProperty("pdf.confirmation.document.bankdata.recipient");      // Empfänger
        bankData[9] = getProperty("pdf.confirmation.document.bankdata.purpose");        // Überschrift Verwendungszweck
        return bankData;
    }

}// Ende class
