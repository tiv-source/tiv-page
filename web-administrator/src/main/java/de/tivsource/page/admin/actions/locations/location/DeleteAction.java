package de.tivsource.page.admin.actions.locations.location;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="locationDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/delete_error.jsp")
  }),
  @TilesDefinition(name="locationEventsError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/events_error.jsp")
  }),
  @TilesDefinition(name="locationVacanciesError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/vacancies_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3269503270283436128L;    

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @Autowired
    @Qualifier("locationDaoLocal")
    private LocationDaoLocal locationDaoLocal;

    private Location location;

    @Autowired
    public DeleteAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=3)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "locationDeleteError"),
        				@Result(name = "error", type="tiles", location = "locationDeleteError"),
                        @Result(name = "events", type="tiles", location = "locationEventsError"),
                        @Result(name = "vacancies", type="tiles", location = "locationVacanciesError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(location != null) {
    	    if(!locationDaoLocal.hasEvents(location.getUuid())) {
                if(!locationDaoLocal.hasVacancies(location.getUuid())) {
            	    Location dbLocation = locationDaoLocal.findByUuid(location.getUuid());
            		dbLocation.setModified(new Date());
            		dbLocation.setModifiedBy(remoteUser);
            		dbLocation.setModifiedAddress(remoteAddress);
            		locationDaoLocal.merge(dbLocation);
            		locationDaoLocal.delete(dbLocation);
                    return SUCCESS;
                }
                return "vacancies";
            }
    	    return "events";
        }
        return ERROR;
    	
    }// Ende execute()
	
}// Ende class
