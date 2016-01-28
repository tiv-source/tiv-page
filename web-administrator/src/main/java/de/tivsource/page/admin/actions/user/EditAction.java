package de.tivsource.page.admin.actions.user;

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
    private static final long serialVersionUID = -1279279278140940652L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

    @InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;
    
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "userEditForm"),
        				@Result(name = "error",   type = "tiles", location = "userEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(user != null) {
    		LOGGER.info(user.getUsername());
    		User dbUser = userDaoLocal.findByUuid(user.getUuid());
    		
    		dbUser.setEmail(user.getEmail());
    		dbUser.setFirstname(user.getFirstname());
    		dbUser.setLastname(user.getLastname());
    		dbUser.setPassword(user.getPassword());
    		dbUser.setRoles(user.getRoles());
    		dbUser.setUsername(user.getUsername());
    		dbUser.setModifiedAddress(remoteAddress);
    		dbUser.setModified(new Date());
    		dbUser.setModifiedBy(remoteUser);

    		userDaoLocal.merge(dbUser);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<Role> getRoleList() {
        return roleDaoLocal.findAll(0, roleDaoLocal.countAll());
    }

}// Ende class
