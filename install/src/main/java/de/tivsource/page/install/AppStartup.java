/**
 * 
 */
package de.tivsource.page.install;

import java.util.Date;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger("INFO");

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

        checkAllAttributesExisting();

    }// Ende contextInitialized

    /**
     * Methode um zu überprüfen ob alle benötigten Attribute in der Datenbank vorhanden sind.
     */
    private void checkAllAttributesExisting() {
        String[][] properties = {
                {"appointment.archive.list.description",                  "true"},
                {"appointment.archive.list.pointInTime.date",             "true"},
                {"appointment.archive.list.pointInTime.date.preposition", "true"},
                {"appointment.archive.list.pointInTime.nice",             "true"},
                {"appointment.archive.list.pointInTime.time",             "true"},
                {"appointment.archive.pointInTime.date",                  "true"},
                {"appointment.archive.pointInTime.nice",                  "true"},
                {"appointment.archive.pointInTime.time",                  "true"},
                {"appointment.booking.text.de",                           "Ticket kaufen"},
                {"appointment.booking.text.en",                           "Buy a ticket"},
                {"appointment.list.description",                          "true"},
                {"appointment.list.pointInTime.date",                     "true"},
                {"appointment.list.pointInTime.date.preposition",         "true"},
                {"appointment.list.pointInTime.nice",                     "true"},
                {"appointment.list.pointInTime.time",                     "true"},
                {"appointment.pointInTime.date",                          "true"},
                {"appointment.pointInTime.nice",                          "true"},
                {"appointment.pointInTime.time",                          "true"},
                {"captcha.image.lastModified",                            "Sun, 24 Nov 2019 14:26:08 GMT"},
                {"companion.page.enabled",                                "false"},
                {"contact.page.enabled",                                  "true"},
                {"cookieconsent.button.background",                       "#f1d600"},
                {"cookieconsent.content.dismiss",                         "Akzeptieren"},
                {"cookieconsent.content.href",                            "/public/privacy/index.html"},
                {"cookieconsent.content.link",                            "Mehr erfahren"},
                {"cookieconsent.content.message",                         "Wir verwenden Cookies, um Ihr Erlebnis auf unserer Website so angenehm wie möglich zu gestalten. Durch die Nutzung unserer Website erklären Sie sich mit unserer Verwendung von Cookies einverstanden."},
                {"cookieconsent.content.target",                          ""},
                {"cookieconsent.popup.background",                        "#000"},
                {"cookieconsent.position",                                "top"},
                {"css-path",                                              "/var/www/html/css/"},
                {"cssFile.lastModified",                                  "Sun, 24 Nov 2019 14:26:08 GMT"},
                {"event.show.description",                                "true"},
                {"event.quantity.list",                                   "1:01;2:02;3:03;4:04;5:05;6:06;7:07;8:08;9:09;10:10;11:11;12:12"},
                {"module.request",                                        "false"},
                {"news.on.home",                                          "false"},
                {"news.on.home.quantity",                                 "2"},
                {"request.template.path",                                 "file:/srv/tiv-page/templates/template_request.xml"},
                {"socialmedia.whatsapp.url",                              ""},
                {"socialmedia.whatsapp.url.icon",                         "/uploads/WhatsApp.png"},
                {"socialmedia.whatsapp.url.icon.alt",                     "WhatsApp"},
        };
        for (int i = 0; i < properties.length; i++) {
            if(!checkAttribute(properties[i][0], properties[i][1])) {
                LOGGER.info("Eigenschaft: " + properties[i][0] + " hinzugefügt.");
            }
        }
    }// Ende checkAllAttributesExisting()

    private Boolean checkAttribute(String inputKey, String inputValue) {
        Property dbProperty = propertyDaoLocal.findByKey(inputKey);
        // Prüfe ob der Wert in der Datenbank vorhanden ist
        if(dbProperty != null) {
            return true;
        }
        Property property = new Property();
        property.setKey(inputKey);
        property.setModified(new Date());
        property.setModifiedAddress("localhost");
        property.setModifiedBy("Installer");
        property.setValue(inputValue);
        propertyDaoLocal.merge(property);
        return false;
    }// Ende checkAttribute(String inputKey, String inputValue)

}// Ende class
