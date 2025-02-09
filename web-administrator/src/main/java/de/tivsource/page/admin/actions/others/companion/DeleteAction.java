package de.tivsource.page.admin.actions.others.companion;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.entity.companion.Companion;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6941950482152018018L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="CompanionDao")
    private CompanionDaoLocal companionDaoLocal;

    private Companion companion;

    @StrutsParameter(depth=2)
    public Companion getCompanion() {
        return companion;
    }

    public void setCompanion(Companion companion) {
        this.companion = companion;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "companionDeleteForm"),
        				@Result(name = "error", type="tiles", location = "companionDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companion != null) {
    	    Companion dbCompanion = companionDaoLocal.findByUuid(companion.getUuid());
    		dbCompanion.setModified(new Date());
    		dbCompanion.setModifiedBy(remoteUser);
    		dbCompanion.setModifiedAddress(remoteAddress);
    		companionDaoLocal.merge(dbCompanion);
    		companionDaoLocal.delete(dbCompanion);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
