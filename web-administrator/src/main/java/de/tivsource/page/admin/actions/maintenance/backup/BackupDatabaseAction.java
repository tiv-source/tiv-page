package de.tivsource.page.admin.actions.maintenance.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.backup.BackupZipFile;
import de.tivsource.page.admin.services.PropertyService;
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
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
public class BackupDatabaseAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4395588224647598044L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupDatabaseAction.class);

	@Autowired
    @Qualifier("galleryDaoLocal")
    private GalleryDaoLocal galleryDaoLocal;

	@Autowired
    @Qualifier("pictureDaoLocal")
    private PictureDaoLocal pictureDaoLocal;

	@Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("roleDaoLocal")
    private RoleDaoLocal roleDaoLocal;

    @Autowired
    @Qualifier("userDaoLocal")
    private UserDaoLocal userDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

    @Autowired
    @Qualifier("locationDaoLocal")
    private LocationDaoLocal locationDaoLocal;

    @Autowired
    @Qualifier("eventDaoLocal")
    private EventDaoLocal eventDaoLocal;

    @Autowired
    @Qualifier("messageDaoLocal")
    private MessageDaoLocal messageDaoLocal;

    @Autowired
    @Qualifier("newsDaoLocal")
    private NewsDaoLocal newsDaoLocal;
    
    @Autowired
    @Qualifier("reservationDaoLocal")
    private ReservationDaoLocal reservationDaoLocal;

    @Autowired
    @Qualifier("vacancyDaoLocal")
    private VacancyDaoLocal vacancyDaoLocal;

    @Autowired
    @Qualifier("manualDaoLocal")
    private ManualDaoLocal manualDaoLocal;

    @Autowired
    @Qualifier("appointmentDaoLocal")
    private AppointmentDaoLocal appointmentDaoLocal;

    @Autowired
    @Qualifier("companionGroupDaoLocal")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    @Autowired
    @Qualifier("companionDaoLocal")
    private CompanionDaoLocal companionDaoLocal;

    @Autowired
    @Qualifier("sliderDaoLocal")
    private SliderDaoLocal sliderDaoLocal;

	private InputStream fileStream;

    @Autowired
    public BackupDatabaseAction(PropertyService propertyService) {
        super(propertyService);
    }

	@Override
    @Actions({
        @Action(
        		value = "database", 
        		results = { @Result(
        				           name = "success", 
        				           type="stream", 
        				           params={"contentType", "text/plain", "inputName", "fileStream", "contentDisposition", "attachment;filename=tiv_page_database.zip"}
        				           ) }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	BackupZipFile.setGalleryDaoLocal(galleryDaoLocal);
    	BackupZipFile.setPictureDaoLocal(pictureDaoLocal);
    	BackupZipFile.setPageDaoLocal(pageDaoLocal);
    	BackupZipFile.setRoleDaoLocal(roleDaoLocal);
    	BackupZipFile.setUserDaoLocal(userDaoLocal);
    	BackupZipFile.setPropertyDaoLocal(propertyDaoLocal);
    	BackupZipFile.setLocationDaoLocal(locationDaoLocal);
    	BackupZipFile.setEventDaoLocal(eventDaoLocal);
    	BackupZipFile.setMessageDaoLocal(messageDaoLocal);
    	BackupZipFile.setNewsDaoLocal(newsDaoLocal);
    	BackupZipFile.setReservationDaoLocal(reservationDaoLocal);
    	BackupZipFile.setVacancyDaoLocal(vacancyDaoLocal);
    	BackupZipFile.setManualDaoLocal(manualDaoLocal);
    	BackupZipFile.setAppointmentDaoLocal(appointmentDaoLocal);
    	BackupZipFile.setCompanionGroupDaoLocal(companionGroupDaoLocal);
        BackupZipFile.setCompanionDaoLocal(companionDaoLocal);
        BackupZipFile.setSliderDaoLocal(sliderDaoLocal);
    	File backupFile = BackupZipFile.getZipFile();
    	fileStream = new FileInputStream(backupFile);
    	backupFile.delete();
    	return SUCCESS;
    }
    
    public InputStream getFileStream() {
		return fileStream;
	}
    
}// Ende class
