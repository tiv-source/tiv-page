/**
 * 
 */
package de.tivsource.page.converter;

import java.util.Map;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;

/**
 * @author marc
 *
 */
public class CaptchaConverter extends StrutsTypeConverter {

    private static final Logger LOGGER = Logger.getLogger("INFO");

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
            LOGGER.info("Kein Captcha angegeben.");
            throw new TypeConversionException("Kein Captcha angegeben.");
        }

        try {
            // Setze die übergebene ID
            String uuid = values[0];
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            CaptchaDaoLocal captchaDaoLocal = (CaptchaDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/CaptchaDao");
            return captchaDaoLocal.findByUuid(uuid);
        } catch (Exception e) {
            LOGGER.info("Keinen gültigen Captcha angegeben.");
            throw new TypeConversionException("Keinen gültigen Captcha angegeben.");
        }
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        Captcha captcha = (Captcha)o;
        return ""+captcha.getUuid();
    }

}// Ende class
