/**
 * 
 */
package de.tivsource.page.install;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;

/**
 * @author Marc Michele
 *
 */
public class AppStartup implements ServletContextListener {

    @EJB
    private RoleDaoLocal roleDaoLocal;

    @EJB
    private UserDaoLocal userDaoLocal;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        CreateRole createRole = new CreateRole();
        createRole.setRoleDaoLocal(roleDaoLocal);
        createRole.generate();

        CreateUser createUser = new CreateUser();
        createUser.setUserDaoLocal(userDaoLocal);
        createUser.setRoleDaoLocal(roleDaoLocal);
        createUser.generate();
    }// Ende contextInitialized

}// Ende class
