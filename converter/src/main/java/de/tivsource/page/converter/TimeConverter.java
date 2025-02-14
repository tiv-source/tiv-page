/**
 * 
 */
package de.tivsource.page.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * @author marc
 *
 */
public class TimeConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(TimeConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine Zeit angegeben.");
        	throw new TypeConversionException("Keine Zeit angegeben.");
        }
        
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
            LOGGER.info("Angegebener Zeit String: " + values[0]);
            return simpleDateFormat.parse(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige Zeit angegeben.");
        	throw new TypeConversionException("Keine gültige Zeit angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
	    Date time = (Date)o;
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
		return simpleDateFormat.format(time);
	}

}// Ende class
