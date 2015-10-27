package de.tivsource.page.admin.actions.location;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;

/**
 * 
 * @author Marc Michele
 *
 */
public class OpeningHourFormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -3637008080563139796L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(OpeningHourFormAction.class);

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
        		value = "openingHourAddForm", 
        		results = { @Result(name = "success", type="tiles", location = "openingHourAddForm") }
        ),
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

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		}

	}// Ende loadPageParameter()

}// Ende class
