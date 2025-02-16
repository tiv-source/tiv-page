package de.tivsource.page.admin.actions.locations.reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="reservationAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reservation/add_form.jsp")
  }),
  @TilesDefinition(name="reservationEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reservation/edit_form.jsp")
  }),
  @TilesDefinition(name="reservationDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reservation/delete_form.jsp")
  }),
  @TilesDefinition(name="reservationView", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reservation/view.jsp")
  }),
  @TilesDefinition(name="reservationConfirmForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reservation/confirm_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 5016702612179192055L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    private String uncheckedEvent;
    
    private Reservation reservation;

    private String uncheckedReservation;

    @StrutsParameter
    public void setUncheckedEvent(String uncheckedEvent) {
        this.uncheckedEvent = uncheckedEvent;
    }

    public Reservation getReservation() {
        return reservation;
    }

    @StrutsParameter
    public void setUncheckedReservation(String uncheckedReservation) {
        this.uncheckedReservation = uncheckedReservation;
    }

    @Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "reservationEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "reservationAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "reservationDeleteForm") }
        ),
        @Action(
                value = "view", 
                results = { @Result(name = "success", type="tiles", location = "reservationView") }
        ),
        @Action(
                value = "confirmForm", 
                results = { @Result(name = "success", type="tiles", location = "reservationConfirmForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<Date> getTimes() {
        LOGGER.info("getTimes() aufgerufen.");
        if(reservation == null) {
            loadPageParameter();
        }

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

        LOGGER.info("inhalt der Liste: " + times.size());

        return times;
    }
    
    private void loadPageParameter() {

        if( uncheckedEvent != null && uncheckedEvent != "" && uncheckedEvent.length() > 0) {
            reservation = new Reservation();
            reservation.setEvent(eventDaoLocal.findByUuid(uncheckedEvent));
        }

	    if( uncheckedReservation != null && uncheckedReservation != "" && uncheckedReservation.length() > 0) {
	        reservation = reservationDaoLocal.findByUuid(uncheckedReservation);
            Calendar calendarTime = Calendar.getInstance();
            calendarTime.setTime(reservation.getTime());
            reservation.setTime(calendarTime.getTime());
	    }

	}// Ende loadPageParameter()
	
}// Ende class
