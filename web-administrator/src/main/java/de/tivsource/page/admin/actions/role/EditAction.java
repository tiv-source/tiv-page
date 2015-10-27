package de.tivsource.page.admin.actions.role;

import java.util.Date;
import java.util.List;

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
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2871395607908117378L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

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
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "roleEditForm"),
        				@Result(name = "error",   type = "tiles", location = "roleEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(role != null) {
    		LOGGER.info(role.getTechnical());
    		Role dbRole = roleDaoLocal.findByUuid(role.getUuid());
    		dbRole.setTechnical(role.getTechnical());
    		dbRole.setModified(new Date());
    		dbRole.setModifiedBy(remoteUser);
    		dbRole.setIp(remoteAddress);
    		roleDaoLocal.merge(dbRole);
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
