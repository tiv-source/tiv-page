package de.tivsource.page.admin.actions.locations.location;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * 
 * @author Marc Michele
 *
 */
public class OpeningHourDeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -3637008080563139796L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(OpeningHourDeleteAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

	private Location location;

	private String uncheckLocation;

	private Integer openingHoursIndex;
	
    public Location getLocation() {
        return location;
    }

    @StrutsParameter
    public void setLocationUuid(String location) {
        this.uncheckLocation = location;
    }

    @StrutsParameter
    public void setOpeningHours(Integer openingHoursIndex) {
        this.openingHoursIndex = openingHoursIndex;
    }

    @Override
    @Actions({
        @Action(
        		value = "openingHourDelete", 
        		results = {
                        @Result(name = "success", type = "redirect", params={"uncheckLocation", "%{location.uuid}", "location", "overview.html", "namespace", "/location"}),
                        @Result(name = "input", type="tiles", location = "openingHourDeleteForm"),
                        @Result(name = "error", type="tiles", location = "openingHourDeleteError")
                        }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	this.loadPageParameter();

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(location != null) {

            Location dbLocation = locationDaoLocal.findByUuid(location.getUuid());
            dbLocation.setModified(new Date());
            dbLocation.setModifiedBy(remoteUser);
            dbLocation.setModifiedAddress(remoteAddress);
            locationDaoLocal.merge(dbLocation);

            locationDaoLocal.removeOpeningHour(openingHoursIndex, location.getUuid());


            return SUCCESS;
        }
        else {
            return ERROR;
        }

    }// Ende execute()

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		}

	}// Ende loadPageParameter()

}// Ende class
