package de.tivsource.page.admin.actions.event;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7464881886808879895L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;
    
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "eventAddForm"),
        				@Result(name = "error", type="tiles", location = "eventAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(event != null) {
    	    event.setUuid(UUID.randomUUID().toString());
    	    event.setModified(new Date());
    	    event.setCreated(new Date());
    	    event.setModifiedBy(remoteUser);
    	    event.setModifiedAddress(remoteAddress);

    	    // Füge Event in die Location ein
    	    event.getLocation().getEvents().add(event);


    	    event.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    event.getDescriptionMap().get(Language.DE).setNamingItem(event);
    	    event.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = event.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    event.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    event.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    event.getDescriptionMap().get(Language.EN).setNamingItem(event);
    	    event.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    event.getDescriptionMap().get(Language.EN).setName(event.getDescriptionMap().get(Language.DE).getName());
    	    event.getDescriptionMap().get(Language.EN).setDescription(event.getDescriptionMap().get(Language.DE).getDescription());
    	    event.getDescriptionMap().get(Language.EN).setKeywords(event.getDescriptionMap().get(Language.DE).getKeywords());

    	    eventDaoLocal.merge(event);
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
