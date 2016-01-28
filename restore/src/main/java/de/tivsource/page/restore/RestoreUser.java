/**
 * 
 */
package de.tivsource.page.restore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.entity.administration.Role;
import de.tivsource.page.entity.administration.User;

/**
 * @author Marc Michele
 *
 */
public class RestoreUser {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestoreUser.class);

    private UserDaoLocal userDaoLocal;

    private RoleDaoLocal roleDaoLocal;

    public RestoreUser(UserDaoLocal userDaoLocal, RoleDaoLocal roleDaoLocal) {
        super();
        this.userDaoLocal = userDaoLocal;
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
                    User user = convert(line);
                    userDaoLocal.merge(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private User convert(String line) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // uuid|username|email|firstname|lastname|password|roles|added|modified|ip|
        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // Erstelle Announcement-Objekt
        User user = new User();

        user.setUuid(items[0]);
        user.setUsername(items[1]);
        user.setEmail(items[2]);
        user.setFirstname(items[3]);
        user.setLastname(items[4]);
        user.setPassword(items[5]);

        // Erzeuge Role-List.
        List<Role> roles = new ArrayList<Role>();

        // Zerlege CSV-String.
        String[] csvTags = items[6].split(";");

        // Laufe durch das Array und füge die Tags der Tag-Liste hinzu.
        for (int i = 0; i < csvTags.length; i++) {
            roles.add(roleDaoLocal.findByUuid(csvTags[i]));
        }
        user.setRoles(roles);

        // Füge Erstellungsdatum hinzu.
        user.setCreated(convertDateString(items[7]));
        user.setModified(convertDateString(items[8]));

        // Setze IP auf localhost.
        user.setModifiedAddress(items[9]);

        user.setModifiedBy(items[10]);

        return user;
    }// Ende convert(String line)

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
    }

    private void cleanup() {
        if(userDaoLocal.countAll() > 0) {
            Iterator<User> userIterator = userDaoLocal.findAll(0, userDaoLocal.countAll()).iterator();
            while(userIterator.hasNext()) {
                User next = userIterator.next();
                next.setRoles(null);
                userDaoLocal.merge(next);
                userDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()

}// Ende class
