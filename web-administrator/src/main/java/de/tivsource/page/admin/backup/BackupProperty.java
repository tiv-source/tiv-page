package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

public class BackupProperty {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupProperty.class);

	private static final int MAX = 1500;
	
	private static PropertyDaoLocal propertyDaoLocal;

    public static void setPropertyDaoLocal(PropertyDaoLocal propertyDaoLocal) {
        BackupProperty.propertyDaoLocal = propertyDaoLocal;
    }

    public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");

		// Datei Kram
		File backupFile = new File("/tmp/property.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => key|value|modified|modifiedBy|modifiedAddress|");

    	Iterator<Property> typeIterator = propertyDaoLocal.findAll(0, MAX).iterator();
    	while(typeIterator.hasNext()) {
    	    Property next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Property next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// uuid|technical|created|modified|modifiedBy|ip|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getKey());
		nextString.append("|");

		nextString.append(next.getValue());
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
