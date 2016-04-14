package de.tivsource.page.admin.actions.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 3437394713690423081L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

	@InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

	@InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;
	
	private User user;

	private String uncheckUser;

	public User getUser() {
        return user;
    }

	public void setUser(String uncheckUser) {
        this.uncheckUser = uncheckUser;
    }

	@Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "userEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "userAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "userDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<Role> getRoleList() {
	    return roleDaoLocal.findAll(0, roleDaoLocal.countAll());
	}
	
	private void loadPageParameter() {

		if( uncheckUser != null && uncheckUser != "" && uncheckUser.length() > 0) {
			user = userDaoLocal.findByUuid(uncheckUser);
		} else {
			user = new User();
		}

	}// Ende loadPageParameter()
	
}// Ende class
