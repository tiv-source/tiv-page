package de.tivsource.page.reservation.actions.event;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.reservation.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -2094121636327219921L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    private Event event;

    private String eventUuid;

    private List<Reservation> reservations;

    @Override
    @Actions({
        @Action(
        		value = "*/reservation_list_view", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "reservation_list_view"),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        ),
        @Action(
        		value = "*/reservation_list_edit", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "reservation_list_edit"),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	// Hole Action Locale
    	this.getLanguageFromActionContext();

    	eventUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("EventUuid: " + eventUuid);

    	// http://ncc1701a/reservation/event/ac7006f9-625c-4996-a534-eb123c5f0320/view.html

        eventUuid = eventUuid.replaceAll("/reservation_list_view.html", "");
        eventUuid = eventUuid.replaceAll("/reservation_list_edit.html", "");
        eventUuid = eventUuid.replaceAll("/event/", "");

        LOGGER.info("EventUuid: " + eventUuid);

        /*
         * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
         * die Location mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(eventUuid) && eventDaoLocal.isEvent(eventUuid)) {
            LOGGER.info("gültige Event Uuid.");
            event = eventDaoLocal.findByUuid(eventUuid);
            reservations = reservationDaoLocal.findAll(event, 0, reservationDaoLocal.countAll(event));
            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    public Event getEvent() {
		return event;
	}

	public List<Reservation> getList() {
        return reservations;
    }
	
    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class