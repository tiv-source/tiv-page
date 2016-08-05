package de.tivsource.page.admin.actions.locations.reservation;

import java.io.File;
import java.net.URL;
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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.administration.User;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.enumeration.UrlType;
import de.tivsource.page.helper.sender.ReservationMail;

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

    private static final String HTDOCS = "/srv/www/htdocs";

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;
    
    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private String redirect;
    
    private Reservation reservation;

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

    	if(reservation != null) {
    	    Reservation dbReservation = reservationDaoLocal.findByUuid(reservation.getUuid());
    	    dbReservation.setConfirmed(true);
    	    dbReservation.setConfirmedAddress(remoteAddress);
    	    dbReservation.setConfirmedDate(new Date());
    	    dbReservation.setConfirmedBy(remoteUser);
    	    reservationDaoLocal.merge(dbReservation);

    	    // Hole das entsprechnde Bild aus der Datenbank
    	    String urlAds = HTDOCS + pictureDaoLocal.findByUuid(
    	    			propertyDaoLocal.findByKey("reservation.ads").getValue()
    	    		).getPictureUrl(UrlType.NORMAL.toString());
    	    
    	    URL urlLogo = ConfirmAction.class.getClassLoader().getResource("logo1.png");
    	    URL urlFont = ConfirmAction.class.getClassLoader().getResource("bankgothicltbt.ttf");

    	    // Hole die Benutzerdaten aus der Datenbank
    	    User user = userDaoLocal.findByUsername(remoteUser);
    	    
    	    LOGGER.info("Pfad der Logo Datei " + urlLogo.getFile());
    	    LOGGER.info("Pfad der Ads  Datei " + urlAds);
    	    LOGGER.info("Pfad der Font Datei " + urlFont.getFile());
    	    
    	    ReservationMail reservationMail = new ReservationMail(
                    propertyDaoLocal.findByKey("mail.user").getValue(),
                    propertyDaoLocal.findByKey("mail.password").getValue(),
                    this.getClass().getClassLoader().getResource("template_confirmation.xml"),
                    dbReservation,
                    new File(urlLogo.getFile()),
                    new File(urlAds),
                    getProperties(),
                    new File(urlFont.getFile()),
                    propertyDaoLocal.findByKey("reservation.formAddress").getValue(),
                    propertyDaoLocal.findByKey("reservation.fromName").getValue(),
                    propertyDaoLocal.findByKey("reservation.replyToAddress").getValue(),
                    user.getFirstname() + " " + user.getLastname(),
                    propertyDaoLocal.findByKey("reservation.bccAddress").getValue()
    	    );
    	    reservationMail.send();

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
