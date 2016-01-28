package de.tivsource.page.admin.actions.role;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.entity.administration.Role;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 3535865629424875588L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteAction.class);

    @InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;
    
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "roleDeleteForm"),
        				@Result(name = "error", type="tiles", location = "roleDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(role != null) {
    	    Role dbRole = roleDaoLocal.findByUuid(role.getUuid());
    	    dbRole.setModified(new Date());
    	    dbRole.setModifiedBy(remoteUser);
    	    dbRole.setModifiedAddress(remoteAddress);
    	    roleDaoLocal.merge(dbRole);
    	    roleDaoLocal.delete(dbRole);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
