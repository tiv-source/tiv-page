package de.tivsource.page.converter;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;


public class CSSFileListConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CSSFileListConverter.class);

    @SuppressWarnings("rawtypes")
	@Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        LOGGER.info("convertFromString(Map context, String[] values, Class toClass) aufgerufen.");
    	SortedSet<CSSFile> files = null;

        if(values.length<=0) {
            throw new TypeConversionException("Kein gültigen CSS - Dateien angegeben");
        }
    	
    	for ( int i = 0; i < values.length; i++ ){
    		
    		if(i==0){
    			files = new TreeSet<CSSFile>();
    		}
    		
    		String cssFileUuid = values[i];
            try {
            	CSSFileDaoLocal cssFileDaoLocal;
                Context earContext = new InitialContext();
                cssFileDaoLocal = (CSSFileDaoLocal) earContext.lookup("java:global/tiv-page/dao-0.0.1/CSSFileDao");
                CSSFile cssFile = cssFileDaoLocal.findByUuid(cssFileUuid);
                files.add(cssFile);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    	}

        if(files.size()<=0) {
            throw new TypeConversionException("Kein gültige CSS-Datei-UUID");
        }

        return files;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public String convertToString(Map context, Object o) {
    	CSSFile files = (CSSFile)o;
    	return ""+files.getUuid();
    }

}// Ende class
