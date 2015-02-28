package de.tivsource.page.install;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.entity.administration.Role;

public class CreateRole {

	private static final Logger LOGGER = Logger.getLogger("INFO");
	
    private RoleDaoLocal roleDaoLocal;

    public void setRoleDaoLocal(RoleDaoLocal roleDaoLocal) {
            this.roleDaoLocal = roleDaoLocal;
    }

    public void generate() {
    	LOGGER.info("generate() aufgerufen.");

    	InputStream inputStream = CreateRole.class
    			.getClassLoader()
    			.getResourceAsStream("csv/role.csv");
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

    	try {
    		String line = null;
    		while ((line = in.readLine()) != null) {
    			if (!line.startsWith("[Format Definition]")) {
    				Role role = convert(line);
    				roleDaoLocal.merge(role);
    			}
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }// Ende generate()

    private Role convert(String line) {
        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        Role role = new Role();
        role.setUuid(items[0]);
        role.setTechnical(items[1]);
        
        return role;
    }
    
}// Ende class
