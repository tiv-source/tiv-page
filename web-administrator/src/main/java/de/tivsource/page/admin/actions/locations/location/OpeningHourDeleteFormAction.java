package de.tivsource.page.admin.actions.locations.location;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;
import de.tivsource.page.enumeration.Weekday;

/**
 * 
 * @author Marc Michele
 *
 */
public class OpeningHourDeleteFormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -3637008080563139796L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(OpeningHourDeleteFormAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

	private Location location;

	private String uncheckLocation;

	private Integer openingHoursIndex;
	
    public Location getLocation() {
        return location;
    }

    public void setLocationUuid(String location) {
        this.uncheckLocation = location;
    }

    public Integer getOpeningHours() {
        return openingHoursIndex;
    }

    public void setOpeningHours(Integer openingHoursIndex) {
        this.openingHoursIndex = openingHoursIndex;
    }

    @Override
    @Actions({
        @Action(
        		value = "openingHourDeleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "openingHourDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public OpeningHour getOpeningHour() {
        List<OpeningHour> openingHours = new ArrayList<OpeningHour>(location.getOpeningHours());
        return openingHours.get(openingHoursIndex);
    }

    public List<Weekday> getWeekdays() {
        return Arrays.asList(Weekday.values());
    }

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		}

	}// Ende loadPageParameter()

}// Ende class
