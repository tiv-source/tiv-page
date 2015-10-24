/**
 * 
 */
package de.tivsource.page.restore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Logger;

import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Address;
import de.tivsource.page.entity.location.ContactDetails;
import de.tivsource.page.entity.location.HourMinute;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.enumeration.Weekday;

/**
 * @author Marc Michele
 *
 */
public class RestoreLocation {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger("INFO");

    private LocationDaoLocal locationDaoLocal;

    public RestoreLocation(LocationDaoLocal locationDaoLocal) {
        super();
        this.locationDaoLocal = locationDaoLocal;
    }

    public void generate(InputStream inputStream) {
        cleanup();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    Location location = convert(line);
                    locationDaoLocal.merge(location);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private Location convert(String line) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // uuid|
        // street|zip|city|country|
        // mobile|telephone|fax|email|homepage|
        // uuid(de)|name(de)|description(de)|keywords(de)|
        // uuid(en)|name(en)|description(en)|keywords(en)|
        // openingHours|
        // latitude|longitude|visible|created|modified|modifiedBy|ip|events|

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // Erstelle Announcement-Objekt
        Location location = new Location();

        location.setUuid(items[0]);
        location.setAddress(createAddress(items[1], items[2], items[3], items[4]));
        location.setContactDetails(createContactDetails(items[5], items[6], items[7], items[8], items[9]));

        Map<Language, Description> descriptionMap = new HashMap<Language, Description>();
        descriptionMap.put(Language.DE, createDescription(
                items[10], items[11], items[12], 
                items[13], Language.DE, location));
        descriptionMap.put(Language.EN, createDescription(
                items[14], items[15], items[16], 
                items[17], Language.EN, location));
        location.setDescriptionMap(descriptionMap);

        System.out.println("OpeningHourString: " + items[18]);
        
        location.setOpeningHours(createOpeningHour(items[18], location));

        location.setLatitude(items[19]);
        location.setLongitude(items[20]);
        location.setVisible(items[21].equals("true") ? true : false);
        location.setCreated(convertDateString(items[22]));
        location.setModified(convertDateString(items[23]));
        location.setModifiedBy(items[24]);
        location.setIp(items[25]);
        location.setEvents(items[26].equals("true") ? true : false);

        return location;
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

    private Address createAddress(String street, String zip, String city, String country) {
        // street|zip|city|country|
        Address address = new Address();
        address.setStreet(street);
        address.setZip(zip);
        address.setCity(city);
        address.setCountry(country);
        return address;
    }

    private ContactDetails createContactDetails(String mobile, String telephone, String fax, String email, String homepage) {
        // mobile|telephone|fax|email|homepage|
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setMobile(mobile);
        contactDetails.setTelephone(telephone);
        contactDetails.setFax(fax);
        contactDetails.setEmail(email);
        contactDetails.setHomepage(homepage);
        return contactDetails;
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

    private SortedSet<OpeningHour> createOpeningHour(String input, Location location) {

        // Erzeuge SortedSet.
        SortedSet<OpeningHour> sortedSet = new TreeSet<OpeningHour>();
        
        String[] csvOpeningHours = input.split("!");

        LOGGER.info("Inhalt InputString: " + input);
        LOGGER.info("Länge des csvOpeningHours Arrays: " + csvOpeningHours.length);
        if(input.length() > 0) {
            // Laufe durch das Array und füge die Tags der Tag-Liste hinzu.
            for (int i = 0; i < csvOpeningHours.length; i++) {
                // Zerlege CSV-String.
                String[] csvOpeningHour = csvOpeningHours[i].split(";");
                // Erstelle OpeningHour
                OpeningHour openingHour = new OpeningHour();
                openingHour.setUuid(UUID.randomUUID().toString());
                openingHour.setLocation(location);
                openingHour.setWeekday(Weekday.valueOf(csvOpeningHour[0]));
                openingHour.setOpen(createHourMinute(csvOpeningHour[1]));
                openingHour.setClose(createHourMinute(csvOpeningHour[2]));
                sortedSet.add(openingHour);
            }// Ende for
        }

        return sortedSet;
    }

    private HourMinute createHourMinute(String input) {
        String[] csvHourMinute = input.split(":");
        HourMinute hourMinute = new HourMinute();
        hourMinute.setHour(Integer.parseInt(csvHourMinute[0]));
        hourMinute.setMinute(Integer.parseInt(csvHourMinute[1]));
        return hourMinute;
    }

    private void cleanup() {
        if(locationDaoLocal.countAll() > 0) {
            Iterator<Location> locationIterator = locationDaoLocal.findAll(0, locationDaoLocal.countAll()).iterator();
            while(locationIterator.hasNext()) {
                Location next = locationIterator.next();
                locationDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()

}// Ende class
