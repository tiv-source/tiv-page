package de.tivsource.page.admin.actions.location;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * 
 * @author Marc Michele
 *
 */
public class OverviewAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4634589509791943416L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(OverviewAction.class);

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
        		value = "overview", 
        		results = {
        		        @Result(name = "success", type="tiles", location = "locationOverview"),
        		        @Result(name = "input", type = "redirectAction", location = "index.html"),
                        @Result(name = "error", type="tiles", location = "openingError")
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	LOGGER.info("Anzahl der OpeningHours: " + location.getOpeningHours().size());
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		}

	}// Ende loadPageParameter()
	
}// Ende class
