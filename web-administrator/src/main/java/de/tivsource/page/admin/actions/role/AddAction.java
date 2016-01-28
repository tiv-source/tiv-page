package de.tivsource.page.admin.actions.role;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.entity.administration.Role;
import de.tivsource.page.entity.administration.User;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 3206549364257116831L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(AddAction.class);

    @InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;
    
    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;
    
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
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "roleAddForm"),
        				@Result(name = "error", type="tiles", location = "roleAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(role != null) {
    	    role.setUuid(UUID.randomUUID().toString());
    	    role.setCreated(new Date());
    	    role.setModified(new Date());
    	    role.setModifiedBy(remoteUser);
    	    role.setModifiedAddress(remoteAddress);

    	    roleDaoLocal.merge(role);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    public List<User> getUserList() {
        return userDaoLocal.findAll(0, userDaoLocal.countAll());
    }

}// Ende class
