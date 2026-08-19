/**
 * 
 */
package de.tivsource.page.admin.services;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * @author Marc Michele
 *
 */
@Service
public class PropertyService {

    private static final Logger LOGGER = LogManager.getLogger(PropertyService.class);

    @Autowired
    private PropertyDaoLocal propertyDaoLocal;

    public String getProperty(String key) {
        LOGGER.debug("getProperty(String key) aufrufen.");
        if(isValidKey(key) && propertyDaoLocal.isKey(key)) {
            return propertyDaoLocal.findByKey(key).getValue();
        }
        return "Invalid Key: " + key;
    }
    
    protected Boolean isValidKey(String input) {
        if (Pattern.matches("[a-zA-Z0-9.]*", input)) {
            return true;
        }
        return false;
    }

}// Ende class
