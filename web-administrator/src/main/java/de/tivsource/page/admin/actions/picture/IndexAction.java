package de.tivsource.page.admin.actions.picture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class IndexAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1843745934395440825L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Picture picture;

    private String uncheckPicture;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(String uncheckPicture) {
        this.uncheckPicture = uncheckPicture;
    }

	@Override
    @Actions({
        @Action(
        		value = "index", 
        		results = { @Result(name = "success", type="tiles", location = "picture") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    private void loadPageParameter() {
        if( uncheckPicture != null && uncheckPicture != "" && uncheckPicture.length() > 0) {
            picture = pictureDaoLocal.findByUuid(uncheckPicture);
        }
    }// Ende loadPageParameter()
	
}// Ende class
