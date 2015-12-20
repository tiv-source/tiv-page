package de.tivsource.page.user.actions.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        				@Result(name = "success",  type = "tiles", location = "page"), 
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

            return SUCCESS;
        } else if (event.getBeginning().before(now)) {
            return ERROR;
        } else {
            return "deadline";
        }

	}

    private void setUpPage() {
        page = new Page();
        page.setTechnical(event.getName(Language.DE));
        page.setDescriptionMap(event.getDescriptionMap());
    }

    private void setUpEvent() {
        if(event == null) {
            event = eventDaoLocal.findByUuid(reservation.getEvent().getUuid());
        }
    }

}// Ende class
