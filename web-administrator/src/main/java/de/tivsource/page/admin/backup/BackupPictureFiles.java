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

/**
 * @author Marc Michele
 *
 */
public class BackupPictureFiles {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupPictureFiles.class);

    private static byte[] buffer = new byte[1024];

    public static File getZipFile() throws IOException {
        LOGGER.info("getZipFile() aufgerufen.");

        // Zip-Datei erstellen und Stream bereitstellen.
        File zipFile = File.createTempFile("tivpage_pictures", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        String picturePath = "/var/www/html/pictures/";
        String picturePathFULL = picturePath + "FULL/";
        String picturePathLARGE = picturePath + "LARGE/";
        String picturePathNORMAL = picturePath + "NORMAL/";
        String picturePathTHUMBNAIL = picturePath + "THUMBNAIL/";

        outZipFile.putNextEntry(new ZipEntry("pictures/"));

        outZipFile.putNextEntry(new ZipEntry("pictures/FULL/"));
        File folderFULL = new File(picturePathFULL);
        File[] listOfFilesFULL = folderFULL.listFiles();
        LOGGER.debug("Anzahl der Dateien: " + listOfFilesFULL.length);
        for (int i = 0; i < listOfFilesFULL.length; i++) {
        	if (listOfFilesFULL[i].isFile()) {
                LOGGER.debug("Füge die Datei " + listOfFilesFULL[i].getName() + " zur Zip-Datei hinzu.");
                addData(listOfFilesFULL[i], outZipFile, "pictures/FULL/" + listOfFilesFULL[i].getName());
        	}
        }

        outZipFile.putNextEntry(new ZipEntry("pictures/LARGE/"));
        File folderLARGE = new File(picturePathLARGE);
        File[] listOfFilesLARGE = folderLARGE.listFiles();
        LOGGER.debug("Anzahl der Dateien: " + listOfFilesLARGE.length);
        for (int i = 0; i < listOfFilesLARGE.length; i++) {
        	if (listOfFilesLARGE[i].isFile()) {
                LOGGER.debug("Füge die Datei " + listOfFilesLARGE[i].getName() + " zur Zip-Datei hinzu.");
                addData(listOfFilesLARGE[i], outZipFile, "pictures/LARGE/" + listOfFilesLARGE[i].getName());
        	}
        }
        
        outZipFile.putNextEntry(new ZipEntry("pictures/NORMAL/"));
        File folderNORMAL = new File(picturePathNORMAL);
        File[] listOfFilesNORMAL = folderNORMAL.listFiles();
        LOGGER.debug("Anzahl der Dateien: " + listOfFilesNORMAL.length);
        for (int i = 0; i < listOfFilesNORMAL.length; i++) {
        	if (listOfFilesNORMAL[i].isFile()) {
                LOGGER.debug("Füge die Datei " + listOfFilesNORMAL[i].getName() + " zur Zip-Datei hinzu.");
                addData(listOfFilesNORMAL[i], outZipFile, "pictures/NORMAL/" + listOfFilesNORMAL[i].getName());
        	}
        }

        outZipFile.putNextEntry(new ZipEntry("pictures/THUMBNAIL/"));
        File folderTHUMBNAIL = new File(picturePathTHUMBNAIL);
        File[] listOfFilesTHUMBNAIL = folderTHUMBNAIL.listFiles();
        LOGGER.debug("Anzahl der Dateien: " + listOfFilesTHUMBNAIL.length);
        for (int i = 0; i < listOfFilesTHUMBNAIL.length; i++) {
        	if (listOfFilesTHUMBNAIL[i].isFile()) {
                LOGGER.debug("Füge die Datei " + listOfFilesTHUMBNAIL[i].getName() + " zur Zip-Datei hinzu.");
                addData(listOfFilesTHUMBNAIL[i], outZipFile, "pictures/THUMBNAIL/" + listOfFilesTHUMBNAIL[i].getName());
        	}
        }


        // Schließe die Zip-Datei.
        outZipFile.close();

        return zipFile;
    }

    private static void addData(File file, ZipOutputStream zipOutputStream, String filename) throws IOException {

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
