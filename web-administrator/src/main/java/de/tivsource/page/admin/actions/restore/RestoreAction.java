package de.tivsource.page.admin.actions.restore;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.restore.RestoreZipFile;
import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
public class RestoreAction extends EmptyAction {

    /**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4275472563540907020L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestoreAction.class);

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

    @InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private File restoreFile;

    public void setRestoreFile(File restoreFile) {
        this.restoreFile = restoreFile;
    }

    @Override
    @Actions({
        @Action(
        		value = "restore", 
        		results = { @Result(name = "success", type="tiles", location = "restore") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	RestoreZipFile restoreZipFile = new RestoreZipFile(userDaoLocal, roleDaoLocal, pageDaoLocal, propertyDaoLocal, locationDaoLocal);
    	restoreZipFile.restoreZip(restoreFile);

    	return SUCCESS;
    }// Ende execute()
    
}// Ende class
