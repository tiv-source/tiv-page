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

import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author marc
 *
 */
public class ContentItemConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ContentItemConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine ContentItem UUID angegeben.");
        	throw new TypeConversionException("Keine ContentItem UUID angegeben.");
        }
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            ContentItemDaoLocal contentItemDaoLocal = (ContentItemDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/ContentItemDao");
            return contentItemDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige ContentItem UUID angegeben.");
        	LOGGER.error(e);
        	throw new TypeConversionException("Keine gültige ContentItem UUID angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		ContentItem contentItem = (ContentItem)o;
		return contentItem.getUuid();
	}

}// Ende class
