/**
 * 
 */
package de.tivsource.page.helper.odf.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.odftoolkit.simple.SpreadsheetDocument;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.helper.odf.CreateReservationODF;

/**
 * @author Marc Michele
 *
 */
public class TestODF {

	private static List<Reservation> reservations;
	static {
		reservations = new ArrayList<Reservation>();
	}

	
	
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        for (int i=0; i<10 ; i++) {
            reservations.add(generateData(i));
        }
        
        CreateReservationODF createReservationODF = new CreateReservationODF();
        SpreadsheetDocument outputDocument = createReservationODF.create(reservations);
        outputDocument.save("test.ods");
    }

    private static Reservation generateData(Integer input) {
    	Reservation reservation = new Reservation();
        reservation.setConfirmed(false);
        reservation.setCreated(new Date());
        reservation.setEmail(input + "keinspam@tivsource.de");
        reservation.setEvent(generateEvent(input));
        reservation.setFirstname("TestVorname" + input);
        reservation.setGender(input % 2 == 0 ? true : false);
        reservation.setCreatedAddress("192.168.0." + input);
        reservation.setLastname("TestNachname" + input);
        reservation.setQuantity(input);
        reservation.setTelephone("005-40-757575" + input);
        reservation.setTime(new Date());
        reservation.setUuid(UUID.randomUUID().toString());
        reservation.setWishes("Test Wünsche, hier stehen die Test Wünsche\nHoffen wir das die nächste Zeile auch funktioniert\nDas hier hat die Nummer " + input);
        return reservation;
    }

    private static Event generateEvent(Integer input) {
    	Event event = new Event();
    	event.setBeginning(new Date());
    	event.setCreated(new Date());
    	event.setDeadline(new Date());
    	event.setDescriptionMap(createDescriptionMap("Event", input, event));
    	event.setEnding(new Date());
    	event.setLocation(generateLocation(input));
    	event.setMaxPersons(100 + input);
    	event.setModified(new Date());
    	event.setModifiedAddress("192.168.1." + input);
    	event.setModifiedBy("TestUser" + input);
    	event.setPicture(null);
    	event.setPiwikGoal(10);
    	event.setPrice(new BigDecimal(input));
    	event.setReservation(true);
    	event.setReservations(reservations);
    	event.setUuid(UUID.randomUUID().toString());
    	event.setVisible(true);
    	return event;
    }// generateEvent(Integer)

    private static Location generateLocation(Integer input) {
    	Location location = new Location();
    	location.setAddress(null);
    	location.setContactDetails(null);
    	location.setCreated(new Date());
    	location.setDescriptionMap(createDescriptionMap("Location", input, location));
    	location.setEvent(true);
    	location.setEvents(null);
    	location.setLatitude("14.00");
    	location.setLongitude("7.00");
    	location.setModified(new Date());
    	location.setModifiedAddress("192.168.2." + input);
    	location.setModifiedBy("LocationTestUser" + input);
    	location.setOpeningHours(null);
    	location.setPicture(null);
    	location.setUuid(UUID.randomUUID().toString());
    	location.setVacancies(null);
    	location.setVisible(true);
    	return location;
    }

    private static Map<Language, Description> createDescriptionMap(String prefix, Integer counter, NamingItem namingItem) {
        Map<Language, Description> descriptionMap = new HashMap<Language, Description>();
        descriptionMap.put(Language.DE, createDescription(
        		UUID.randomUUID().toString(), prefix + "Name" + counter, prefix + "Description" + counter, 
        		prefix + "Keywords" + counter, Language.DE, namingItem));
        descriptionMap.put(Language.EN, createDescription(
        		UUID.randomUUID().toString(), prefix + "Name" + counter, prefix + "Description" + counter, 
        		prefix + "Keywords" + counter, Language.EN, namingItem));
        return descriptionMap;
    }
    
    private static Description createDescription(
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

}// Ende class
