package de.tivsource.page.admin.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.entity.administration.Role;


public class RoleListConverter extends StrutsTypeConverter {

    @SuppressWarnings("rawtypes")
	@Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
    	
    	List<Role> roleList = null;

        if(values.length<=0) {
            throw new TypeConversionException("Kein gültigen Rollen angegeben");
        }
    	
    	for ( int i = 0; i < values.length; i++ ){
    		
    		if(i==0){
    			roleList = new ArrayList<Role>();
    		}
    		
    		String roleId = values[i];
            try {
                RoleDaoLocal roleDaoLocal;
                Context earContext = new InitialContext();
                roleDaoLocal = (RoleDaoLocal) earContext.lookup("java:global/tiv-page/dao-0.0.1/RoleDao");
                Role role = roleDaoLocal.findByUuid(roleId);
                roleList.add(role);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    	}

        if(roleList.size()<=0) {
            throw new TypeConversionException("Kein gültige Role - UUID");
        }

        return roleList;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public String convertToString(Map context, Object o) {
            Role role = (Role)o;
            return ""+role.getUuid();
    }
}
