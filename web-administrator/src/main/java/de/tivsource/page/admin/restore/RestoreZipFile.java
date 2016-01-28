/**
 * 
 */
package de.tivsource.page.admin.restore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.restore.RestoreEvent;
import de.tivsource.page.restore.RestoreGallery;
import de.tivsource.page.restore.RestoreLocation;
import de.tivsource.page.restore.RestoreMessage;
import de.tivsource.page.restore.RestorePage;
import de.tivsource.page.restore.RestoreProperty;
import de.tivsource.page.restore.RestoreReservation;
import de.tivsource.page.restore.RestoreRole;
import de.tivsource.page.restore.RestoreUser;
import de.tivsource.page.restore.RestoreVacancy;

/**
 * @author Marc Michele
 *
 */
public class RestoreZipFile {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestoreZipFile.class);

    private GalleryDaoLocal galleryDaoLocal;

    private UserDaoLocal userDaoLocal;

    private RoleDaoLocal roleDaoLocal;

    private PageDaoLocal pageDaoLocal;

    private PropertyDaoLocal propertyDaoLocal;

    private LocationDaoLocal locationDaoLocal;

    private EventDaoLocal eventDaoLocal;

    private MessageDaoLocal messageDaoLocal;

    private ReservationDaoLocal reservationDaoLocal;

    private VacancyDaoLocal vacancyDaoLocal;

    private Map<String, InputStream> streams = new HashMap<String, InputStream>();

    private Map<String, InputStream> pageStreams = new HashMap<String, InputStream>();

    private Map<String, InputStream> messageStreams = new HashMap<String, InputStream>();

    private Map<String, InputStream> reservationStreams = new HashMap<String, InputStream>();

    private Map<String, InputStream> vacancyStreams = new HashMap<String, InputStream>();

    public RestoreZipFile(GalleryDaoLocal galleryDaoLocal, UserDaoLocal userDaoLocal, RoleDaoLocal roleDaoLocal,
            PageDaoLocal pageDaoLocal, PropertyDaoLocal propertyDaoLocal,
            LocationDaoLocal locationDaoLocal, EventDaoLocal eventDaoLocal,
            MessageDaoLocal messageDaoLocal,
            ReservationDaoLocal reservationDaoLocal,
            VacancyDaoLocal vacancyDaoLocal) {
        super();
        this.galleryDaoLocal = galleryDaoLocal;
        this.userDaoLocal = userDaoLocal;
        this.roleDaoLocal = roleDaoLocal;
        this.pageDaoLocal = pageDaoLocal;
        this.propertyDaoLocal = propertyDaoLocal;
        this.locationDaoLocal = locationDaoLocal;
        this.eventDaoLocal = eventDaoLocal;
        this.messageDaoLocal = messageDaoLocal;
        this.reservationDaoLocal = reservationDaoLocal;
        this.vacancyDaoLocal = vacancyDaoLocal;
    }

    public void restoreZip(File file) throws IOException {
        LOGGER.info("restoreZip(File file) aufgerufen.");

        ZipFile zipFile = new ZipFile(file);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));

        ZipEntry zipEntry = null;

        while((zipEntry = zipInputStream.getNextEntry()) != null) {
            String fileName = zipEntry.getName();
            if(fileName.startsWith("page")) {
                pageStreams.put(fileName, zipFile.getInputStream(zipEntry));
            } else if(fileName.startsWith("message")) {
                messageStreams.put(fileName, zipFile.getInputStream(zipEntry));
            } else if(fileName.startsWith("reservation")) {
                reservationStreams.put(fileName, zipFile.getInputStream(zipEntry));
            } else if(fileName.startsWith("vacancy")) {
                vacancyStreams.put(fileName, zipFile.getInputStream(zipEntry));
            } else {
                streams.put(fileName, zipFile.getInputStream(zipEntry));
            }
        }// Ende while

        // Stelle Galerien wieder her
        restoreGallery();

        // Stelle Rollen wieder her
        restoreRole();

        // Stelle Benutzer wieder her
        restoreUser();

        // Stelle Seiten wieder her
        restorePage();

        // Stelle Properties wieder her
        restoreProperty();

        // Stelle Location wieder her
        restoreLocation();

        // Stelle Event wieder her
        restoreEvent();

        // Stelle Message wieder her
        restoreMessage();

        // Stelle Reservation wieder her
        restoreReservation();

        // Stelle Vacancy wieder her
        restoreVacancy();

        // Schließe Datei
        zipFile.close();

        // Schließe Datei Stream
        zipInputStream.close();

    }// Ende restoreZip(File file)

    private void restoreGallery() {
        LOGGER.info("restoreGallery() aufgerufen.");
        RestoreGallery restoreGallery = new RestoreGallery(galleryDaoLocal);
        restoreGallery.generate(streams.get("gallery.csv"));
    }

    private void restoreUser() {
        LOGGER.info("restoreUser() aufgerufen.");
        RestoreUser restoreUser = new RestoreUser(userDaoLocal, roleDaoLocal);
        restoreUser.generate(streams.get("user.csv"));
    }

    private void restoreRole() {
        LOGGER.info("restoreRole() aufgerufen.");
        RestoreRole restoreRole = new RestoreRole(roleDaoLocal);
        restoreRole.generate(streams.get("role.csv"));
    }

    private void restorePage() {
        LOGGER.info("restorePage() aufgerufen.");
        RestorePage restorePage = new RestorePage(pageDaoLocal, pageStreams);
        restorePage.generate();
    }

    private void restoreProperty() {
        LOGGER.info("restoreProperty() aufgerufen.");
        RestoreProperty restoreProperty = new RestoreProperty(propertyDaoLocal);
        restoreProperty.generate(streams.get("property.csv"));
    }

    private void restoreLocation() {
        LOGGER.info("restoreLocation() aufgerufen.");
        RestoreLocation restoreLocation = new RestoreLocation(locationDaoLocal);
        restoreLocation.generate(streams.get("location.csv"));
    }

    private void restoreEvent() {
        LOGGER.info("restoreEvent() aufgerufen.");
        RestoreEvent eventLocation = new RestoreEvent(locationDaoLocal, eventDaoLocal);
        eventLocation.generate(streams.get("event.csv"));
    }

    private void restoreMessage() {
        LOGGER.info("restoreMessage() aufgerufen.");
        RestoreMessage restoreMessage = new RestoreMessage(messageDaoLocal, messageStreams);
        restoreMessage.generate();
    }

    private void restoreReservation() {
        LOGGER.info("restoreReservation() aufgerufen.");
        RestoreReservation restoreReservation = new RestoreReservation(reservationDaoLocal, eventDaoLocal, reservationStreams);
        restoreReservation.generate();
    }

    private void restoreVacancy() {
        LOGGER.info("restoreVacancy() aufgerufen.");
        RestoreVacancy vacancyReservation = new RestoreVacancy(vacancyDaoLocal, locationDaoLocal, vacancyStreams);
        vacancyReservation.generate();
    }

}// Ende class
