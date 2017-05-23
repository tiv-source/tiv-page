package de.tivsource.page.admin.actions.locations.location;

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
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
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
    private static final long serialVersionUID = 7586573485098206790L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;
    
    private Location location;

	public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "locationAddForm"),
        				@Result(name = "error", type="tiles", location = "locationAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(location != null) {
    	    location.setUuid(UUID.randomUUID().toString());
    	    location.setModified(new Date());
    	    location.setCreated(new Date());
    	    location.setModifiedBy(remoteUser);
    	    location.setModifiedAddress(remoteAddress);
    	    
    	    location.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    location.getDescriptionMap().get(Language.DE).setNamingItem(location);
    	    location.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    String noLineBreaks = location.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    location.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
            location.getDescriptionMap().get(Language.EN).setDescription(location.getDescriptionMap().get(Language.DE).getDescription());
            location.getDescriptionMap().get(Language.EN).setKeywords(location.getDescriptionMap().get(Language.DE).getKeywords());
            location.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
            location.getDescriptionMap().get(Language.EN).setName(location.getDescriptionMap().get(Language.DE).getName());
            location.getDescriptionMap().get(Language.EN).setNamingItem(location);
            location.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());

            /*

            // Pfad in dem die Bild Datei gespeichert wird.
            String uploadPath = "/var/www/html/uploads/";

            // Name der Bild Datei die erstellt werden soll. 
            String pictureSaveName = DigestUtils.shaHex("Hier ist das Geheimniss."
                + picture.getName() + new Date() + "Noch ein bischen.")
                + ".png";
            
            

            File fullPictureFileToCreate = new File(uploadPath, pictureSaveName);
            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fullPictureFileToCreate.exists()) {
                savePictureFile(picture, fullPictureFileToCreate);
            }

            location.setPicture(pictureSaveName);
            */
            
    		locationDaoLocal.merge(location);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("d8a2d89f-cda4-4c64-9e51-18592e88bbc6");
	}

}// Ende class
