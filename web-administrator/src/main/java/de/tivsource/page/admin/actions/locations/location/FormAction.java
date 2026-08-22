package de.tivsource.page.admin.actions.locations.location;


import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.enumeration.Weekday;

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
  }),
  @TilesDefinition(name="imageForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/image_form.jsp")
  }),
  @TilesDefinition(name="osmImageForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/osm_image_form.jsp")
  }),
  @TilesDefinition(name="locationOverview", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/default.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/location_overview.jsp")
  }),
  @TilesDefinition(name="openingHourAddForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/default.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/opening_hour_add_form.jsp")
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

    @Autowired
    @Qualifier("cssDaoLocal")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @Autowired
    @Qualifier("locationDaoLocal")
    private LocationDaoLocal locationDaoLocal;

	private Location location;

	private String uncheckLocation;

	private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    @Autowired
    public FormAction(PropertyService propertyService) {
        super(propertyService);
    }

    public Location getLocation() {
        return location;
    }

    @StrutsParameter
    public void setUncheckLocation(String uncheckLocation) {
        this.uncheckLocation = uncheckLocation;
    }

	public String getLang() {
        return lang;
    }

	@StrutsParameter
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
            results = { @Result(name = "success", type="tiles", location = "locationEditForm") }
        ),
        @Action(
            value = "addForm",
            results = { @Result(name = "success", type="tiles", location = "locationAddForm") }
        ),
        @Action(
            value = "deleteForm",
            results = { @Result(name = "success", type="tiles", location = "locationDeleteForm") }
        ),
        @Action(
            value = "imageForm", 
            results = { @Result(name = "success", type="tiles", location = "imageForm") }
        ),
        @Action(
            value = "osmImageForm", 
            results = { @Result(name = "success", type="tiles", location = "osmImageForm") }
        ),
        @Action(
            value = "overview", 
            results = { @Result(name = "success", type="tiles", location = "locationOverview") }
        ),
        @Action(
            value = "openingHourAddForm",
            results = { @Result(name = "success", type="tiles", location = "openingHourAddForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    public List<Weekday> getWeekdays() {
        return Arrays.asList(Weekday.values());
    }

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
			LOGGER.info("Anzahl der OpeningHours: " + location.getOpeningHours().size());
		} else {
			location = new Location();
		}

	}// Ende loadPageParameter()
	
}// Ende class
