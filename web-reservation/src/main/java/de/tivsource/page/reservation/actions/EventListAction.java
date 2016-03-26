package de.tivsource.page.reservation.actions;

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
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;

public class EventListAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6236431708460575442L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EventListAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    /**
     * Location Uuid im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String locationUuid;

    private List<Event> events;

    private Location location;

    public Location getLocation() {
        return location;
    }

    @Override
    @Actions({
        @Action(value = "*/list_create", results = {
            @Result(name = "success", type = "tiles", location = "list_create"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        }),
        @Action(value = "*/list_edit", results = {
                @Result(name = "success", type = "tiles", location = "list_edit"),
                @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
                @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        }),
        @Action(value = "*/list_view", results = {
                @Result(name = "success", type = "tiles", location = "list_view"),
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
        
        
        locationUuid = locationUuid.replaceAll("/list_create.html", "");
        locationUuid = locationUuid.replaceAll("/list_edit.html", "");
        locationUuid = locationUuid.replaceAll("/list_view.html", "");
        locationUuid = locationUuid.replaceAll("/", "");
            
        LOGGER.info("LocationUuid: " + locationUuid);

        /*
         * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
         * die Location mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(locationUuid) && locationDaoLocal.isEventLocation(locationUuid)) {
            LOGGER.info("gültige Location Uuid.");
            location = locationDaoLocal.findByUuid(locationUuid);
            events = eventDaoLocal.findAll(locationUuid, 0, eventDaoLocal.countAll(locationUuid));
            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    public List<Event> getList() {
        return events;
    }

	public Integer countQuantity(String uuid) {
		LOGGER.info("countQuantity aufgerufen.");
		LOGGER.info("Event UUID: " + uuid);
		return reservationDaoLocal.countQuantity(uuid);
	}

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class
