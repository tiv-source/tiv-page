/**
 * 
 */
package de.tivsource.page.admin.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * @author Marc Michele
 *
 */
public class BackupZipFile {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupZipFile.class);

    private static PageDaoLocal pageDaoLocal;

    private static UserDaoLocal userDaoLocal;

    private static RoleDaoLocal roleDaoLocal;

    private static PropertyDaoLocal propertyDaoLocal;
    
    private static LocationDaoLocal locationDaoLocal;

    private static EventDaoLocal eventDaoLocal;

    private static byte[] buffer = new byte[1024];

    public static void setPageDaoLocal(PageDaoLocal pageDaoLocal) {
        BackupZipFile.pageDaoLocal = pageDaoLocal;
    }

    public static void setUserDaoLocal(UserDaoLocal userDaoLocal) {
        BackupZipFile.userDaoLocal = userDaoLocal;
    }

    public static void setRoleDaoLocal(RoleDaoLocal roleDaoLocal) {
        BackupZipFile.roleDaoLocal = roleDaoLocal;
    }

    public static void setPropertyDaoLocal(PropertyDaoLocal propertyDaoLocal) {
        BackupZipFile.propertyDaoLocal = propertyDaoLocal;
    }

    public static void setLocationDaoLocal(LocationDaoLocal locationDaoLocal) {
        BackupZipFile.locationDaoLocal = locationDaoLocal;
    }

    public static void setEventDaoLocal(EventDaoLocal eventDaoLocal) {
        BackupZipFile.eventDaoLocal = eventDaoLocal;
    }

    public static File getZipFile() throws IOException {
        LOGGER.info("getZipFile() aufgerufen.");

        // Zip-Datei erstellen und Stream bereitstellen.
        File zipFile = File.createTempFile("complete_tivpage", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        /*
         * Backup Page
         */
        BackupPage.setPageDaoLocal(pageDaoLocal);
        BackupPage backupPage = new BackupPage();
        addMultiData(backupPage.getBackupFiles(), outZipFile);

        /*
         * Backup Role
         */
        BackupRole.setRoleDaoLocal(roleDaoLocal);
        addData(BackupRole.getBackupFile(), outZipFile, "role.csv");

        /*
         * Backup User
         */
        BackupUser.setUserDaoLocal(userDaoLocal);
        addData(BackupUser.getBackupFile(), outZipFile, "user.csv");

        /*
         * Backup Property
         */
        BackupProperty.setPropertyDaoLocal(propertyDaoLocal);
        addData(BackupProperty.getBackupFile(), outZipFile, "property.csv");

        /*
         * Backup Location
         */
        BackupLocation.setLocationDaoLocal(locationDaoLocal);
        addData(BackupLocation.getBackupFile(), outZipFile, "location.csv");

        /*
         * Backup Event
         */
        BackupEvent.setEventDaoLocal(eventDaoLocal);;
        addData(BackupEvent.getBackupFile(), outZipFile, "event.csv");

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

    private static void addMultiData(List<File> files,
            ZipOutputStream zipOutputStream) throws IOException {
        Iterator<File> fileIterator = files.iterator();
        while (fileIterator.hasNext()) {
            File next = fileIterator.next();
            addData(next, zipOutputStream, next.getName());
        }
    }

}// Ende class
