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
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.entity.message.Message;

/**
 * @author Marc Michele
 *
 */
public class RestoreMessage {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreMessage.class);
    
    private MessageDaoLocal messageDaoLocal;

    private Map<String, InputStream> streams;

    public RestoreMessage(MessageDaoLocal messageDaoLocal,
            Map<String, InputStream> streams) {
        super();
        this.messageDaoLocal = messageDaoLocal;
        this.streams = streams;
    }

    /**
     * Generiert aus der Datei die entsprechenden News-Einträge.
     * @throws ParseException 
     */
    public void generate() {
        LOGGER.info("generate() aufgerufen.");
        cleanup();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(streams.get("message.csv")));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    Message message = convert(line);
                    messageDaoLocal.merge(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Ende generate()
    
    private Message convert(String line) {
        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // uuid|gender|firstname|lastname|mail|telephone|fax|content|privacy|created|createdAddress|

        Message message = new Message();

        message.setUuid(items[0]);
        message.setGender(items[1].equals("true") ? true : false);
        message.setFirstname(items[2]);
        message.setLastname(items[3]);
        message.setMail(items[4]);
        message.setTelephone(items[5]);
        message.setFax(items[6]);
        message.setContent(createContent(items[7]));
        message.setPrivacy(items[8].equals("true") ? true : false);
        message.setCreated(convertDateString(items[9]));
        message.setCreatedAddress(items[10]);
        
        return message;
    }

    private String createContent(String filename) {
        BufferedReader input = new BufferedReader(new InputStreamReader(streams.get(filename)));
        StringBuffer contentString = new StringBuffer();
        try {
            String lineInput = null;
            while ((lineInput = input.readLine()) != null) {
                contentString.append(lineInput);
                contentString.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentString.toString();
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
        if(messageDaoLocal.countAll() > 0) {
            Iterator<Message> messageIterator = messageDaoLocal.findAll(0, messageDaoLocal.countAll()).iterator();
            while(messageIterator.hasNext()) {
                Message next = messageIterator.next();
                messageDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()
    
}// Ende class
