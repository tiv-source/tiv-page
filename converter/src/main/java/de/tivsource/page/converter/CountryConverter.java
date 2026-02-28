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

import de.tivsource.page.common.country.Country;
import de.tivsource.page.dao.country.CountryDaoLocal;

/**
 * 
 * @author Marc Michele
 * TODO: Fehlermeldung ausarbeiten.
 */
public class CountryConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CountryConverter.class);

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
            LOGGER.info("Keine Country UUID angegeben.");
            throw new TypeConversionException("Keine Country UUID angegeben angegeben.");
        }

        try {
            LOGGER.info("Versuche Country Objekt anhand der UUID zu finden.");
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            CountryDaoLocal countryDaoLocal = (CountryDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/CountryDao");
            return countryDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
            LOGGER.info("Keine gültige Country UUID angegeben.");
            throw new TypeConversionException("Keine gültige Country UUID angegeben.");
        }
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        Country country = (Country)o;
        return country.getUuid();
    }

}// Ende class
