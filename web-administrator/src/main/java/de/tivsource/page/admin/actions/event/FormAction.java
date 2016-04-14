package de.tivsource.page.admin.actions.event;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -8430298776289373788L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Event event;

    private String uncheckEvent;

    private String lang;

    public Event getEvent() {
        return event;
    }

	public void setEvent(String uncheckEvent) {
        this.uncheckEvent = uncheckEvent;
    }

	public String getLang() {
	    return lang;
	}

	public void setLang(String lang) {
	    this.lang = lang;
	}
	
	@Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "eventEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "eventAddForm") }
        ),
        @Action(
                value = "copyForm", 
                results = { @Result(name = "success", type="tiles", location = "eventCopyForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "eventDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<Location> getLocationList() {
	    return locationDaoLocal.findAllEventLocation();
	}

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("81e889ed-3195-4390-bf96-80477300c313");
	}

	private void loadPageParameter() {
		if( uncheckEvent != null && uncheckEvent != "" && uncheckEvent.length() > 0) {
		    event = eventDaoLocal.findByUuid(uncheckEvent);
		} else {
		    event = new Event();
		}
	}// Ende loadPageParameter()

}// Ende class
