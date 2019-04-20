/**
 * 
 */
package de.tivsource.page.install;

import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

/**
 * @author Marc Michele
 *
 */
public class AppStartup implements ServletContextListener {

    @EJB
    private RoleDaoLocal roleDaoLocal;

    @EJB
    private UserDaoLocal userDaoLocal;

    @EJB
    private PropertyDaoLocal propertyDaoLocal;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        // Hole Eigenschaft aus der Datenbank
        Property dbProperty = propertyDaoLocal.findByKey("isInstalled");
        boolean isInstalled = false;
        // Prüfe ob der Wert in der Datenbank vorhanden ist
        if(dbProperty != null) {
            isInstalled = dbProperty.getValue().equals("true") ? true : false;
        }

        // Wenn noch nichts Installiert wurde
        if(!isInstalled) {
            CreateRole createRole = new CreateRole();
            createRole.setRoleDaoLocal(roleDaoLocal);
            createRole.generate();

            CreateUser createUser = new CreateUser();
            createUser.setUserDaoLocal(userDaoLocal);
            createUser.setRoleDaoLocal(roleDaoLocal);
            createUser.generate();

            // Setze den Wert das die Installation ausgeführt wurde
            Property property = new Property();
            property.setKey("isInstalled");
            property.setModified(new Date());
            property.setModifiedAddress("localhost");
            property.setModifiedBy("Installer");
            property.setValue("true");
            propertyDaoLocal.merge(property);
        }

    }// Ende contextInitialized

}// Ende class
