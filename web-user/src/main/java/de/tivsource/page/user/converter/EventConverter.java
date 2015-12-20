/**
 * 
 */
package de.tivsource.page.user.converter;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.entity.event.Event;

/**
 * @author marc
 *
 */
public class EventConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EventConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine Event UUID angegeben.");
        	throw new TypeConversionException("Keine Event UUID angegeben angegeben.");
        }
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            EventDaoLocal eventDaoLocal = (EventDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/EventDao");
            return eventDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige Event UUID angegeben.");
        	throw new TypeConversionException("Keine gültige Event UUID angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
	    Event event = (Event)o;
		return event.getUuid();
	}

}// Ende class
