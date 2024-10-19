/**
 * 
 */
package de.tivsource.page.admin.converter;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * @author marc
 *
 */
public class CompanionGroupConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CompanionGroupConverter.class);

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
        	LOGGER.info("Keine CompanionGroup UUID angegeben.");
        	throw new TypeConversionException("Keine CompanionGroup UUID angegeben angegeben.");
        }
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            CompanionGroupDaoLocal companionGroupDaoLocal = (CompanionGroupDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/CompanionGroupDao");
            return companionGroupDaoLocal.findByUuid(values[0]);
        } catch (Exception e) {
        	LOGGER.info("Keine gültige CompanionGroup UUID angegeben.");
        	throw new TypeConversionException("Keine gültige CompanionGroup UUID angegeben.");
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
	    CompanionGroup companionGroup = (CompanionGroup)o;
		return companionGroup.getUuid();
	}

}// Ende class
