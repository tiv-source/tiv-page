package de.tivsource.page.admin.actions.locations.event;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;

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
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Event event;

    private String lang = "DE";

    private List<Location> locationList;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        locationList = locationDaoLocal.findAll(0, locationDaoLocal.countAll());
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
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
                String noLineBreaks = event.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbEvent.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbEvent.getDescriptionMap().get(Language.EN).setKeywords(event.getKeywords(Language.EN));
                dbEvent.getDescriptionMap().get(Language.EN).setName(event.getName(Language.EN));
            } else {
        	    String noLineBreaks = event.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
        	    dbEvent.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
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
    		dbEvent.setPiwikGoal(event.getPiwikGoal());
    		dbEvent.setMaxReservations(event.getMaxReservations());
    		dbEvent.setMaxPersons(event.getMaxPersons());
    		dbEvent.setPictureOnPage(event.getPictureOnPage());
    		dbEvent.setTimeSelection(event.getTimeSelection());
    		dbEvent.setCssGroup(event.getCssGroup());

    		eventDaoLocal.merge(dbEvent);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<Location> getLocationList() {
        return locationList;
    }// Ende getLocationList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
