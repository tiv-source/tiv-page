package de.tivsource.page.user.actions.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.helper.EmailSender;
import de.tivsource.page.helper.EmailTemplate;
import de.tivsource.page.user.actions.EmptyAction;

public class ReservationAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6904998219814327383L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(ReservationAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

	private Reservation reservation;

	private Page page;

	private Event event;
	
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Event getEvent() {
        setUpEvent();
        return event;
    }

    @Override
    public Page getPage() {
        if(page == null) {
            setUpPage();
        }
        return page;
    }// Ende getPage()

    public List<Date> getTimes() {
        LOGGER.info("getTimes() aufgerufen.");
        setUpEvent();

        List<Date> times = new ArrayList<Date>();

        // Anfangs Punkt der Zeitreihe
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(event.getBeginning());
        times.add(calendarStart.getTime());
                
        // Endpunkt der Zeitreihe 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getEnding());
        calendar.add(Calendar.MINUTE, -30);
        Date end = calendar.getTime();

        // Datum mit dem gerechnet wird
        Date time = event.getBeginning();
        while (time.before(end)) {
            Calendar calendarTime = Calendar.getInstance();
            calendarTime.setTime(time);
            calendarTime.add(Calendar.MINUTE, 15);
            time = calendarTime.getTime();
            times.add(time);
        }

        LOGGER.info("inhalt der Liste: " + times.size());

        return times;
    }
    
    @Actions({
        @Action(
        		value = "reserve", 
        		results = { 
        				@Result(name = "success",  type = "tiles", location = "eventSuccess"), 
        				@Result(name = "input",    type = "tiles", location = "event"),
        				@Result(name = "deadline", type = "tiles", location = "eventDeadline"),
        				@Result(name = "error",    type = "redirectAction", location = "index.html", params={"namespace", "/"})
        				}
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();

    	setUpEvent();
        // Setze Daten in ein Page Objekt.
        setUpPage();
        Date now = new Date();
        if(event.getDeadline().after(now)) {

            // Speichere Message Objekt
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
            reservation.setUuid(UUID.randomUUID().toString());
            reservation.setConfirmed(false);
            reservation.setIp(remoteAddress);
            reservation.setCreated(new Date());
            reservationDaoLocal.merge(reservation);
            
            sendMail();

            return SUCCESS;
        } else if (event.getBeginning().before(now)) {
            return ERROR;
        } else {
            return "deadline";
        }

	}

    private void setUpPage() {
        if(event == null) {
            event = eventDaoLocal.findByUuid(reservation.getEvent().getUuid());
        }
        page = new Page();
        page.setTechnical(event.getName(Language.DE));
        page.setDescriptionMap(event.getDescriptionMap());
        page.getDescriptionMap().get(Language.DE).setName("Reservierung erfolgreich - " + event.getLocation().getName(Language.DE) + " - " + page.getDescriptionMap().get(Language.DE).getName());
        // TODO: Hier müsste das noch lokalisiert werden
        page.getDescriptionMap().get(Language.EN).setName("Reservierung erfolgreich - " + event.getLocation().getName(Language.EN) + " - " + page.getDescriptionMap().get(Language.EN).getName());
    }

    private void setUpEvent() {
        if(event == null) {
            event = eventDaoLocal.findByUuid(reservation.getEvent().getUuid());
        }
    }


    private void sendMail() {
        LOGGER.info("sendMail() aufgerufen.");
        
        // Datums Formatierung
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy ");
        
        javax.mail.Authenticator auth = new javax.mail.Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        propertyDaoLocal.findByKey("mail.user").getValue(),
                        propertyDaoLocal.findByKey("mail.password").getValue());
            }
        };
        
        InputStream template;

        try {
            URL templatePath = this.getClass().getClassLoader().getResource("template_reservation.xml");
            LOGGER.info("Pfad zur template Datei: " + templatePath);
            template = templatePath.openStream();
            LOGGER.info("Template eingelesen");
            Object notification = EmailTemplate.getEmailTemplate(template);
            LOGGER.info("Template eingelesen");

            // Get session
            Session session = Session.getDefaultInstance(getProperties(), auth);
            session.setDebug(true);
            
            EmailSender sendIt = new EmailSender();
            String[] argu = {
                    reservation.getGender() ? "Frau" : "Herr",
                    reservation.getFirstname(), 
                    reservation.getLastname(), 
                    reservation.getEmail(), 
                    reservation.getTelephone(),
                    reservation.getEvent().getName("de") + " im " + reservation.getEvent().getLocation().getName("de"),
                    simpleDateFormat.format(reservation.getTime()),
                    reservation.getQuantity().toString(),
                    reservation.getWishes(),
                    reservation.getWishes().replace("\n", "<br/>")
                    };
            sendIt.send("Zur Zeit nicht genutzt", (EmailTemplate)notification, argu, session);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
        
    }// Ende sendMail()

    private Properties getProperties() {
        LOGGER.info("getProperties() aufgerufen.");

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.transport.protocol", 
                propertyDaoLocal.findByKey("mail.transport.protocol").getValue());
        props.put("mail.host", 
                propertyDaoLocal.findByKey("mail.host").getValue());
        props.put("mail.smtp.auth", 
                propertyDaoLocal.findByKey("mail.smtp.auth").getValue());
        props.put("mail.smtp.tls", 
                propertyDaoLocal.findByKey("mail.smtp.tls").getValue());
        props.put("mail.smtp.localhost", 
                propertyDaoLocal.findByKey("mail.smtp.tls").getValue());
        props.put("mail.user", 
                propertyDaoLocal.findByKey("mail.user").getValue());
        props.put("mail.password", 
                propertyDaoLocal.findByKey("mail.password").getValue());
        props.put("mail.mime.charset", 
                propertyDaoLocal.findByKey("mail.mime.charset").getValue());
        props.put("mail.use8bit", 
                propertyDaoLocal.findByKey("mail.use8bit").getValue());
        
        return props;
    } // Ende getProperties()

}// Ende class
