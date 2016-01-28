package de.tivsource.page.admin.actions.user;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.entity.administration.User;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 671128068812196324L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteAction.class);

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;
    
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
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "userDeleteForm"),
        				@Result(name = "error", type="tiles", location = "userDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(user != null) {
    		User dbUser = userDaoLocal.findByUuid(user.getUuid());
            
    		dbUser.setModifiedAddress(remoteAddress);
            dbUser.setModified(new Date());
            dbUser.setModifiedBy(remoteUser);
            dbUser.setRoles(null);

            userDaoLocal.merge(dbUser);
    		userDaoLocal.delete(dbUser);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
