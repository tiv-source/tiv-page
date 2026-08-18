package de.tivsource.page.user.actions.course;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;

/**
 * TODO: Muss noch ausgearbeitet werden (iCal4j)
 * @author Marc Michele
 *
 */

@TilesDefinitions({
  @TilesDefinition(name="course", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/course.jsp"),
    @TilesPutAttribute(name = "css",        value = "/WEB-INF/tiles/active/css/course.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/course.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/course.jsp")
  })
})
public class ICSAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 6773842640719364262L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ICSAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    private Course course;

    private String courseUuid;

    private InputStream inputStream;

    private String contentDisposition;

    @Override
    @Actions({
        @Action(value = "*/ics", results = {
            @Result(
                name = "success",
                type="stream",
                params= {
                    "contentType", "text/calendar;charset=UTF-8",
                    "inputName", "inputStream",
                    "contentDisposition", "${contentDisposition}",
                    "bufferSize", "1024"
                }
            ),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        courseUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Course Uuid: " + courseUuid);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        courseUuid = courseUuid.replaceAll("/ics.html", "");
        courseUuid = courseUuid.replaceAll("/course/", "");
            
        LOGGER.info("Course Uuid: " + courseUuid);

        /*
         * Wenn die Appointment Uuid keine nicht erlaubten Zeichen enthält und es
         * das Appointment Objekt mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(courseUuid) && courseDaoLocal.isCourseUuid(courseUuid)) {
            LOGGER.info("gültige Course Uuid.");
            course = courseDaoLocal.findByUuid(courseUuid);
            /*
             * Wenn das Appointment Objekt sichbar sein soll und auch schon das
             * Anzeigedtaum erreicht hat wird dieser Block ausgefüht.
             */
            if(course.getVisible()) {
                
                Calendar calendar = new Calendar();
                calendar.add(new ProdId("-//T.I.V. Consulting GmbH//czastka.academy//DE"));
                calendar.add(ImmutableVersion.VERSION_2_0);
                calendar.add(ImmutableCalScale.GREGORIAN);

                // Zeitzonen-Registry laden
                TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
                // Deutsche Zeitzone holen
                VTimeZone tzBerlin = registry.getTimeZone("Europe/Berlin").getVTimeZone();
                // Zeitzonen-Definition zum Kalender hinzufügen
                calendar.add(tzBerlin);

                Iterator<VEvent> events = convert(course).iterator();
                while(events.hasNext()) {
                    VEvent next = events.next();
                    calendar.add(next);
                }
                
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                CalendarOutputter outputter = new CalendarOutputter();
                outputter.output(calendar, buffer);
                inputStream = new ByteArrayInputStream(buffer.toByteArray());;

                String fileName = "kurs_termin.ics"; 
                this.contentDisposition = "attachment; filename=\"" + fileName + "\"";

                
                return SUCCESS;
            }
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return null;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

    private List<VEvent> convert(Course course) {
        List<VEvent> events = new ArrayList<VEvent>();
        Iterator<CourseUnit> units = course.getUnits().iterator();
        while(units.hasNext()) {
            CourseUnit next = units.next();
            events.add(convert(next, course));
        }
        return events;
    }

    private VEvent convert(CourseUnit courseUnit, Course course) {
        // Zeitzone für die Einheit festlegen
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        // Start Zeit der Einheit aufbereiten
        LocalDateTime localDateTimeStart = new java.sql.Timestamp(courseUnit.getPointInTime().getTime()).toLocalDateTime();
        LOGGER.info(courseUnit.getName(getLanguage()) + " => StartZeit => " + localDateTimeStart);
        ZonedDateTime start = localDateTimeStart.atZone(zoneId);
        // End Zeit der Einheit aufbereiten
        LocalDateTime localDateTimeEnd = new java.sql.Timestamp(courseUnit.getEndDate().getTime()).toLocalDateTime();
        LOGGER.info(courseUnit.getName(getLanguage()) + " => EndZeit => " + localDateTimeEnd);
        ZonedDateTime end = localDateTimeEnd.atZone(zoneId);
        // Event erzeugen
        VEvent vevent = new VEvent(start, end, course.getName(getLanguage()) +" (" + course.getNumber() + "): " + courseUnit.getName(getLanguage()));
        vevent.add(new net.fortuna.ical4j.model.property.Location(convertToLocationString(course.getLocation())));
        vevent.add(new Description(
                courseUnit.getName(getLanguage()) + "\n" + "\n" +
                courseUnit.getDescription(getLanguage()
                        )));
        vevent.add(new Uid(courseUnit.getUuid()));
        // Eine Stunden vorher anzeigen
        VAlarm alarm1Std = new VAlarm();
        alarm1Std.add(new net.fortuna.ical4j.model.property.Action("DISPLAY"));
        alarm1Std.add(new Trigger(Duration.ofMinutes(-60)));
        alarm1Std.add(new Description("Erinnerung: Der Kurs '" + course.getName(getLanguage()) + "' beginnt in einer Stunde!"));
        vevent.add(alarm1Std);

        // 24 Stunden vorher 
        VAlarm alarm24Std = new VAlarm();
        alarm1Std.add(new net.fortuna.ical4j.model.property.Action("DISPLAY"));
        alarm24Std.add(new Trigger(Duration.ofDays(-1)));
        alarm24Std.add(new Description("Erinnerung: Morgen beginnt der Kurs '" + course.getName(getLanguage()) + "'!"));
        vevent.add(alarm24Std);
        
        return vevent;
    }// Ende convert(CourseUnit courseUnit, Location location)

    private String convertToLocationString(Location location) {
        StringBuffer sb = new StringBuffer();
        sb.append(location.getName(getLanguage()));
        sb.append(", ");
        sb.append(location.getAddress().getStreet());
        sb.append(", ");
        sb.append(location.getAddress().getZip());
        sb.append(" ");
        sb.append(location.getAddress().getCity());
        sb.append(", ");
        sb.append(location.getAddress().getCountry());
        return sb.toString();
    }// Ende convertToLocationString(Location location)

}// Ende class
