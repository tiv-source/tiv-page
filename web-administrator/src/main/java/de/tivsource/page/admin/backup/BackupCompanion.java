package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.entity.companion.Companion;

public class BackupCompanion {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupCompanion.class);

	private static CompanionDaoLocal companionDaoLocal;

	public static void setCompanionDaoLocal(CompanionDaoLocal companionDaoLocal) {
        BackupCompanion.companionDaoLocal = companionDaoLocal;
    }

    public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
		// Datei Kram
		File backupFile = new File("/tmp/companion.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|name|appendix|street|zip|city|country|mobile|telephone|fax|email|homepage|group|visible|created|modified|modifiedBy|modifiedAddress|");

    	Iterator<Companion> typeIterator = companionDaoLocal.findAll(0, companionDaoLocal.countAll()).iterator();
    	while(typeIterator.hasNext()) {
    	    Companion next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Companion next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    // uuid|name|appendix|street|zip|city|country|mobile|telephone|fax|email|homepage|
	    // group|visible|created|modified|modifiedBy|modifiedAddress|
	    
	    
		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getUuid());
		nextString.append("|");

        nextString.append(next.getName());
        nextString.append("|");

        nextString.append(next.getAppendix());
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

        nextString.append(next.getGroup().getUuid());
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


		return nextString.toString();
	}

}// Ende class
