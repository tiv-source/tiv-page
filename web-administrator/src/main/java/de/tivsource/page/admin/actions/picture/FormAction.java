package de.tivsource.page.admin.actions.picture;

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
	private static final long serialVersionUID = 667128497047812792L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Picture picture;

    private String uncheckPicture;

    private String lang;

    public Picture getPicture() {
        return picture;
    }

	public void setPicture(String uncheckPicture) {
        this.uncheckPicture = uncheckPicture;
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
        		results = { @Result(name = "success", type="tiles", location = "pictureEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "pictureAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "pictureDeleteForm") }
        ),
        @Action(
                value = "pictureForm", 
                results = { @Result(name = "success", type="tiles", location = "pictureForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<Gallery> getGalleryList() {
		return galleryDaoLocal.findAll(0, galleryDaoLocal.countAll());
	}
	
	private void loadPageParameter() {
		if( uncheckPicture != null && uncheckPicture != "" && uncheckPicture.length() > 0) {
		    picture = pictureDaoLocal.findByUuid(uncheckPicture);
		} else {
		    picture = new Picture();
		}
	}// Ende loadPageParameter()

}// Ende class
