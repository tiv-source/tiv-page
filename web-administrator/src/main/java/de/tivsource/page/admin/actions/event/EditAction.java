package de.tivsource.page.admin.actions.event;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6472541996524122642L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Event event;

    private String lang;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "eventEditForm"),
        				@Result(name = "error",   type = "tiles", location = "eventEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(event != null) {
    		LOGGER.info("UUID des Events: " + event.getUuid());
    		Event dbEvent = eventDaoLocal.findByUuid(event.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                event.getDescriptionMap().put(Language.DE, dbEvent.getDescriptionObject(Language.DE));
                dbEvent.getDescriptionMap().get(Language.EN).setDescription(event.getDescription(Language.EN));
                dbEvent.getDescriptionMap().get(Language.EN).setKeywords(event.getKeywords(Language.EN));
                dbEvent.getDescriptionMap().get(Language.EN).setName(event.getName(Language.EN));
            } else {
                dbEvent.getDescriptionMap().get(Language.DE).setDescription(event.getDescription(Language.DE));
                dbEvent.getDescriptionMap().get(Language.DE).setKeywords(event.getKeywords(Language.DE));;
                dbEvent.getDescriptionMap().get(Language.DE).setName(event.getName(Language.DE));
            }
    		
    		dbEvent.setBeginning(event.getBeginning());
    		dbEvent.setDeadline(event.getDeadline());
    		dbEvent.setEnding(event.getEnding());
    		dbEvent.setModifiedAddress(remoteAddress);
    		dbEvent.setLocation(locationDaoLocal.findByUuidWidthEvents(event.getLocation().getUuid()));
    		dbEvent.setModified(new Date());
    		dbEvent.setModifiedBy(remoteUser);
    		dbEvent.setPrice(event.getPrice());
    		dbEvent.setReservation(event.getReservation());
    		dbEvent.setVisible(event.getVisible());
    		dbEvent.setPicture(event.getPicture());
    		dbEvent.setPiwikGoal(event.getPiwikGoal());

    		eventDaoLocal.merge(dbEvent);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<Location> getLocationList() {
        return locationDaoLocal.findAllEventLocation();
    }

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("81e889ed-3195-4390-bf96-80477300c313");
	}

}// Ende class
