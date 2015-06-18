package de.tivsource.page.admin.actions.restore;

import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.page.admin.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class IndexAction extends EmptyAction {

    /**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6559007062753892440L;

    /**
	 * Statischer Logger der Klasse.
	 */
	private static final Logger LOGGER = Logger.getLogger("INFO");
	
    @Override
    @Actions({
        @Action(
        		value = "index", 
        		results = { @Result(name = "success", type="tiles", location = "restoreForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	return SUCCESS;
    }// Ende execute()
    
}// Ende class
