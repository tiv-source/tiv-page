package de.tivsource.page.user.actions.location;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="locationView", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",    value = "/WEB-INF/tiles/active/meta/location_view.jsp"),
    @TilesPutAttribute(name = "twitter", value = "/WEB-INF/tiles/active/twitter/location_view.jsp"),
    @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/location/location_view.jsp")
  })
})
public class LocationAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6236431708460575442L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LocationAction.class);

    /**
     * Attribut das die maximal Anzahl der Liste enthält. 
     */
    private static final Integer TO = 3;

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    /**
     * Location Uuid im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String locationUuid;

    private Location location;

    private List<Event> events;
    
    private Page page;

    public Location getLocation() {
        return location;
    }

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "locationView"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.location").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Lese UUID aus dem ServletRequest
            locationUuid = ServletActionContext.getRequest().getServletPath();
            LOGGER.info("LocationUuid: " + locationUuid);
            // Lösche unbenötigte Teile aus dem Pfad
            locationUuid = locationUuid.replaceAll("/index.html", "");
            locationUuid = locationUuid.replaceAll("/location/", "");
            LOGGER.info("LocationUuid: " + locationUuid);

            /*
             * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
             * die Location mit der Uuid gibt dann wird der Block ausgeführt.
             */
            if (isValid(locationUuid) && locationDaoLocal.isLocation(locationUuid)) {
                LOGGER.info("gültige Location Uuid.");

                // Setze Daten in ein Page Objekt
                setUpPage();

                // Wenn dies eine Event-Location ist dann hole die Events aus der
                // Datenbank
                if(location.getEvent()) {
                    events = eventDaoLocal.findAll(locationUuid, 0, TO);
                }

                return SUCCESS;
            }

            // Wenn es einen Manipulationsversuch gab.
            return ERROR;
            
        } else {
            // Wenn das Modul nicht aktiviert ist.
            return ERROR;
        }
    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    public List<Event> getEvents() {
		return events;
	}

	private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpPage() {
        location = locationDaoLocal.findByUuid(locationUuid);
        page = new Page();
        page.setTechnical(location.getTechnical());
        page.setDescriptionMap(location.getDescriptionMap());
        page.setPicture(location.getPicture());
        page.setPictureOnPage(location.getPictureOnPage());
        page.setCssGroup(location.getCssGroup());
    }

}// Ende class
