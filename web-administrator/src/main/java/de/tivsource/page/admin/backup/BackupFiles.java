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

import org.apache.log4j.Logger;

/**
 * @author Marc Michele
 *
 */
public class BackupFiles {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupFiles.class);

    private static byte[] buffer = new byte[1024];

    public static File getZipFile() throws IOException {
        LOGGER.info("getZipFile() aufgerufen.");

        // Zip-Datei erstellen und Stream bereitstellen.
        File zipFile = File.createTempFile("complete_tivpage", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        File folder = new File("/srv/www/htdocs/uploads/");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                /*
                 * Add File to Zipfile
                 */
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
        file.delete();
    }

    
    
}// Ende class
