package de.tivsource.page.admin.actions.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.backup.BackupZipFile;
import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
public class BackupAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4395588224647598044L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupAction.class);

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
    	BackupZipFile.setPageDaoLocal(pageDaoLocal);
    	BackupZipFile.setRoleDaoLocal(roleDaoLocal);
    	BackupZipFile.setUserDaoLocal(userDaoLocal);
    	BackupZipFile.setPropertyDaoLocal(propertyDaoLocal);
    	BackupZipFile.setLocationDaoLocal(locationDaoLocal);
    	BackupZipFile.setEventDaoLocal(eventDaoLocal);
    	File backupFile = BackupZipFile.getZipFile();
    	fileStream = new FileInputStream(backupFile);
    	backupFile.delete();
    	return SUCCESS;
    }
    
    public InputStream getFileStream() {
		return fileStream;
	}
    
}// Ende class
