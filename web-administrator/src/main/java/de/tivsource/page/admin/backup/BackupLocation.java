package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.SortedSet;

import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;

public class BackupLocation {

	private static final int MAX = 1500;
	
	private static LocationDaoLocal locationDaoLocal;

    public static void setLocationDaoLocal(LocationDaoLocal locationDaoLocal) {
        BackupLocation.locationDaoLocal = locationDaoLocal;
    }

    public static File getBackupFile() throws IOException {

		// Datei Kram
		File backupFile = new File("/tmp/location.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|street|zip|city|country|mobile|telephone|fax|email|homepage|uuid(de)|name(de)|description(de)|keywords(de)|uuid(en)|name(en)|description(en)|keywords(en)|openingHours|latitude|longitude|visible|created|modified|modifiedBy|ip|events|");

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

		// uuid|street|zip|city|country|mobile|telephone|fax|email|homepage|uuid(de)|name(de)|description(de)|keywords(de)|uuid(en)|name(en)|description(en)|keywords(en)|openingHours|latitude|longitude|visible|created|modified|modifiedBy|ip|
	    
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

        nextString.append(next.getIp());
        nextString.append("|");

        nextString.append(next.getEvents().toString());
        nextString.append("|");

		return nextString.toString();
	}

	private static String convertOpeningHours(SortedSet<OpeningHour> openingHours) {

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
