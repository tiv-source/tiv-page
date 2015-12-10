package de.tivsource.page.admin.actions.location;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * 
 * @author Marc Michele
 *
 */
public class PictureEditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 840057440703403778L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(PictureEditAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Location location;

    private File picture;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    @Override
    @Actions({
        @Action(
        		value = "picture", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "locationPictureForm"),
        				@Result(name = "error",   type = "tiles", location = "pictureEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(location != null) {
    		LOGGER.info(location.getUuid());
    		Location dbLocation = locationDaoLocal.findByUuid(location.getUuid());

    		dbLocation.setModified(new Date());
    		dbLocation.setModifiedBy(remoteUser);
    		dbLocation.setIp(remoteAddress);

    		
    		
            // Pfad in dem die Bild Datei gespeichert wird.
            String uploadPath = "/srv/www/htdocs/uploads/";

            // Lösche das alte Bild
            File oldPicture = new File(uploadPath + dbLocation.getPicture());
            if (oldPicture.exists()) {
                oldPicture.delete();
            }
            
            // Name der Bild Datei die erstellt werden soll. 
            String pictureSaveName = DigestUtils.shaHex("Hier ist das Geheimniss."
                + picture.getName() + new Date() + "Noch ein bischen.")
                + ".png";

            File fullPictureFileToCreate = new File(uploadPath, pictureSaveName);
            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fullPictureFileToCreate.exists()) {
                savePictureFile(picture, fullPictureFileToCreate);
            }

            dbLocation.setPicture(pictureSaveName);
    		
    		
    		
    		locationDaoLocal.merge(dbLocation);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

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
