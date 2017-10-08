package de.tivsource.page.admin.actions.others.companiongroup;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;

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

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    private CompanionGroup companionGroup;

    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

    public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "companionGroupDeleteForm"),
        				@Result(name = "error", type="tiles", location = "companionGroupDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companionGroup != null) {
    	    CompanionGroup dbCompanionGroup = companionGroupDaoLocal.findByUuid(companionGroup.getUuid());
    		dbCompanionGroup.setModified(new Date());
    		dbCompanionGroup.setModifiedBy(remoteUser);
    		dbCompanionGroup.setModifiedAddress(remoteAddress);
    		companionGroupDaoLocal.merge(dbCompanionGroup);
    		companionGroupDaoLocal.delete(dbCompanionGroup);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
