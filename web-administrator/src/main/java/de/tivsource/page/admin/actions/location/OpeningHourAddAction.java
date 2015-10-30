package de.tivsource.page.admin.actions.location;


import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
public class OpeningHourAddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -3637008080563139796L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(OpeningHourAddAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Location location;

	private OpeningHour openingHour;

    public Location getLocation() {
        return location;
    }

    public OpeningHour getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(OpeningHour openingHour) {
        this.openingHour = openingHour;
    }

    @Override
    @Actions({
        @Action(
        		value = "openingHourAdd", 
        		results = {
                        @Result(name = "success", type = "redirect", params={"locationUuid", "%{openingHour.location.uuid}", "location", "overview.html", "namespace", "/location"}),
                        @Result(name = "input", type="tiles", location = "openingHourAddForm"),
                        @Result(name = "error", type="tiles", location = "openingHourAddError")
                        }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(openingHour != null) {

            Location dbLocation = locationDaoLocal.findByUuid(openingHour.getLocation().getUuid());
            location = dbLocation;
            dbLocation.setModified(new Date());
            dbLocation.setModifiedBy(remoteUser);
            dbLocation.setIp(remoteAddress);

            openingHour.setUuid(UUID.randomUUID().toString());
            openingHour.setLocation(dbLocation);
            dbLocation.getOpeningHours().add(openingHour);
            
            locationDaoLocal.merge(dbLocation);

            return SUCCESS;
        }
        else {
            return ERROR;
        }

    }// Ende execute()

}// Ende class
