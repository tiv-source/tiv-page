package de.tivsource.page.admin.actions.maintenance.backup;

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
import de.tivsource.page.admin.backup.BackupCss;
import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
public class BackupCssAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -1816649984608026243L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupCssAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

	private InputStream fileStream;

	@Override
    @Actions({
        @Action(
        		value = "css", 
        		results = { @Result(
        				           name = "success", 
        				           type="stream", 
        				           params={"contentType", "text/plain", "inputName", "fileStream", "contentDisposition", "attachment;filename=tiv_page_css.zip"}
        				           ) }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	BackupCss.setPropertyDaoLocal(propertyDaoLocal);
    	File backupFile = BackupCss.getZipFile();
    	fileStream = new FileInputStream(backupFile);
    	backupFile.delete();
    	return SUCCESS;
    }
    
    public InputStream getFileStream() {
		return fileStream;
	}
    
}// Ende class
