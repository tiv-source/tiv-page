/**
 * 
 */
package de.tivsource.page.converter;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.entity.course.Course;

/**
 * 
 * @author Marc Michele
 * TODO: Fehlermeldung ausarbeiten.
 */
public class CourseConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CourseConverter.class);

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
            LOGGER.info("Keine Course UUID angegeben.");
            throw new TypeConversionException("Keine Course UUID angegeben angegeben.");
        }

        try {
            LOGGER.info("Versuche Course Objekt anhand der UUID zu finden.");
            // Überprüfen ob es eine UUID ist.
            if(!isValid(values[0])) {
                throw new TypeConversionException("Keine gültige Course UUID angegeben.");
            }
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            CourseDaoLocal courseDaoLocal = (CourseDaoLocal) initialContext.lookup("java:global/tiv-page/dao/CourseDao");
            Optional<Course> course = courseDaoLocal.findByUuid(values[0]);
            if(!course.isPresent()) {
                throw new TypeConversionException("Kein Course Objekt zu dieser UUID gefunden.");
            }
            return course.get();
        } catch (Exception e) {
            LOGGER.info("Keine gültige Course UUID angegeben.");
            throw new TypeConversionException("Keine gültige Course UUID angegeben.");
        }
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        Course course = (Course)o;
        return course.getUuid();
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

}// Ende class
