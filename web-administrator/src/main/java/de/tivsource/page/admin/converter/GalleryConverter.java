/**
 * 
 */
package de.tivsource.page.admin.converter;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;

/**
 * @author marc
 *
 */
public class GalleryConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(GalleryConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine Gallery UUID angegeben.");
        	throw new TypeConversionException("Keine Gallery UUID angegeben angegeben.");
        }
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            GalleryDaoLocal galleryDaoLocal = (GalleryDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/GalleryDao");
            return galleryDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige Gallery UUID angegeben.");
        	throw new TypeConversionException("Keine gültige Gallery UUID angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		Gallery gallery = (Gallery)o;
		return gallery.getUuid();
	}

}// Ende class
