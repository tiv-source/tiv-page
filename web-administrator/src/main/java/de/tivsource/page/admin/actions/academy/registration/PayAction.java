package de.tivsource.page.admin.actions.academy.registration;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.mustangproject.BankDetails;
import org.mustangproject.Contact;
import org.mustangproject.Invoice;
import org.mustangproject.Item;
import org.mustangproject.LegalOrganisation;
import org.mustangproject.Product;
import org.mustangproject.TradeParty;
import org.mustangproject.ZUGFeRD.Profiles;
import org.mustangproject.ZUGFeRD.ZUGFeRDExporterFromA1;

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

    private File epdf;

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
    	        dbRegistration.setFullyPaid(registration.getFullyPaid());
    	        if(registration.getFullyPaid()) {
    	            // Wenn vollständig bezahlt wurde dann wird der Wert auf 0.00 gesetzt 
    	            dbRegistration.setPartialPayment(new BigDecimal("0.00"));
    	        } else {
    	            // Wenn nicht vollständig bezahlt wurde dann wird der angegbene Wert übernommen
    	            dbRegistration.setPartialPayment(registration.getPartialPayment());
    	        }
    	        
    	        registrationDaoLocal.merge(dbRegistration);
    	        // Zurück zu den zu bestätigenden Anmeldungen
    	        redirection = "confirmed.html";

                file = new File("/srv/tiv-page/documents/" + number + ".pdf");
                epdf = new File("/srv/tiv-page/documents/" + number + "_e.pdf");

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
                
                generateFacturaX(dbRegistration);

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
                    "Zahlungsbestätigung für den Kurs \"" + 
                    dbRegistration.getCourse().getName("de") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")\""
            );
            mailTemplate.setBody(getProperty("mail.course.pay.body.en"));
            mailTemplate.setHtml(getProperty("mail.course.pay.html.en"));
            mailTemplate.addImages(getProperty("mail.course.pay.images.en"));
        } else {
            mailTemplate.setSubject(
                    "Zahlungsbestätigung für den Kurs \"" + 
                    dbRegistration.getCourse().getName("de") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")\""
            );
            mailTemplate.setBody(getProperty("mail.course.pay.body.de"));
            mailTemplate.setHtml(getProperty("mail.course.pay.html.de"));
            mailTemplate.addImages(getProperty("mail.course.pay.images.de"));
        }
        mailTemplate.addAttachment(epdf.getAbsolutePath(), number + ".pdf", "application/pdf");
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

    private void generateFacturaX(Registration registration) {

        Contact sellerContact = new Contact();
        if(getProperty("zugferd.seller.user.name.from.userobject").equals("true")) {
            // Name des Benutzers der die Rechnung erstellt hat
            // wird aus dem User Objekt genommen
            sellerContact.setName(user.getFirstname() + " " + user.getLastname());
        } else {
            // Generische Name des Benutzer 
            // wird aus den Einstellungen genommen
            sellerContact.setEMail(getProperty("zugferd.seller.user.name"));
        }

        // Telefonnummer des Benutzers der die Rechnung erstellt hat
        // wird aus den Einstellungen genommen
        sellerContact.setPhone(getProperty("zugferd.seller.user.phone"));

        if(getProperty("zugferd.seller.user.mailaddress.from.userobject").equals("true")) {
            // Mail-Adresse des Benutzer der die Rechnung erstellt hat
            // wird aus dem User-Objekt genommen
            sellerContact.setEMail(user.getEmail());
        } else {
            // Mail-Adresse des Benutzer generische Adresse
            // wird aus den Einstellungen genommen
            sellerContact.setEMail(getProperty("zugferd.seller.user.mailaddress.generic"));
        }

        // Daten des Verkäufers
        TradeParty seller = new TradeParty(
                getProperty("zugferd.seller.name"), 
                getProperty("zugferd.seller.street"), 
                getProperty("zugferd.seller.zip"), 
                getProperty("zugferd.seller.city"), 
                getProperty("zugferd.seller.country")
                );

        // Kontakt des Verkäufers hinzufügen
        seller.setContact(sellerContact);
        // Steuerdaten des Verkäufers
        seller.setTaxID(getProperty("zugferd.seller.taxid"));
        seller.setVATID(getProperty("zugferd.seller.vatid"));
        // Adresse unter der die Buchhaltung zu erreichen ist
        seller.setEmail(getProperty("zugferd.seller.mailaddress"));

        if(getProperty("zugferd.seller.globalid.enabled").equals("true")) {
            // Globale ID des Verkäufers
            seller.setGlobalID(getProperty("zugferd.seller.globalid"));
            // Schema der globalen ID
            // 0060 = D-U-N-S Nummer (Dun & Bradstreet)
            // 0088 – GLN (Global Location Number / ehemals ILN)
            // 0177 – Odette ID (Odette International Limited)
            // 0204 – Die Leitweg-ID (Nur für den Peppol-Netzwerk-Versand)
            seller.setGlobalIDScheme(getProperty("zugferd.seller.globalid.scheme"));
        }

        if(getProperty("zugferd.seller.legal.organisation.enabled").equals("true")) {
            // LegalOrganisation-Objekt
            // mit der Handelsregisternummer (z. B. HRB 12345)
            // und dem ISO-Schema für das deutsche Handelsregister "0021"
            LegalOrganisation legalOrganisation = 
                    new LegalOrganisation(
                            getProperty("zugferd.seller.legal.organisation.id"),
                            getProperty("zugferd.seller.legal.organisation.scheme")
                            );
            seller.setLegalOrganisation(legalOrganisation);
        }

        // Bankverbindung erstellen
        BankDetails bank = new BankDetails(
                getProperty("zugferd.seller.bank.iban"),
                getProperty("zugferd.seller.bank.bic")
                );
        bank.setAccountName(getProperty("zugferd.seller.bank.name"));
        bank.setPaymentMeansCode("58");
        // Bankverbindung hinzufügen
        seller.addBankDetails(bank);

        Contact recipientContact = new Contact();
        // Name des Kunden der die Rechnung erhält
        recipientContact.setName(registration.getFirstname() + " " + registration.getLastname());
        if(registration.getTelephone() != null && registration.getTelephone().length() > 0) {
            // Telefonnummer des Kunden der die Rechnung erhält
            recipientContact.setPhone(registration.getTelephone());
        }
        // Mail-Adresse unter des Kunden der die Rechnung erhält
        recipientContact.setEMail(registration.getEmail());

        // Daten des Käufers
        TradeParty recipient = new TradeParty(
                registration.getConsumer() ? 
                        registration.getFirstname() + " " + registration.getLastname() : registration.getFirm(),
                registration.getStreet(), 
                registration.getZip(), 
                registration.getCity(), 
                registration.getCountry().getAlpha2()
                ).setEmail(registration.getEmail());

        recipient.setContact(recipientContact);

        // Rechnungsdaten
        Invoice invoice = new Invoice()
                .setNumber(registration.getInvoiceNumber())
                .setIssueDate(new Date())
                .setDueDate(new Date())
                .setSender(seller)
                .setRecipient(recipient)
                .setCurrency(getProperty("zugferd.invoice.currency"));

        // Lieferdatum
        invoice.setDeliveryDate(registration.getCourse().getStartDate());
        // Auftragsnummer
        invoice.setSellerOrderReferencedDocumentID(registration.getOrderConfirmationNumber());
        // Bestellnummer
        //invoice.setBuyerOrderReferencedDocumentID(registration.getOrderConfirmationNumber());

        invoice.setPaymentTermDescription(
                getProperty("zugferd.invoice.term")
                );

        if(registration.getFullyPaid()) {
            // Da die Rechnung schon vollständig bezahlt wurde, setzen wir
            // hier als Betrag die Gesamtsummer ein.
            invoice.setTotalPrepaidAmount(registration.sum());
        } else {
            // Da die Rechnung nicht vollständig bezahlt wurde, setzen wir
            // hier den Teilbetrag ein.
            invoice.setTotalPrepaidAmount(registration.getPartialPayment());
        }

        // Faktor berechnen (z.B. 1.19)
        BigDecimal faktor1 = BigDecimal.ONE.add(
                registration.getCourse().getPriceTax().divide(new BigDecimal("100"))
                );
        // Nettopreis berechnen und auf 2 Nachkommastellen runden
        BigDecimal netto1 = registration.getCourse().getPrice().divide(faktor1, 2, RoundingMode.HALF_UP);
        
        Product product1 = new Product()
                .setName(registration.getCourse().getCourseDocumentDescription())
                .setSellerAssignedID(registration.getCourse().getNumber())
                // .setUnit("C62") // "C62" ist der offizielle UN-Code für "Stück"
                .setUnit("E48") // "E48" steht für Pauschale / Serviceeinheit
                .setVATPercent(registration.getCourse().getPriceTax()); // Mehrwertsteuersatz
        
        // Item(Produkt, Einzelpreis brutto/netto, Menge)
        Item item1 = new Item(product1, netto1, new BigDecimal("1"));
        item1.setBasisQuantity(BigDecimal.ONE);
        item1.setLineTotalAmount(registration.getCourse().getPrice()); 
        invoice.addItem(item1);

        // Total Netto
        BigDecimal totalNet = netto1;
        
        if(registration.getListOfMaterials()) {
            Product product2 = new Product()
                    .setName(registration.getCourse().getMaterialDocumentDescription())
                    .setUnit("C62") // "C62" ist der offizielle UN-Code für "Stück"
                    //.setUnit("E48") // Steht für Pauschale / Serviceeinheit
                    .setVATPercent(registration.getCourse().getMaterialTax()); // Mehrwertsteuersatz

            // Faktor berechnen (z.B. 1.19)
            BigDecimal faktor2 = BigDecimal.ONE.add(
                    registration.getCourse().getMaterialTax().divide(new BigDecimal("100"))
                    );
            // Nettopreis berechnen und auf 2 Nachkommastellen runden
            BigDecimal netto2 = registration.getCourse().getMaterialPrice()
                    .divide(faktor2, 2, RoundingMode.HALF_UP);
            
            // Item(Produkt, Einzelpreis brutto/netto, Menge)
            Item item2 = new Item(product2, netto2, new BigDecimal("1")); 
            item2.setBasisQuantity(BigDecimal.ONE);
            item2.setLineTotalAmount(registration.getCourse().getMaterialPrice()); 
            invoice.addItem(item2);
            totalNet = netto1.add(netto2);
        }

        BigDecimal expectedVatTotal = registration.tax();
        BigDecimal mustangVatTotal = totalNet.multiply(new BigDecimal("0.19")).setScale(2, RoundingMode.HALF_UP);

        BigDecimal roundingDifference = expectedVatTotal.subtract(mustangVatTotal);
        // Nur anwenden, wenn es eine Rundungsdifferenz gibt (ungleich Null)
        if (roundingDifference.compareTo(BigDecimal.ZERO) != 0) {
            invoice.setRoundingAmount(roundingDifference);
        }
        
        try (ZUGFeRDExporterFromA1 exporter = new ZUGFeRDExporterFromA1()) {
            exporter.setProducer(getProperty("zugferd.producer")); 
            // Validierung abschalten
            exporter.ignorePDFAErrors();
            // Konfiguration für das Standard-ZUGFeRD-Profil (EN16931 / Comfort)
            exporter.setProfile(Profiles.getByName(getProperty("zugferd.profile"))); 
            // Uhrsprungs PDF
            exporter.load(file.getAbsolutePath());
            // XML-Daten
            exporter.setTransaction(invoice);
            // Zeil PDF
            exporter.export(epdf.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info("ZUGFeRD-Rechnung erfolgreich erstellt!");

    }

}// Ende class
