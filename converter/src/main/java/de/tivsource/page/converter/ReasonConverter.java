/**
 * 
 */
package de.tivsource.page.converter;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.dao.reason.ReasonDaoLocal;
import de.tivsource.page.entity.request.Reason;

/**
 * @author marc
 *
 */
public class ReasonConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ReasonConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
	    LOGGER.info("convertFromString(Map context, String[] values, Class toClass) aufgerufen.");

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine Reason UUID angegeben.");
        	throw new TypeConversionException("Keine Reason UUID angegeben angegeben.");
        }
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            ReasonDaoLocal reasonDaoLocal = (ReasonDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/ReasonDao");
            return reasonDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige Reason UUID angegeben.");
        	throw new TypeConversionException("Keine gültige Reason UUID angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
	    Reason reason = (Reason)o;
		return reason.getUuid();
	}

}// Ende class
