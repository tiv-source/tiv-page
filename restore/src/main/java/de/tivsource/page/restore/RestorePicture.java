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

import org.apache.log4j.Logger;

import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.entity.picture.PictureDescription;
import de.tivsource.page.entity.picture.PictureUrl;
import de.tivsource.page.enumeration.UrlType;

/**
 * @author Marc Michele
 *
 */
public class RestorePicture {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestorePicture.class);

    private GalleryDaoLocal galleryDaoLocal;

    private PictureDaoLocal pictureDaoLocal;

    public RestorePicture(GalleryDaoLocal galleryDaoLocal, PictureDaoLocal pictureDaoLocal) {
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
                	Picture picture = convert(line);
                    pictureDaoLocal.merge(picture);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private Picture convert(String line) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|
	    // pictureUrls|orderNumber|visible|gallery|created|modified|modifiedBy|modifiedAddress|

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // Erstelle Announcement-Objekt
        Picture picture = new Picture();

        picture.setUuid(items[0]);

        Map<Language, PictureDescription> descriptionMap = new HashMap<Language, PictureDescription>();
        descriptionMap.put(Language.DE, createDescription(
                items[1], items[2], items[3], 
                items[4], Language.DE, picture));
        descriptionMap.put(Language.EN, createDescription(
                items[5], items[6], items[7], 
                items[8], Language.EN, picture));
        picture.setDescriptionMap(descriptionMap);

        
        Map<UrlType, PictureUrl> pictureUrls = new HashMap<UrlType, PictureUrl>();
		String[] urlItems = items[9].split(";");
		for (String element : urlItems) {
			PictureUrl pictureUrl = createPictureUrl(element, picture);
			pictureUrls.put(pictureUrl.getUrlType(), pictureUrl);
		}
		picture.setPictureUrls(pictureUrls);
        
		
        picture.setOrderNumber(Integer.parseInt(items[10]));
        picture.setVisible(items[11].equals("true") ? true : false);
        picture.setGallery(galleryDaoLocal.findByUuid(items[12]));
        picture.setCreated(convertDateString(items[13]));
        picture.setModified(convertDateString(items[14]));
        picture.setModifiedBy(items[15]);
        picture.setModifiedAddress(items[16]);

        return picture;
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

    private PictureDescription createDescription(String uuid, String name, String description, String keywords, Language language, Picture picture) {
    	PictureDescription pageDescription = new PictureDescription();
        pageDescription.setUuid(uuid);
        pageDescription.setName(name);
        pageDescription.setDescription(description);
        pageDescription.setKeywords(keywords);
        pageDescription.setLanguage(language);
        pageDescription.setPicture(picture);
        return pageDescription;
    }

    private PictureUrl createPictureUrl(String input, Picture picture) {
    	String[] items = input.split(",");

    	PictureUrl pictureUrl = new PictureUrl();
    	pictureUrl.setPicture(picture);
    	pictureUrl.setUrl(items[1]);

		if (items[2].equals("THUMBNAIL")) {
			pictureUrl.setUrlType(UrlType.THUMBNAIL);
		} else if (items[2].equals("NORMAL")) {
			pictureUrl.setUrlType(UrlType.NORMAL);
		} else if (items[2].equals("LARGE")) {
			pictureUrl.setUrlType(UrlType.LARGE);
		} else {
			pictureUrl.setUrlType(UrlType.FULL);
		}

    	pictureUrl.setUuid(items[0]);
    	return pictureUrl;
    }
    
    private void cleanup() {
        if(pictureDaoLocal.countAll() > 0) {
            Iterator<Picture> pictureIterator = pictureDaoLocal.findAll(0, pictureDaoLocal.countAll()).iterator();
            while(pictureIterator.hasNext()) {
            	Picture next = pictureIterator.next();
                pictureDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()

}// Ende class
