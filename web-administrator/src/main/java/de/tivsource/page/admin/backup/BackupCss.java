/**
 * 
 */
package de.tivsource.page.admin.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * @author Marc Michele
 *
 */
public class BackupCss {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupCss.class);

    private static byte[] buffer = new byte[1024];

    private static PropertyDaoLocal propertyDaoLocal;

    public static void setPropertyDaoLocal(PropertyDaoLocal propertyDaoLocal) {
		BackupCss.propertyDaoLocal = propertyDaoLocal;
	}

	public static File getZipFile() throws IOException {
        LOGGER.info("getZipFile() aufgerufen.");
    	String pathToCss = propertyDaoLocal.findByKey("css-path").getValue();
        
        // Zip-Datei erstellen und Stream bereitstellen.
        File zipFile = File.createTempFile("css_tivpage", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        File folder = new File(pathToCss);
        File[] listOfFiles = folder.listFiles();
        LOGGER.debug("Anzahl der Dateien: " + listOfFiles.length);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                LOGGER.debug("Füge die Datei " + listOfFiles[i].getName() + " zur Zip-Datei hinzu.");
                addData(listOfFiles[i], outZipFile, listOfFiles[i].getName());
            } 
        }

        // Schließe die Zip-Datei.
        outZipFile.close();

        return zipFile;
    }

    private static void addData(File file, ZipOutputStream zipOutputStream,
            String filename) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        // Ab hier beginnt der Exhibition Teil
        zipOutputStream.putNextEntry(new ZipEntry(filename));

        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }

        zipOutputStream.closeEntry();
        fileInputStream.close();
    }

    
    
}// Ende class
