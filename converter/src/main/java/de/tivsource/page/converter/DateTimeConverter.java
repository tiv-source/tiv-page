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
public class DateTimeConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DateTimeConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Kein Datum mit Urzeit angegeben.");
        	throw new TypeConversionException("Kein Datum mit Uhrzeit angegeben.");
        }
        
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN);
            LOGGER.info("Angegebener Datum mit Uhrzeit String: " + values[0]);
            return simpleDateFormat.parse(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Kein gültiges Datum mit Uhrzeit angegeben.");
        	throw new TypeConversionException("Kein gültiges Datum mit Uhrzeit angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
	    Date dateTime = (Date)o;
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN);
		return simpleDateFormat.format(dateTime);
	}

}// Ende class
