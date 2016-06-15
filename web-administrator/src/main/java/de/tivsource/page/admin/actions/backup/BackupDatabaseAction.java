package de.tivsource.page.admin.actions.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.backup.BackupZipFile;
import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
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

	@InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

	@InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

	@InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="MessageDao")
    private MessageDaoLocal messageDaoLocal;

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;
    
    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

	private InputStream fileStream;

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
    	File backupFile = BackupZipFile.getZipFile();
    	fileStream = new FileInputStream(backupFile);
    	backupFile.delete();
    	return SUCCESS;
    }
    
    public InputStream getFileStream() {
		return fileStream;
	}
    
}// Ende class
