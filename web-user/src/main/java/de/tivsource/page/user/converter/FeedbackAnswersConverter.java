package de.tivsource.page.user.converter;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.entity.administration.Role;


public class FeedbackAnswersConverter extends StrutsTypeConverter {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FeedbackAnswersConverter.class);

    @SuppressWarnings("rawtypes")
	@Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
    	
        LOGGER.info("convertFromString() aufgerufen.");
        
    	Map<String, String> answer = null;

        if(values.length<=0) {
            throw new TypeConversionException("Keine gültigen Anworten angegeben");
        }
    	
        /**
    	for ( int i = 0; i < values.length; i++ ){
    		
    		if(i==0){
    			answer = new HashMap<String, Integer>();
    		}
    		
    		String roleId = values[i];
            try {
                RoleDaoLocal roleDaoLocal;
                Context earContext = new InitialContext();
                roleDaoLocal = (RoleDaoLocal) earContext.lookup("java:global/tiv-page/dao-0.0.1/RoleDao");
                Role role = roleDaoLocal.findByUuid(roleId);
                answer.add(role);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    	}

        if(answer.size()<=0) {
            throw new TypeConversionException("Kein gültige Role - UUID");
        }
        
        */

        System.out.println(values[0]);
        
        return answer;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public String convertToString(Map context, Object o) {
            Role role = (Role)o;
            return ""+role.getUuid();
    }
}
