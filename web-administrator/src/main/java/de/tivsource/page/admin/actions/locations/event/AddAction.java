package de.tivsource.page.admin.actions.locations.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
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
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7464881886808879895L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Event event;

    private List<Location> locationList;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
    	    event.setTechnical("EVENT_" + event.getUuid());

    	    event.getImage().setUuid(UUID.randomUUID().toString());
    	    event.getImage().generate();
    	    event.getImage().setCreated(new Date());
    	    event.getImage().setModified(new Date());
    	    event.getImage().setModifiedAddress(remoteAddress);
    	    event.getImage().setModifiedBy(remoteUser);
    	    
    	    eventDaoLocal.merge(event);
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

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("Variable uploadedFiles ist nicht leer.");
            Iterator<UploadedFile> ufIterator = uploadedFiles.iterator();
            while(ufIterator.hasNext()) {
                UploadedFile next = ufIterator.next();
                LOGGER.info("UploadedFile für Input-Name: " + next.getInputName() + " gefunden.");
                if(next.getInputName().equalsIgnoreCase("event.image")) {
                    this.event = new Event();
                    this.event.setImage(new PictureItemImage());
                    this.event.getImage().setPictureItem(this.event);
                    this.event.getImage().setUploadFile(UploadedFileToUploadFile.convert(next));
                }                
            }// Ende while()
         }
    }// Ende withUploadedFiles(List<UploadedFile> uploadedFiles)

}// Ende class
