package de.tivsource.page.admin.actions.system.role;

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
    private static final long serialVersionUID = -770085522386553425L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="RoleDao")
    private RoleDaoLocal roleDaoLocal;
	
	@InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

	private Role role;

	private String uncheckRole;

	public Role getRole() {
        return role;
    }

	public void setRole(String uncheckRole) {
        this.uncheckRole = uncheckRole;
    }

	@Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "roleEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "roleAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "roleDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<User> getUserList() {
	    return userDaoLocal.findAll(0, userDaoLocal.countAll());
	}
	
	private void loadPageParameter() {

		if( uncheckRole != null && uncheckRole != "" && uncheckRole.length() > 0) {
		    role = roleDaoLocal.findByUuid(uncheckRole);
		} else {
		    role = new Role();
		}

	}// Ende loadPageParameter()
	
}// Ende class
