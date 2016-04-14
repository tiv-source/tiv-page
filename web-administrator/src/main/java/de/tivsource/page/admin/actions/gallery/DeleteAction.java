package de.tivsource.page.admin.actions.gallery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -4785386555841300052L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

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
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "galleryDeleteForm"),
        				@Result(name = "error", type="tiles", location = "galleryDeleteError"),
        				@Result(name = "references", type="tiles", location = "galleryReferences")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");


        if(gallery != null) {
            if(!galleryDaoLocal.hasReferences(gallery.getUuid())) {
                Gallery dbGallery = galleryDaoLocal.findByUuid(gallery.getUuid());
                galleryDaoLocal.delete(dbGallery);
                return SUCCESS;
            }
            else {
            	return "references";
            }
    	}
        else {
    		return ERROR;
    	}

    }// Ende execute()
	
}// Ende class
