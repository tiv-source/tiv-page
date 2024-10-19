/**
 * 
 */
package de.tivsource.page.converter;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * @author marc
 *
 */
public class LocationConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LocationConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
	    LOGGER.info("convertFromString(Map context, String[] values, Class toClass) aufgerufen.");

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine Location UUID angegeben.");
        	throw new TypeConversionException("Keine Location UUID angegeben angegeben.");
        }
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            LocationDaoLocal locationDaoLocal = (LocationDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/LocationDao");
            return locationDaoLocal.findByUuidWidthEvents(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige Location UUID angegeben.");
        	throw new TypeConversionException("Keine gültige Location UUID angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		Location location = (Location)o;
		return location.getUuid();
	}

}// Ende class
