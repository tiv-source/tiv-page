package de.tivsource.page.admin.actions.locations.location;


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
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="locationAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/add_form.jsp")
  }),
  @TilesDefinition(name="locationEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/edit_form.jsp")
  }),
  @TilesDefinition(name="locationDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 1231962694824098776L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

	private Location location;

	private String uncheckLocation;

	private String lang;

    private List<Picture> pictureList;

    private List<CSSGroup> cssGroupList;

    public Location getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.uncheckLocation = location;
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
        pictureList = pictureDaoLocal.findAll(propertyDaoLocal.findByKey("gallery.uuid.for.location.picture").getValue());
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
            value = "editForm",
            results = { @Result(name = "success", type="tiles", location = "locationEditForm") }
        ),
        @Action(
            value = "addForm",
            results = { @Result(name = "success", type="tiles", location = "locationAddForm") }
        ),
        @Action(
            value = "deleteForm",
            results = { @Result(name = "success", type="tiles", location = "locationDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<Picture> getPictureList() {
        return pictureList;
    }// Ende getPictureList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		} else {
			location = new Location();
		}

	}// Ende loadPageParameter()
	
}// Ende class
