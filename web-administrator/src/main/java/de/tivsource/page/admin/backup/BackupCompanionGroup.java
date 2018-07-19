package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;
import de.tivsource.page.entity.enumeration.Language;

public class BackupCompanionGroup {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupCompanionGroup.class);

	private static CompanionGroupDaoLocal companionGroupDaoLocal;

	public static void setCompanionGroupDaoLocal(CompanionGroupDaoLocal companionGroupDaoLocal) {
        BackupCompanionGroup.companionGroupDaoLocal = companionGroupDaoLocal;
    }

    public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
		// Datei Kram
		File backupFile = new File("/tmp/companionGroup.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|uuid(de)|name(de)|description(de)|keywords(de)|uuid(en)|name(en)|description(en)|keywords(en)|visible|created|modified|modifiedBy|modifiedAddress|picture|pictureOnPage|technical|orderNumber|");

    	Iterator<CompanionGroup> typeIterator = companionGroupDaoLocal.findAll(0, companionGroupDaoLocal.countAll()).iterator();
    	while(typeIterator.hasNext()) {
    	    CompanionGroup next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(CompanionGroup next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|
	    // visible|created|modified|modifiedBy|modifiedAddress|picture|
	    // pictureOnPage|technical|orderNumber|
	    
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

        nextString.append(next.getPicture().getUuid());
        nextString.append("|");

        nextString.append(next.getPictureOnPage().toString());
        nextString.append("|");

        nextString.append(next.getTechnical());
        nextString.append("|");

        nextString.append(next.getOrderNumber().toString());
        nextString.append("|");

		return nextString.toString();
	}

}// Ende class
