package de.tivsource.page.converter;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.entity.lecturer.Lecturer;

/**
 * 
 * @author Marc Michele
 *
 */
public class LecturerSetConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LecturerSetConverter.class);

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        SortedSet<Lecturer> lecturerSet = new TreeSet<Lecturer>();
        LOGGER.info("Anzahl Lecturer Objekte im Set zu beginn der Konvertierung: " + lecturerSet.size());
        
        if (values == null || values.length == 0 || values[0].trim().length() == 0) {
            LOGGER.info("Keine Lecturer UUID angegeben.");
            throw new TypeConversionException("Keine Lecturer UUID angegeben angegeben.");
        }
        LOGGER.info("Anzahl der zu konvertierenden UUIDs: " + values.length);
        
        
        try {
            Context initialContext = new InitialContext();
            // TODO: Remove version number from dao
            LecturerDaoLocal lecturerDaoLocal = (LecturerDaoLocal) initialContext.lookup("java:global/tiv-page/dao/LecturerDao");

            for(int i=0; i < values.length; i++) {
                lecturerSet.add(lecturerDaoLocal.findByUuid(values[i]));
            }
        } catch (Exception e) {
            LOGGER.info("Keine gültige Lecturer UUID angegeben.");
            throw new TypeConversionException("Keine gültige Lecturer UUID angegeben.");
        }
        LOGGER.info("Anzahl der konvertierten Lecturer Objekte: " + lecturerSet.size());
        return lecturerSet;

    }// Ende convertFromString

    /* (non-Javadoc)
     * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object o) {
        // TODO: Muss noch implementiert werden.
        return null;
    }

}// Ende class
