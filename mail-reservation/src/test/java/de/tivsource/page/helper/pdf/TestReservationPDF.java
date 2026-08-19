/**
 * 
 */
package de.tivsource.page.helper.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Address;
import de.tivsource.page.entity.location.ContactDetails;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 *
 */
public class TestReservationPDF {

    /**
     * @param args
     * @throws IOException 
     * @throws COSVisitorException 
     */
    public static void main(String[] args) throws IOException {
    	Location location = new Location();
    	
    	Address address = new Address();
    	address.setCity("Castrop-Rauxel");
    	address.setCountry("Deutschland");
    	address.setStreet("Dorfstrasse 3");
    	address.setZip("47530");

    	ContactDetails contactDetails = new ContactDetails();
    	contactDetails.setEmail("info@tiv-source.de");
    	contactDetails.setHomepage("http://www.tiv-source.de/");
    	contactDetails.setTelephone("02305-0555555407");

        Map<Language, Description> descriptionMapLocation = new HashMap<Language, Description>();
        descriptionMapLocation.put(Language.DE, createDescription(
                "", "Filiale Dorfstrasse", "", 
                "", Language.DE, location));


        PictureItemImage pictureLocation = new PictureItemImage();
        pictureLocation.setNormal("testfiles/location.png");
        
    	location.setAddress(address);
    	location.setContactDetails(contactDetails);
    	location.setDescriptionMap(descriptionMapLocation);
    	location.setImage(pictureLocation);


    	Event event = new Event();

        PictureItemImage pictureEvent = new PictureItemImage();
        pictureEvent.setNormal("testfiles/event.png");

        Map<Language, Description> descriptionMapEvent = new HashMap<Language, Description>();
        descriptionMapEvent.put(Language.DE, createDescription(
                "", "Eventname hier", "", 
                "", Language.DE, location));

    	event.setBeginning(new Date());
    	event.setDescriptionMap(descriptionMapEvent);
    	event.setLocation(location);
    	event.setImage(pictureEvent);

    	Reservation reservation = new Reservation();
    	/*
    	reservation.setConfirmed(confirmed);
    	reservation.setConfirmedDate(confirmedDate);
    	*/

    	reservation.setEmail("test.die.email@test.die.domaine.test.die.top.level.domain");
    	reservation.setEvent(event);
    	reservation.setFirstname("Max Mustermann");
    	reservation.setGender(true);
    	reservation.setLastname("Wurst-Marmelade");
    	reservation.setQuantity(15);
    	reservation.setTelephone("+49-2302-1211122121");
    	reservation.setTime(new Date());
    	reservation.setUuid("53a78421-e1e5-49e3-a577-81151912433f");

    	File file = new File("target/ReservationPDF.pdf");
        new CreateReservationPDF(
                file,
                reservation,
                new File("testfiles/reservation_header_logo.png"),
                new File("testfiles/reservation_werbung.png"),
                new File("testfiles/bankgothicltbt.ttf")
        );

    }

    private static Description createDescription(
            String uuid, String name, String description, 
            String keywords, Language language, NamingItem namingItem) {
        Description returnDescription = new Description();
        returnDescription.setUuid(uuid);
        returnDescription.setName(name);
        returnDescription.setDescription(description);
        returnDescription.setKeywords(keywords);
        returnDescription.setLanguage(language);
        returnDescription.setNamingItem(namingItem);
        return returnDescription;
    }
    
}// Ende class
