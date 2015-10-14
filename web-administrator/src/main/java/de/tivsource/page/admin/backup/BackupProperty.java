package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

public class BackupProperty {

	private static final int MAX = 1500;
	
	private static PropertyDaoLocal propertyDaoLocal;

    public static void setPropertyDaoLocal(PropertyDaoLocal propertyDaoLocal) {
        BackupProperty.propertyDaoLocal = propertyDaoLocal;
    }

    public static File getBackupFile() throws IOException {

		// Datei Kram
		File backupFile = new File("/tmp/property.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => key|value||");

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

		// uuid|technical|created|modified|modifiedBy|ip|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getKey());
		nextString.append("|");

		nextString.append(next.getValue());
		nextString.append("|");

		return nextString.toString();
	}

}// Ende class
