package de.tivsource.page.admin.actions.maintenance.files;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -2036755864820648448L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    private String file;

    @Autowired
    public DeleteAction(PropertyService propertyService) {
        super(propertyService);
    }

    public String getFile() {
        return file;
    }

    @StrutsParameter
    public void setFile(String file) {
        this.file = file;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "fileDeleteForm"),
        				@Result(name = "error", type="tiles", location = "fileDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	// TODO: Upload-Pfad aus Datenbank laden
    	// TODO: Überprüfen ob die Variable file mit .. oder / anfängt
    	if(file != null && file.length() > 0) {
    	    String strPath = "/var/www/html/uploads/";
    	    String deleteFile = strPath + file;
    	    File delete = new File(deleteFile);
    	    delete.delete();
    	    return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
