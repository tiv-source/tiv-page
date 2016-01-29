package de.tivsource.page.admin.actions.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
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
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7464881886808879895L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(AddAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Event event;

    private File picture;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setPicture(File picture) {
        this.picture = picture;
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

    	    event.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    event.getDescriptionMap().get(Language.EN).setNamingItem(event);
    	    event.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    event.getDescriptionMap().get(Language.EN).setName(event.getDescriptionMap().get(Language.DE).getName());
    	    event.getDescriptionMap().get(Language.EN).setDescription(event.getDescriptionMap().get(Language.DE).getDescription());
    	    event.getDescriptionMap().get(Language.EN).setKeywords(event.getDescriptionMap().get(Language.DE).getKeywords());

    	    
    	    if(picture != null) {

                // Pfad in dem die Bild Datei gespeichert wird.
                String uploadPath = "/srv/www/htdocs/uploads/";

                // Name der Bild Datei die erstellt werden soll. 
                String pictureSaveName = DigestUtils.shaHex("Hier ist das Geheimniss."
                    + picture.getName() + new Date() + "Noch ein bischen.")
                    + ".png";

                File fullPictureFileToCreate = new File(uploadPath, pictureSaveName);
                // Wenn die Datei noch nicht existiert wird Sie erstellt.
                if (!fullPictureFileToCreate.exists()) {
                    savePictureFile(picture, fullPictureFileToCreate);
                }

                event.setPicture(pictureSaveName);

    	    }



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

    private static void savePictureFile(File source, File destination) throws Exception {
        byte[] buffer = new byte[(int) source.length()];
        InputStream in = new FileInputStream(source);
        in.read(buffer);
        FileOutputStream fileOutStream = new FileOutputStream(destination);
        fileOutStream.write(buffer);
        fileOutStream.flush();
        fileOutStream.close();
        in.close();
    }

}// Ende class
