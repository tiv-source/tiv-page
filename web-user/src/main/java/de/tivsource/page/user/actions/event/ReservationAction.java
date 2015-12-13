package de.tivsource.page.user.actions.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
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
    
	private Reservation reservation;

	private Page page;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public Page getPage() {
        if(page == null) {
            setUpPage();
        }
        return page;
    }// Ende getPage()

    @Actions({
        @Action(
        		value = "reserve", 
        		results = { 
        				@Result(name = "success", type="tiles", location = "page"), 
        				@Result(name = "input", type="tiles", location = "event")
        				}
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();

    	
    	// Speichere Message Objekt
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
        
        reservationDaoLocal.merge(reservation);
        
		return SUCCESS;
	}
    
    private void setUpPage() {
        LOGGER.info("Action Errors: " + this.getFieldErrors().size());
        if(this.getFieldErrors().size() > 0) {
            page = pageDaoLocal.findByTechnical("contact");
        } else {
            page = pageDaoLocal.findByTechnical("sent");
        }
    }
    
}// Ende class
