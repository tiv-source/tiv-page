package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;

public class BackupLocation {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupLocation.class);

	private static final int MAX = 1500;
	
	private static LocationDaoLocal locationDaoLocal;

    public static void setLocationDaoLocal(LocationDaoLocal locationDaoLocal) {
        BackupLocation.locationDaoLocal = locationDaoLocal;
    }

    public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
		// Datei Kram
		File backupFile = new File("/tmp/location.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|street|zip|city|country|mobile|telephone|fax|email|homepage|uuid(de)|name(de)|description(de)|keywords(de)|uuid(en)|name(en)|description(en)|keywords(en)|openingHours|latitude|longitude|visible|created|modified|modifiedBy|modifiedAddress|events|picture|order|pictureOnPage|inLocationList|");

    	Iterator<Location> typeIterator = locationDaoLocal.findAll(0, MAX).iterator();
    	while(typeIterator.hasNext()) {
    	    Location next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Location next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// uuid|street|zip|city|country|mobile|telephone|fax|email|homepage|
	    // uuid(de)|name(de)|description(de)|keywords(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|
	    // openingHours|latitude|longitude|visible|created|
	    // modified|modifiedBy|modifiedAddress|events|picture|order|pictureOnPage|inLocationList|
	    
		StringBuffer nextString = new StringBuffer();

		
		nextString.append(next.getUuid());
		nextString.append("|");

		
        nextString.append(next.getAddress().getStreet());
        nextString.append("|");

        nextString.append(next.getAddress().getZip());
        nextString.append("|");

        nextString.append(next.getAddress().getCity());
        nextString.append("|");

        nextString.append(next.getAddress().getCountry());
        nextString.append("|");

        
        nextString.append(next.getContactDetails().getMobile());
        nextString.append("|");

        nextString.append(next.getContactDetails().getTelephone());
        nextString.append("|");

        nextString.append(next.getContactDetails().getFax());
        nextString.append("|");

        nextString.append(next.getContactDetails().getEmail());
        nextString.append("|");

        nextString.append(next.getContactDetails().getHomepage());
        nextString.append("|");



        nextString.append(next.getDescriptionObject(Language.DE).getUuid());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getName());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getDescription());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getKeywords());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescriptionObject(Language.EN).getName());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getDescription());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getKeywords());
        nextString.append("|");

        
        nextString.append(convertOpeningHours(next.getOpeningHours()));
        nextString.append("|");
        
        
        
        nextString.append(next.getLatitude());
        nextString.append("|");

        nextString.append(next.getLongitude());
        nextString.append("|");
        
        nextString.append(next.getVisible().toString());
        nextString.append("|");
        
        nextString.append(simpleDateFormat.format(next.getCreated()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getModified()));
        nextString.append("|");

        nextString.append(next.getModifiedBy());
        nextString.append("|");

        nextString.append(next.getModifiedAddress());
        nextString.append("|");

        nextString.append(next.getEvent().toString());
        nextString.append("|");

        nextString.append(next.getPicture().getUuid());
        nextString.append("|");

        nextString.append(next.getOrder().toString());
        nextString.append("|");

        nextString.append(next.getPictureOnPage().toString());
        nextString.append("|");

        nextString.append(next.getInLocationList().toString());
        nextString.append("|");

		return nextString.toString();
	}

	private static String convertOpeningHours(SortedSet<OpeningHour> openingHours) {

	    LOGGER.info("Anzahl der OpeningHours im SortedSet: " + openingHours.size());
	    
	    StringBuffer hourString = new StringBuffer();

	    Iterator<OpeningHour> iterator = openingHours.iterator();

	    while(iterator.hasNext()) {
	        OpeningHour next = iterator.next();

	        hourString.append(next.getWeekday().toString());
	        hourString.append(";");

            hourString.append(next.getOpen().toString());
            hourString.append(";");

            hourString.append(next.getClose().toString());
            hourString.append("!");
	    }

	    return hourString.toString();
	}
}// Ende class
