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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;

/**
 * @author Marc Michele
 *
 */
public class BackupZipFile {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupZipFile.class);

    private static GalleryDaoLocal galleryDaoLocal;

    private static PictureDaoLocal pictureDaoLocal;

    private static PageDaoLocal pageDaoLocal;

    private static UserDaoLocal userDaoLocal;

    private static RoleDaoLocal roleDaoLocal;

    private static PropertyDaoLocal propertyDaoLocal;
    
    private static LocationDaoLocal locationDaoLocal;

    private static EventDaoLocal eventDaoLocal;

    private static MessageDaoLocal messageDaoLocal;

    private static NewsDaoLocal newsDaoLocal;

    private static ReservationDaoLocal reservationDaoLocal;

    private static VacancyDaoLocal vacancyDaoLocal;

    private static ManualDaoLocal manualDaoLocal;

    private static AppointmentDaoLocal appointmentDaoLocal;

    private static CompanionGroupDaoLocal companionGroupDaoLocal;

    private static CompanionDaoLocal companionDaoLocal;
    
    private static byte[] buffer = new byte[1024];

    public static void setGalleryDaoLocal(GalleryDaoLocal galleryDaoLocal) {
		BackupZipFile.galleryDaoLocal = galleryDaoLocal;
	}

	public static void setPictureDaoLocal(PictureDaoLocal pictureDaoLocal) {
		BackupZipFile.pictureDaoLocal = pictureDaoLocal;
	}

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

    public static void setMessageDaoLocal(MessageDaoLocal messageDaoLocal) {
        BackupZipFile.messageDaoLocal = messageDaoLocal;
    }

    public static void setNewsDaoLocal(NewsDaoLocal newsDaoLocal) {
		BackupZipFile.newsDaoLocal = newsDaoLocal;
	}

	public static void setReservationDaoLocal(ReservationDaoLocal reservationDaoLocal) {
        BackupZipFile.reservationDaoLocal = reservationDaoLocal;
    }

    public static void setVacancyDaoLocal(VacancyDaoLocal vacancyDaoLocal) {
        BackupZipFile.vacancyDaoLocal = vacancyDaoLocal;
    }

    public static void setManualDaoLocal(ManualDaoLocal manualDaoLocal) {
		BackupZipFile.manualDaoLocal = manualDaoLocal;
	}

	public static void setAppointmentDaoLocal(AppointmentDaoLocal appointmentDaoLocal) {
        BackupZipFile.appointmentDaoLocal = appointmentDaoLocal;
    }

    public static void setCompanionGroupDaoLocal(CompanionGroupDaoLocal companionGroupDaoLocal) {
        BackupZipFile.companionGroupDaoLocal = companionGroupDaoLocal;
    }

    public static void setCompanionDaoLocal(CompanionDaoLocal companionDaoLocal) {
        BackupZipFile.companionDaoLocal = companionDaoLocal;
    }

    public static File getZipFile() throws IOException {
        LOGGER.info("getZipFile() aufgerufen.");

        // Zip-Datei erstellen und Stream bereitstellen.
        File zipFile = File.createTempFile("complete_tivpage", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        /*
         * Backup Gallery
         */
        BackupGallery.setGalleryDaoLocal(galleryDaoLocal);
        addData(BackupGallery.getBackupFile(), outZipFile, "gallery.csv");

        /*
         * Backup Picture
         */
        BackupPicture.setPictureDaoLocal(pictureDaoLocal);
        addData(BackupPicture.getBackupFile(), outZipFile, "picture.csv");

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

        /*
         * Backup Message
         */
        BackupMessage.setMessageDaoLocal(messageDaoLocal);
        BackupMessage backupMessage = new BackupMessage();
        addMultiData(backupMessage.getBackupFiles(), outZipFile);

        /*
         * Backup News
         */
        BackupNews.setNewsDaoLocal(newsDaoLocal);
        BackupNews backupNews = new BackupNews();
        addMultiData(backupNews.getBackupFiles(), outZipFile);

        /*
         * Backup Reservation
         */
        BackupReservation.setReservationDaoLocal(reservationDaoLocal);
        BackupReservation backupReservation = new BackupReservation();
        addMultiData(backupReservation.getBackupFiles(), outZipFile);

        /*
         * Backup Vacancy
         */
        BackupVacancy.setVacancyDaoLocal(vacancyDaoLocal);
        BackupVacancy backupVacancy = new BackupVacancy();
        addMultiData(backupVacancy.getBackupFiles(), outZipFile);

        /*
         * Backup Manual
         */
        BackupManual.setManualDaoLocal(manualDaoLocal);
        BackupManual backupManual = new BackupManual();
        addMultiData(backupManual.getBackupFiles(), outZipFile);

        /*
         * Backup Appointment
         */
        BackupAppointment.setAppointmentDaoLocal(appointmentDaoLocal);
        BackupAppointment backupAppointment = new BackupAppointment();
        addMultiData(backupAppointment.getBackupFiles(), outZipFile);

        /*
         * Backup CompanionGroup
         */
        BackupCompanionGroup.setCompanionGroupDaoLocal(companionGroupDaoLocal);;
        addData(BackupCompanionGroup.getBackupFile(), outZipFile, "companionGroup.csv");

        /*
         * Backup Companion
         */
        BackupCompanion.setCompanionDaoLocal(companionDaoLocal);;
        addData(BackupCompanion.getBackupFile(), outZipFile, "companion.csv");

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
