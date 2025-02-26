package de.tivsource.page.converter;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;

/**
 *
 * @author Marc Michele
 *
 */
public class CSSGroupConverter extends StrutsTypeConverter {

    private static final Logger LOGGER = LogManager.getLogger(CSSGroupConverter.class);

    @SuppressWarnings("rawtypes")
	@Override
    public Object convertFromString(Map context, String[] values, Class toClass) {

        LOGGER.info("convertFromString(Map context, String[] values, Class toClass) aufgerufen.");

        if(values.length<=0) {
            throw new TypeConversionException("Kein gültigen CSS - Gruppe angegeben");
        }
    	

        String cssFileUuid = values[0];
        try {
        	CSSGroupDaoLocal cssGroupDaoLocal;
        	Context earContext = new InitialContext();
        	cssGroupDaoLocal = (CSSGroupDaoLocal) earContext.lookup("java:global/tiv-page/dao-0.0.1/CSSGroupDao");
        	CSSGroup cssGroup = cssGroupDaoLocal.findByUuid(cssFileUuid);
        	return cssGroup;
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
    }// Ende convertFromString(Map context, String[] values, Class toClass)

    @SuppressWarnings("rawtypes")
	@Override
    public String convertToString(Map context, Object o) {
    	CSSGroup cssGroup = (CSSGroup)o;
    	return ""+cssGroup.getUuid();
    }// Ende convertToString(Map context, Object o)

}// Ende class
