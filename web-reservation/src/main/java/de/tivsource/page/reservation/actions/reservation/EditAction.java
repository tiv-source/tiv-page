package de.tivsource.page.reservation.actions.reservation;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.reservation.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2437049578027549600L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    private Reservation reservation;
    
    private String reservationUuid;
    
	@Override
    @Actions({
        @Action(
        		value = "*/edit", 
        		results = { @Result(name = "success", type="tiles", location = "reservation_view") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	// Hole Action Locale
    	this.getLanguageFromActionContext();



    	reservationUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("ReservationUuid: " + reservationUuid);

    	// http://ncc1701a/reservation/event/ac7006f9-625c-4996-a534-eb123c5f0320/view.html
        
        
        reservationUuid = reservationUuid.replaceAll("/view.html", "");
        reservationUuid = reservationUuid.replaceAll("/editForm.html", "");
        reservationUuid = reservationUuid.replaceAll("/reservation/", "");
            
        LOGGER.info("ReservationUuid: " + reservationUuid);

        
        /*
         * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
         * die Location mit der Uuid gibt dann wird der Block ausgeführt.
         */
        // TODO: isReservation()
        if (isValid(reservationUuid)) {
            LOGGER.info("gültige Event Uuid.");
            reservation = reservationDaoLocal.findByUuid(reservationUuid);
            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    public Reservation getReservation() {
		return reservation;
	}

	private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class
