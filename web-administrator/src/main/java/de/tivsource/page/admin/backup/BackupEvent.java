package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.event.Event;

public class BackupEvent {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupEvent.class);

	private static final int MAX = 1500;
	
	private static EventDaoLocal eventDaoLocal;

    public static void setEventDaoLocal(EventDaoLocal eventDaoLocal) {
        BackupEvent.eventDaoLocal = eventDaoLocal;
    }

    public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
		// Datei Kram
		File backupFile = new File("/tmp/event.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|uuid(de)|name(de)|description(de)|keywords(de)|uuid(en)|name(en)|description(en)|keywords(en)|visible|created|modified|modifiedBy|modifiedAddress|price|beginning|ending|deadline|location|reservation|picture|");

    	Iterator<Event> typeIterator = eventDaoLocal.findAll(0, MAX).iterator();
    	while(typeIterator.hasNext()) {
    	    Event next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Event next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|
	    // visible|created|modified|modifiedBy|modifiedAddress|price|beginning|ending|deadline|location|reservation|
	    
		StringBuffer nextString = new StringBuffer();

		
		nextString.append(next.getUuid());
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

        
        nextString.append(next.getPrice().toString());
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getBeginning()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getEnding()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getDeadline()));
        nextString.append("|");

        nextString.append(next.getLocation().getUuid());
        nextString.append("|");

        nextString.append(next.getReservation().toString());
        nextString.append("|");

        nextString.append(next.getPicture());
        nextString.append("|");

		return nextString.toString();
	}

}// Ende class
