package de.tivsource.page.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

public class BackupProperty {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupProperty.class);

    private PropertyDaoLocal propertyDaoLocal;

    public BackupProperty(PropertyDaoLocal propertyDaoLocal) {
        super();
        this.propertyDaoLocal = propertyDaoLocal;
    }

    public File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");

        // Erzeuge eine zufällige UUID
        String randomId = UUID.randomUUID().toString();
        
        // Datei Kram
        File backupFile = new File("/tmp/property_" + randomId + ".csv");
        FileWriter backupFileWriter = new FileWriter(backupFile);
        BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

        // Format Definition
        backupFileWriterOut.write("[Format Definition] => key|value|created|modified|modifiedBy|modifiedAddress|");

        Iterator<Property> typeIterator = propertyDaoLocal.findAll(0, propertyDaoLocal.countAll()).iterator();
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

        // key|value|created|modified|modifiedBy|modifiedAddress|
        StringBuffer nextString = new StringBuffer();

        nextString.append(next.getKey());
        nextString.append("|");

        nextString.append(next.getValue());
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
