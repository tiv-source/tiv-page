/**
 * 
 */
package de.tivsource.page.restore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.entity.administration.Role;

/**
 * @author Marc Michele
 *
 */
public class RestoreRole {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestoreRole.class);

    private RoleDaoLocal roleDaoLocal;

    public RestoreRole(RoleDaoLocal roleDaoLocal) {
        super();
        this.roleDaoLocal = roleDaoLocal;
    }

    public void generate(InputStream inputStream) {
        LOGGER.info("generate(InputStream inputStream) aufgerufen.");
        cleanup();
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
        role.setCreated(convertDateString(items[2]));
        role.setModified(convertDateString(items[3]));
        role.setModifiedBy(items[4]);
        role.setModifiedAddress(items[5]);

        return role;
    }

    /**
     * Methode zum Konvertieren eines Strings des Formates "1970-12-01 23:59:59" in ein Date-Object. 
     * @param dateString
     * @return
     */
    private Date convertDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }// Ende convertDateString(String dateString)

    private void cleanup() {
        if(roleDaoLocal.countAll() > 0) {
            Iterator<Role> roleIterator = roleDaoLocal.findAll(0, roleDaoLocal.countAll()).iterator();
            while(roleIterator.hasNext()) {
                Role next = roleIterator.next();
                next.setUsers(null);
                roleDaoLocal.merge(next);
                roleDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()
    
}// Ende class
