package de.tivsource.page.admin.actions.gallery;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;

/**
 * 
 * @author Marc Michele
 *
 */
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5770235481070099665L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Gallery gallery;

    private String lang;

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
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
        				@Result(name = "input",   type = "tiles", location = "galleryEditForm"),
        				@Result(name = "error",   type = "tiles", location = "galleryEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(gallery != null) {
    		LOGGER.info("UUID des Events: " + gallery.getUuid());
    		Gallery dbGallery = galleryDaoLocal.findByUuid(gallery.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                gallery.getDescriptionMap().put(Language.DE, dbGallery.getDescriptionObject(Language.DE));
                dbGallery.getDescriptionMap().get(Language.EN).setDescription(gallery.getDescription(Language.EN));
                dbGallery.getDescriptionMap().get(Language.EN).setKeywords(gallery.getKeywords(Language.EN));
                dbGallery.getDescriptionMap().get(Language.EN).setName(gallery.getName(Language.EN));
            } else {
                dbGallery.getDescriptionMap().get(Language.DE).setDescription(gallery.getDescription(Language.DE));
                dbGallery.getDescriptionMap().get(Language.DE).setKeywords(gallery.getKeywords(Language.DE));;
                dbGallery.getDescriptionMap().get(Language.DE).setName(gallery.getName(Language.DE));
            }
    		
    		
    		dbGallery.setModifiedAddress(remoteAddress);
    		dbGallery.setModified(new Date());
    		dbGallery.setModifiedBy(remoteUser);
    		dbGallery.setVisible(gallery.getVisible());
    		dbGallery.setOrderNumber(gallery.getOrderNumber());
    		dbGallery.setTechnical(gallery.getTechnical());
    		
    		
    		galleryDaoLocal.merge(dbGallery);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
