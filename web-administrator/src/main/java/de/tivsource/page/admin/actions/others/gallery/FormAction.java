package de.tivsource.page.admin.actions.others.gallery;

import java.util.Arrays;
import java.util.List;

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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.enumeration.GalleryType;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="galleryAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/add_form.jsp")
  }),
  @TilesDefinition(name="galleryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/edit_form.jsp")
  }),
  @TilesDefinition(name="galleryDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -3282620598068445927L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Gallery gallery;

    private String uncheckGallery;

    private String lang = "DE";

    private List<Picture> pictureList;

    private List<CSSGroup> cssGroupList;

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
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
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

	public List<GalleryType> getGalleryTypeList() {
	    return Arrays.asList(GalleryType.values());
	}

    public List<Picture> getPictureList() {
        return pictureList;
    }// Ende getPictureList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

	private void loadPageParameter() {
	    LOGGER.info("loadPageParameter() aufgerufen.");
		if( uncheckGallery != null && uncheckGallery != "" && uncheckGallery.length() > 0) {
		    gallery = galleryDaoLocal.findByUuid(uncheckGallery);
		    pictureList = pictureDaoLocal.findAll(uncheckGallery);
		} else {
		    gallery = new Gallery();
		}
	}// Ende loadPageParameter()

}// Ende class
