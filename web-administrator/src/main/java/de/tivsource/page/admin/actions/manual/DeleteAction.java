package de.tivsource.page.admin.actions.manual;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.entity.manual.Manual;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -8955242912078594369L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    private Manual manual;

    public Manual getManual() {
        return manual;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "manualDeleteForm"),
        				@Result(name = "error", type="tiles", location = "manualDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(manual != null) {
    		Manual dbManual = manualDaoLocal.findByUuid(manual.getUuid());
    		dbManual.setModified(new Date());
    		dbManual.setModifiedBy(remoteUser);
    		dbManual.setModifiedAddress(remoteAddress);
    		manualDaoLocal.merge(dbManual);
    		manualDaoLocal.delete(dbManual);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
