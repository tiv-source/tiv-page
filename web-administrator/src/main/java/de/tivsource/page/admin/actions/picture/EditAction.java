package de.tivsource.page.admin.actions.picture;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
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
	private static final long serialVersionUID = 4375251545367946812L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Picture picture;

    private String lang;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
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
        				@Result(name = "input",   type = "tiles", location = "pictureEditForm"),
        				@Result(name = "error",   type = "tiles", location = "pictureEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(picture != null) {
    		LOGGER.info("UUID des Events: " + picture.getUuid());
    		Picture dbPicture = pictureDaoLocal.findByUuid(picture.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                picture.getDescriptionMap().put(Language.DE, dbPicture.getDescriptionMap().get(Language.DE));
                dbPicture.getDescriptionMap().get(Language.EN).setDescription(picture.getDescriptionMap().get(Language.EN).getDescription());
                dbPicture.getDescriptionMap().get(Language.EN).setKeywords(picture.getDescriptionMap().get(Language.EN).getKeywords());
                dbPicture.getDescriptionMap().get(Language.EN).setName(picture.getDescriptionMap().get(Language.EN).getName());
            } else {
                dbPicture.getDescriptionMap().get(Language.DE).setDescription(picture.getDescriptionMap().get(Language.DE).getDescription());
                dbPicture.getDescriptionMap().get(Language.DE).setKeywords(picture.getDescriptionMap().get(Language.DE).getKeywords());;
                dbPicture.getDescriptionMap().get(Language.DE).setName(picture.getDescriptionMap().get(Language.DE).getName());
            }

            dbPicture.setOrderNumber(picture.getOrderNumber());
            dbPicture.setVisible(picture.getVisible());
            dbPicture.setGallery(picture.getGallery());
            
    		dbPicture.setModified(new Date());
    		dbPicture.setModifiedBy(remoteUser);
    		dbPicture.setModifiedAddress(remoteAddress);
    		
    		pictureDaoLocal.merge(dbPicture);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

	public List<Gallery> getGalleryList() {
		return galleryDaoLocal.findAll(0, galleryDaoLocal.countAll());
	}

}// Ende class
