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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * @author Marc Michele
 *
 */
public class RestoreGallery {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreGallery.class);

    private GalleryDaoLocal galleryDaoLocal;

    private PictureDaoLocal pictureDaoLocal;

    private Map<String, String> pictures = new HashMap<String, String>(); 
    
    public RestoreGallery(GalleryDaoLocal galleryDaoLocal, PictureDaoLocal pictureDaoLocal) {
        super();
        this.galleryDaoLocal = galleryDaoLocal;
        this.pictureDaoLocal = pictureDaoLocal;
    }

    public void generate(InputStream inputStream) {
        LOGGER.debug("generate(InputStream inputStream) aufgerufen");
        cleanup();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                	Gallery gallery = convert(line);
                    galleryDaoLocal.merge(gallery);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void generateOverviewPictures() {
    	LOGGER.debug("generateOverviewPictures() aufgerufen");
    	Iterator<String> galleryUuids = pictures.keySet().iterator();
    	while (galleryUuids.hasNext()) {
    		String next = galleryUuids.next();
    		convertPictures(next, pictures.get(next));
        }
    }
    
    private Gallery convert(String line) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|
	    // visible|created|modified|modifiedBy|modifiedAddress|picture|
	    // technical|orderNumber|pictureOnPage|

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // Erstelle Announcement-Objekt
        Gallery gallery = new Gallery();

        gallery.setUuid(items[0]);

        Map<Language, Description> descriptionMap = new HashMap<Language, Description>();
        descriptionMap.put(Language.DE, createDescription(
                items[1], items[2], items[3], 
                items[4], Language.DE, gallery));
        descriptionMap.put(Language.EN, createDescription(
                items[5], items[6], items[7], 
                items[8], Language.EN, gallery));
        gallery.setDescriptionMap(descriptionMap);

        gallery.setVisible(items[9].equals("true") ? true : false);
        gallery.setCreated(convertDateString(items[10]));
        gallery.setModified(convertDateString(items[11]));
        gallery.setModifiedBy(items[12]);
        gallery.setModifiedAddress(items[13]);

        pictures.put(items[0], items[14]);

        gallery.setTechnical(items[15]);
        gallery.setOrderNumber(Integer.parseInt(items[16]));
        gallery.setPictureOnPage(items[17].equals("true") ? true : false);


        
        return gallery;
    }// Ende convert(String line)

    private void convertPictures(String galleryUuid, String pictureUuid) {
    	LOGGER.debug("convertPictures(String gallery, String picture) aufgerufen");
        Gallery gallery = galleryDaoLocal.findByUuid(galleryUuid);
        gallery.setPicture(pictureDaoLocal.findByUuid(pictureUuid));
        galleryDaoLocal.merge(gallery);
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

    private void cleanup() {
        if(galleryDaoLocal.countAll() > 0) {
            Iterator<Gallery> galleryIterator = galleryDaoLocal.findAll(0, galleryDaoLocal.countAll()).iterator();
            while(galleryIterator.hasNext()) {
            	Gallery next = galleryIterator.next();
            	galleryDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()

}// Ende class
