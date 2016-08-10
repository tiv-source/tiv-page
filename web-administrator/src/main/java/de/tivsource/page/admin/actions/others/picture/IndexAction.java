package de.tivsource.page.admin.actions.others.picture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pictureGrid", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/default_jquery.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/picture/list.jsp")
  })
})
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
        		results = { @Result(name = "success", type="tiles", location = "pictureGrid") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    private void loadPageParameter() {
    	// TODO: Unnötigen code entfernen
        if( uncheckPicture != null && uncheckPicture != "" && uncheckPicture.length() > 0) {
            picture = pictureDaoLocal.findByUuid(uncheckPicture);
        }
    }// Ende loadPageParameter()
	
}// Ende class
