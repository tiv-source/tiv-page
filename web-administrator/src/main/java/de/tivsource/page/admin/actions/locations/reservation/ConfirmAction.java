package de.tivsource.page.admin.actions.locations.reservation;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.administration.User;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.exceptions.NoMailSessionCreatedException;
import de.tivsource.page.helper.MailSender;
import de.tivsource.page.helper.MailTemplate;
import de.tivsource.page.helper.pdf.CreateReservationPDF;
import jakarta.mail.MessagingException;

/**
 * 
 * @author Marc Michele
 *
 */
public class ConfirmAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7464881886808879895L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ConfirmAction.class);

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;
    
    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    /**
     * Benutzer der die Bestätigung verschickt.
     */
    private User user;

    /**
     * Reservierung für die die Bestätigung verschickt werden soll.
     */
    private Reservation dbReservation;

    /**
     * PDF-Datei/Bestätigung 
     */
    private File pdfFile;
    
    private String redirect;

    private Reservation reservation;

    @StrutsParameter(depth=1)
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getRedirect() {
        return redirect;
    }

    @Override
    @Actions({
        @Action(
        		value = "confirm", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "queue.html"),
        				@Result(name = "input", type = "tiles", location = "reservationConfirmForm"),
        				@Result(name = "error", type = "tiles", location = "reservationConfirmError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
        // Hole die Benutzerdaten aus der Datenbank
        user = userDaoLocal.findByUsername(remoteUser);

        
    	if(reservation != null) {
    	    // Lade die Reservierung und setze Attribute
    	    dbReservation = reservationDaoLocal.findByUuid(reservation.getUuid());
    	    dbReservation.setConfirmed(true);
    	    dbReservation.setConfirmedAddress(remoteAddress);
    	    dbReservation.setConfirmedDate(new Date());
    	    dbReservation.setConfirmedBy(remoteUser);
    	    reservationDaoLocal.merge(dbReservation);

    	    LOGGER.info("UUID des Events: " + dbReservation.getEvent().getUuid());
    	    LOGGER.info("UUID der Location: " + dbReservation.getEvent().getLocation().getUuid());

            // Erzeuge das PDF-Bestätigung
            pdfFile = new File("/tmp/" + reservation.getUuid() + ".pdf");
            new CreateReservationPDF(
                    pdfFile,
                    dbReservation,
                    new File(getProperty("mail.confirmreservation.logo.path")),
                    new File(getProperty("mail.confirmreservation.ads.path")),
                    new File(getProperty("mail.confirmreservation.font.path"))
            );

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy ");
            String[] arguments = {
                    dbReservation.getGender() ? "Sehr geehrte Frau" : "Sehr geehrter Herr",
                    dbReservation.getFirstname(), 
                    dbReservation.getLastname(),
                    user.getFirstname() + " " + user.getLastname(),
                    dbReservation.getEmail(), 
                    dbReservation.getTelephone(),
                    simpleDateFormat.format(dbReservation.getTime()),
                    dbReservation.getQuantity().toString(),
                    dbReservation.getWishes(),
                    dbReservation.getWishes().replace("\n", "<br/>")
                    };

            MailSender mailSender = new MailSender(getProperties(), createMailTemplate());
            Boolean debugMailSession = getProperty("mail.confirmreservation.debug.session").equals("true") ? true : false;
            mailSender.createSession(debugMailSession);
            
            new Thread(new Runnable() {
                public void run(){
                    try {
                        mailSender.send(arguments);
                        pdfFile.delete();
                    } catch (NoMailSessionCreatedException | MessagingException | UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return; // to stop the thread
                }
            }).start();
            
    	    /*
    	    
    	    // Hole das entsprechnde Bild aus der Datenbank
    	    String urlAds = HTDOCS + pictureDaoLocal.findByUuid(
    	    			propertyDaoLocal.findByKey("reservation.ads").getValue()
    	    		).getPictureUrl(UrlType.NORMAL.toString());
    	    
    	    URL templatePath = new URL(propertyDaoLocal.findByKey("reservation.confirm.template.path").getValue());
    	    URL logoPath = new URL(propertyDaoLocal.findByKey("reservation.confirm.logo.path").getValue());
    	    URL fontPath = new URL(propertyDaoLocal.findByKey("reservation.confirm.font.path").getValue());

    	    
    	    LOGGER.info("Pfad der Logo Datei " + logoPath.getFile());
    	    LOGGER.info("Pfad der Ads  Datei " + urlAds);
    	    LOGGER.info("Pfad der Font Datei " + fontPath.getFile());
    	    
    	    ReservationMail reservationMail = new ReservationMail(
                    propertyDaoLocal.findByKey("mail.user").getValue(),
                    propertyDaoLocal.findByKey("mail.password").getValue(),
                    templatePath,
                    dbReservation,
                    new File(logoPath.getFile()),
                    new File(urlAds),
                    getProperties(),
                    new File(fontPath.getFile()),
                    propertyDaoLocal.findByKey("reservation.formAddress").getValue(),
                    propertyDaoLocal.findByKey("reservation.fromName").getValue(),
                    propertyDaoLocal.findByKey("reservation.replyToAddress").getValue(),
                    user.getFirstname() + " " + user.getLastname(),
                    propertyDaoLocal.findByKey("reservation.bccAddress").getValue()
    	    );

            new Thread(new Runnable() {
                public void run(){
                    try {
                        reservationMail.send();
                    } catch (COSVisitorException | MessagingException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return; // to stop the thread
                }
            }).start();
*/
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    public List<Date> getTimes() {
        LOGGER.info("getTimes() aufgerufen.");

        List<Date> times = new ArrayList<Date>();

        // Anfangs Punkt der Zeitreihe
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(reservation.getEvent().getBeginning());
        times.add(calendarStart.getTime());
                
        // Endpunkt der Zeitreihe 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservation.getEvent().getEnding());
        calendar.add(Calendar.MINUTE, -30);
        Date end = calendar.getTime();

        // Datum mit dem gerechnet wird
        Date time = reservation.getEvent().getBeginning();
        while (time.before(end)) {
            Calendar calendarTime = Calendar.getInstance();
            calendarTime.setTime(time);
            calendarTime.add(Calendar.MINUTE, 15);
            time = calendarTime.getTime();
            times.add(time);
        }

        LOGGER.info("Inhalt der Liste: " + times.size());

        return times;
    }

    private Properties getProperties() {
        LOGGER.info("getProperties() aufgerufen.");

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.transport.protocol", 
                propertyDaoLocal.findByKey("mail.transport.protocol").getValue());
        props.put("mail.host", 
                propertyDaoLocal.findByKey("mail.host").getValue());
        props.put("mail.port", 
                propertyDaoLocal.findByKey("mail.port").getValue());
        props.put("mail.smtp.auth", 
                propertyDaoLocal.findByKey("mail.smtp.auth").getValue());
        props.put("mail.smtp.tls", 
                propertyDaoLocal.findByKey("mail.smtp.tls").getValue());
        props.put("mail.smtp.starttls.enable",
                propertyDaoLocal.findByKey("mail.smtp.starttls.enable").getValue());
        props.put("mail.smtp.localhost", 
                propertyDaoLocal.findByKey("mail.smtp.localhost").getValue());
        props.put("mail.user", 
                propertyDaoLocal.findByKey("mail.user").getValue());
        props.put("mail.password", 
                propertyDaoLocal.findByKey("mail.password").getValue());
        props.put("mail.mime.charset", 
                propertyDaoLocal.findByKey("mail.mime.charset").getValue());
        props.put("mail.use8bit", 
                propertyDaoLocal.findByKey("mail.use8bit").getValue());
        //TODO: Reihenfolge aufräumen
        props.put("mail.smtp.ssl.protocols", 
                propertyDaoLocal.findByKey("mail.smtp.ssl.protocols").getValue());
        props.put("mail.smtp.ssl.trust", 
                propertyDaoLocal.findByKey("mail.smtp.ssl.trust").getValue());
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
                getProperty("mail.confirmreservation.from.personal"),
                getProperty("mail.confirmreservation.from.address")
                );
        mailTemplate.addTo(
                dbReservation.getFirstname() + " " + dbReservation.getLastname(),
                dbReservation.getEmail()
                );
        mailTemplate.addBcc(
                getProperty("mail.confirmreservation.bcc.personal"),
                getProperty("mail.confirmreservation.bcc.address")
                );
        mailTemplate.addReplyTo(
                user.getFirstname() + " " + user.getLastname(),
                getProperty("mail.confirmreservation.replyTo.address")
                );
        mailTemplate.addAttachment(
                pdfFile.getAbsolutePath(),
                getProperty("mail.confirmreservation.pdf.name"),
                "application/pdf"
                );
        if(getProperty("mail.contactform.language").equals("en")) {
            mailTemplate.setSubject(
                    "Reservierungsbestätigung für " + 
                    dbReservation.getEvent().getLocation().getName("en")
                    );
            mailTemplate.setBody(getProperty("mail.confirmreservation.body.en"));
            mailTemplate.setHtml(getProperty("mail.confirmreservation.html.en"));
            mailTemplate.addImages(getProperty("mail.confirmreservation.images.en"));
        } else {
            mailTemplate.setSubject(
                    "Reservierungsbestätigung für " + 
                    dbReservation.getEvent().getLocation().getName("de")
                    );
            mailTemplate.setBody(getProperty("mail.confirmreservation.body.de"));
            mailTemplate.setHtml(getProperty("mail.confirmreservation.html.de"));
            mailTemplate.addImages(getProperty("mail.confirmreservation.images.de"));
        }
        return mailTemplate;
    }

}// Ende class
