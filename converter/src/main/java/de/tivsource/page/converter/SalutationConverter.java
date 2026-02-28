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

import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;

/**
 * 
 * @author Marc Michele
 * TODO: Fehlermeldung ausarbeiten.
 */
public class SalutationConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SalutationConverter.class);

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
            LOGGER.info("Keine Salutation UUID angegeben.");
            throw new TypeConversionException("Keine Salutation UUID angegeben angegeben.");
        }

        try {
            LOGGER.info("Versuche Salutation Objekt anhand der UUID zu finden.");
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            SalutationDaoLocal salutationDaoLocal = (SalutationDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/SalutationDao");
            return salutationDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
            LOGGER.info("Keine gültige Salutation UUID angegeben.");
            throw new TypeConversionException("Keine gültige Salutation UUID angegeben.");
        }
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        Salutation salutation = (Salutation)o;
        return salutation.getUuid();
    }

}// Ende class
