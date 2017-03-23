package de.tivsource.page.admin.actions.locations.location;


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
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.enumeration.Weekday;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="openingHourAddForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/default.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/opening_hour_add_form.jsp")
  })
})
public class OpeningHourAddFormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 1930159940136753862L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(OpeningHourAddFormAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

	private Location location;

	private String uncheckLocation;
	
    public Location getLocation() {
        return location;
    }

    public void setLocationUuid(String location) {
        this.uncheckLocation = location;
    }

    @Override
    @Actions({
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

    public List<Weekday> getWeekdays() {
        return Arrays.asList(Weekday.values());
    }

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		}

	}// Ende loadPageParameter()

}// Ende class
