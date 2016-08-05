package de.tivsource.page.admin.actions.others.gallery;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -3282620598068445927L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Gallery gallery;

    private String uncheckGallery;

    private String lang;

    public Gallery getGallery() {
        return gallery;
    }

	public void setGallery(String uncheckGallery) {
        this.uncheckGallery = uncheckGallery;
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
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "galleryEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "galleryAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "galleryDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Check ob gallery gesetzt wurde
		return pictureDaoLocal.findAll(gallery.getUuid());
	}

	private void loadPageParameter() {
		if( uncheckGallery != null && uncheckGallery != "" && uncheckGallery.length() > 0) {
		    gallery = galleryDaoLocal.findByUuid(uncheckGallery);
		} else {
		    gallery = new Gallery();
		}
	}// Ende loadPageParameter()

}// Ende class
