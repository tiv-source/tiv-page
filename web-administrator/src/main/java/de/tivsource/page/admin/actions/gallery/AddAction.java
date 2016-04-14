package de.tivsource.page.admin.actions.gallery;

import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3442711359941308571L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Gallery gallery;

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "galleryAddForm"),
        				@Result(name = "error", type="tiles", location = "galleryAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(gallery != null) {
    	    gallery.setUuid(UUID.randomUUID().toString());
    	    gallery.setModified(new Date());
    	    gallery.setCreated(new Date());
    	    gallery.setModifiedBy(remoteUser);
    	    gallery.setModifiedAddress(remoteAddress);

    	    gallery.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    gallery.getDescriptionMap().get(Language.DE).setNamingItem(gallery);
    	    gallery.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);

    	    gallery.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    gallery.getDescriptionMap().get(Language.EN).setNamingItem(gallery);
    	    gallery.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    gallery.getDescriptionMap().get(Language.EN).setName(gallery.getDescriptionMap().get(Language.DE).getName());
    	    gallery.getDescriptionMap().get(Language.EN).setDescription(gallery.getDescriptionMap().get(Language.DE).getDescription());
    	    gallery.getDescriptionMap().get(Language.EN).setKeywords(gallery.getDescriptionMap().get(Language.DE).getKeywords());

    	    galleryDaoLocal.merge(gallery);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

}// Ende class
