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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.contentitem.Content;
import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * @author Marc Michele
 *
 */
public class RestoreManual {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreManual.class);
    
    private ManualDaoLocal manualDaoLocal;

    private PictureDaoLocal pictureDaoLocal;

    private Map<String, InputStream> streams;

    public RestoreManual(ManualDaoLocal manualDaoLocal, PictureDaoLocal pictureDaoLocal,
            Map<String, InputStream> streams) {
        super();
        this.manualDaoLocal = manualDaoLocal;
        this.pictureDaoLocal = pictureDaoLocal;
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
                new InputStreamReader(streams.get("manual.csv")));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    Manual manual = convert(line);
                    manualDaoLocal.merge(manual);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Ende generate()
    
    private Manual convert(String line) {
        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|content_uuid(de)|content(de)|content_created(de)|content_modified(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|content_uuid(en)|content(en)|content_created(en)|content_modified(en)|
	    // visible|created|modified|modifiedBy|modifiedAddress|picture|


        Manual manual = new Manual();

        manual.setUuid(items[0]);

        Map<Language, Description> descriptionMap = new HashMap<Language, Description>();
        descriptionMap.put(Language.DE, createDescription(
                items[1], items[2], items[3], 
                items[4], Language.DE, manual));
        descriptionMap.put(Language.EN, createDescription(
                items[9], items[10], items[11], 
                items[12], Language.EN, manual));
        manual.setDescriptionMap(descriptionMap);
        
        Map<Language, Content> contentMap = new HashMap<Language, Content>();
        contentMap.put(Language.DE, createContent(
                items[5], items[6], convertDateString(items[7]), 
                convertDateString(items[8]), Language.DE, manual));
        contentMap.put(Language.EN, createContent(
                items[13], items[14], convertDateString(items[15]), 
                convertDateString(items[16]), Language.EN, manual));
        manual.setContentMap(contentMap);

        manual.setVisible(items[17].equals("true") ? true : false);
        manual.setCreated(convertDateString(items[18]));
        manual.setModified(convertDateString(items[19]));
        manual.setModifiedBy(items[20]);
        manual.setModifiedAddress(items[21]);
        manual.setPicture(pictureDaoLocal.findByUuid(items[22]));
        
        return manual;
    }

    private Description createDescription(
            String uuid, String name, String description, 
            String keywords, Language language, NamingItem namingItem) {
        Description pageDescription = new Description();
        pageDescription.setUuid(uuid);
        pageDescription.setName(name);
        pageDescription.setDescription(description);
        pageDescription.setKeywords(keywords);
        pageDescription.setLanguage(language);
        pageDescription.setNamingItem(namingItem);
        return pageDescription;
    }

    private Content createContent(
            String uuid, String filename, Date created, 
            Date modified, Language language, ContentItem contentItem) {
        BufferedReader inDE = new BufferedReader(new InputStreamReader(streams.get(filename)));
        StringBuffer contentString = new StringBuffer();
        try {
            String lineDE = null;
            while ((lineDE = inDE.readLine()) != null) {
                contentString.append(lineDE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Content content = new Content();
        content.setUuid(uuid);
        content.setContent(contentString.toString());
        content.setContentItem(contentItem);
        content.setCreated(created);
        content.setLanguage(language);
        content.setModified(modified);
        return content;
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
        if(manualDaoLocal.countAll() > 0) {
            Iterator<Manual> manualIterator = manualDaoLocal.findAll(0, manualDaoLocal.countAll()).iterator();
            while(manualIterator.hasNext()) {
            	Manual next = manualIterator.next();
                manualDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()
    
}// Ende class
