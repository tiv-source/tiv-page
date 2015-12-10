/**
 * 
 */
package de.tivsource.page.restore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * @author Marc Michele
 *
 */
public class RestoreEvent {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestoreEvent.class);

    private LocationDaoLocal locationDaoLocal;

    private EventDaoLocal eventDaoLocal;

    public RestoreEvent(LocationDaoLocal locationDaoLocal, EventDaoLocal eventDaoLocal) {
        super();
        this.locationDaoLocal = locationDaoLocal;
        this.eventDaoLocal = eventDaoLocal;
    }

    public void generate(InputStream inputStream) {
        LOGGER.debug("generate(InputStream inputStream) aufgerufen");
        cleanup();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    Event event = convert(line);
                    eventDaoLocal.merge(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private Event convert(String line) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // uuid|
        // uuid(de)|name(de)|description(de)|keywords(de)|
        // uuid(en)|name(en)|description(en)|keywords(en)|
        // visible|created|modified|modifiedBy|ip|price|beginning|ending|deadline|location|reservation|picture|

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // Erstelle Announcement-Objekt
        Event event = new Event();

        event.setUuid(items[0]);

        Map<Language, Description> descriptionMap = new HashMap<Language, Description>();
        descriptionMap.put(Language.DE, createDescription(
                items[1], items[2], items[3], 
                items[4], Language.DE, event));
        descriptionMap.put(Language.EN, createDescription(
                items[5], items[6], items[7], 
                items[8], Language.EN, event));
        event.setDescriptionMap(descriptionMap);

        event.setVisible(items[9].equals("true") ? true : false);
        event.setCreated(convertDateString(items[10]));
        event.setModified(convertDateString(items[11]));
        event.setModifiedBy(items[12]);
        event.setIp(items[13]);
        
        event.setPrice(new BigDecimal(items[14]));
        event.setBeginning(convertDateString(items[15]));
        event.setEnding(convertDateString(items[16]));
        event.setDeadline(convertDateString(items[17]));
        
        Location location = locationDaoLocal.findByUuidWidthEvents(items[18]);
        location.getEvents().add(event);
        event.setLocation(location);
        
        event.setReservation(items[19].equals("true") ? true : false);
        event.setPicture(items[20]);
        

        return event;
    }// Ende convert(String line)

    /**
     * Methode zum Konvertieren eines Strings des Formates "1970-12-01 23:59:59" in ein Date-Object. 
     * @param dateString
     * @return
     */
    private Date convertDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private Description createDescription(
            String uuid, String name, String description, 
            String keywords, Language language, NamingItem namingItem) {
        Description pageDescription = new Description();
        pageDescription.setUuid(uuid);
        pageDescription.setName(name);
        pageDescription.setDescription(description);
        pageDescription.setKeywords(keywords);
        pageDescription.setLanguage(language);
        pageDescription.setNamingItem(namingItem);
        return pageDescription;
    }

    private void cleanup() {
        if(eventDaoLocal.countAll() > 0) {
            Iterator<Event> locationIterator = eventDaoLocal.findAll(0, eventDaoLocal.countAll()).iterator();
            while(locationIterator.hasNext()) {
                Event next = locationIterator.next();
                eventDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()

}// Ende class
