package de.tivsource.page.user.actions.reservation;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.interfaces.Pagination;

public class LocationAction extends EmptyAction implements Pagination {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6236431708460575442L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(LocationAction.class);

    /**
     * Attribut das die maximal Anzahl der Liste enthält. 
     */
    private static final Integer TO = 7;

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

    private List<Event> events;

    private Location location;
    
    private Page page;

    private Integer next;
    private Integer previous;
    private Integer current;

    /**
     * Attribut das den Startpunkt der Liste enthält.
     */
    private Integer from;

    /**
     * Angefordete Seitenzahl (Achtung kann durch den benutzer manipuliert werden). 
     */
    private Integer pagination;

    /**
     * Attribut das die Anzahl der Objekte in der Datenbank enthält.
     */
    private Integer dbQuantity;

    /**
     * Attribute das die maximal mögliche Anzahl an Seiten enthält.
     */
    private Integer maxPages;

    public Location getLocation() {
        return location;
    }

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "reservationLocation"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        locationUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("LocationUuid: " + locationUuid);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        locationUuid = locationUuid.replaceAll("/index.html", "");
        locationUuid = locationUuid.replaceAll("/reservation/", "");
            
        LOGGER.info("LocationUuid: " + locationUuid);
        
        // Setze Daten in ein Page Objekt
        setUpPage();

        
        /*
         * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
         * die Location mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(locationUuid) && locationDaoLocal.isEventLocation(locationUuid)) {
            LOGGER.info("gültige Location Uuid.");

            // Hole die Anzahl aus der Datenbank
            this.getDBCount();

            // Wenn page nicht gesetzt wurde
            if(pagination == null) {
                pagination = 1;
            }

            //  Wenn page größer als maxPages ist.
            if(pagination > maxPages) {
                pagination = 1;
            }

            // Kalkuliere die Seiten
            this.calculate();

            events = eventDaoLocal.findAll(locationUuid, from, TO);
            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public Integer getNext() {
        return next;
    }

    @Override
    public Integer getPrevious() {
        return previous;
    }

    @Override
    public Integer getCurrent() {
        return current;
    }

    @Override
    public void setPage(Integer extpage) {
        pagination = extpage;
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
        page.setTechnical(location.getName(Language.DE));
        page.setDescriptionMap(location.getDescriptionMap());
    }

    private void getDBCount() {
        LOGGER.debug("getDBCount() aufgerufen.");
        dbQuantity = this.eventDaoLocal.countAll(locationUuid);
        LOGGER.debug("DbQuantity: " + dbQuantity);
        // Berechne die Maximal mögliche Seitenzahl
        maxPages = (dbQuantity % TO == 0) ? (dbQuantity / TO) : (dbQuantity / TO) + 1;
        LOGGER.debug("MaxPages: " + maxPages);
    }// Ende getDBCount()

    /**
     * Methode die Start und Enpunkt der Liste und die vorherige beziehungweise
     * die nächste Seitenzahl berechnet.
     */
    private void calculate() {
        if(pagination == 1) {
            previous = null;
            next = (2 <= maxPages) ? 2 : null;
            from = 0;
            current = pagination;
        } else {
            previous = pagination -1;
            next = (pagination + 1 <= maxPages) ? pagination + 1 : null;
            from = (pagination - 1) * TO;
            current = pagination;
        }
    }// Ende calculate()

    
}// Ende class
