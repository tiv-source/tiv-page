package de.tivsource.page.user.actions.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3286347439057757386L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(IndexAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    /**
     * Location Uuid im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String eventUuid;

    private Event event;

    private Page page;

    @Override
    public Page getPage() {
        return page;
    }

    public Event getEvent() {
        return event;
    }

    public List<Date> getTimes() {
        List<Date> times = new ArrayList<Date>();

        // Anfangs Punkt der Zeitreihe
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(event.getBeginning());
        times.add(calendarStart.getTime());
                
        // Endpunkt der Zeitreihe 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getEnding());
        calendar.add(Calendar.MINUTE, -30);
        Date end = calendar.getTime();

        // Datum mit dem gerechnet wird
        Date time = event.getBeginning();
        while (time.before(end)) {
            Calendar calendarTime = Calendar.getInstance();
            calendarTime.setTime(time);
            calendarTime.add(Calendar.MINUTE, 15);
            time = calendarTime.getTime();
            times.add(time);
        }
        
        

        return times;
    }
    
    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "event"),
            @Result(name = "input", type = "tiles", location = "eventDeadline"),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"}) 
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        eventUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("EventUuid: " + eventUuid);
        
        eventUuid = eventUuid.replaceAll("/index.html", "");
        eventUuid = eventUuid.replaceAll("/event/", "");
            
        LOGGER.info("EventUuid: " + eventUuid);

        
        /*
         * Wenn die Event Uuid keine nicht erlaubten Zeichen enthält und es
         * die Event mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(eventUuid) && eventDaoLocal.isEvent(eventUuid)) {
            LOGGER.info("gültige Event Uuid.");
            event = eventDaoLocal.findByUuid(eventUuid);
            // Setze Daten in ein Page Objekt.
            setUpPage();
            Date now = new Date();
            if(event.getDeadline().after(now)) {
                return SUCCESS;
            } else if (event.getBeginning().before(now)) {
                return ERROR;
            } else {
                return INPUT;
            }
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()



    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpPage() {
        page = new Page();
        page.setTechnical(event.getName(Language.DE));
        page.setDescriptionMap(event.getDescriptionMap());
    }
    
}// Ende class
