package de.tivsource.page.admin.actions.others.picture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.entity.picture.PictureUrl;
import de.tivsource.page.enumeration.UrlType;

/**
 * 
 * @author Marc Michele
 *
 */
public class PictureEditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -732434023428163882L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(PictureEditAction.class);

    /**
     * Pfad zu den Verzeichnissen der Bild Dateien
     */
    private static final String picturePath = "/srv/www/htdocs/pictures/";

    /**
     * Pfad in dem die hochgeladene orginal Bild Datei gespeichert wird.
     */
    private static final String uploadPath = picturePath + "FULL/";

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Picture picture;

    private File file;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    @Actions({
        @Action(
        		value = "picture", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "pictureForm"),
        				@Result(name = "error",   type = "tiles", location = "pictureEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(picture != null) {
    		LOGGER.info("UUID des Bildes: " + picture.getUuid());
    		Picture dbPicture = pictureDaoLocal.findByUuid(picture.getUuid());

    		dbPicture.setModified(new Date());
    		dbPicture.setModifiedBy(remoteUser);
    		dbPicture.setModifiedAddress(remoteAddress);

    	    if(file != null) {
    	    	// Lösche die alten Bilder
    	    	deletePictures(dbPicture.getPictureUrls());

                // Name der Bild Datei die erstellt werden soll. 
                String pictureSaveName = DigestUtils.shaHex("Hier ist das Geheimniss."
                    + file.getName() + new Date() + "Noch ein bischen.")
                    + ".png";
                LOGGER.info("Variable pictureSaveName: " + pictureSaveName);

                File fullPictureFileToCreate = new File(uploadPath, pictureSaveName);
                // Wenn die Datei noch nicht existiert wird Sie erstellt.
                if (!fullPictureFileToCreate.exists()) {
                    savePictureFile(file, fullPictureFileToCreate);
                }

            	createNormal(uploadPath + pictureSaveName, picturePath
            		+ "NORMAL/" + pictureSaveName);
            	createThumbnail(uploadPath + pictureSaveName, picturePath
            		+ "THUMBNAIL/" + pictureSaveName);
            	createLarge(uploadPath + pictureSaveName, picturePath
            		+ "LARGE/" + pictureSaveName);

            	// Setzte die Urls in das Bild.
            	dbPicture.setPictureUrls(generatePictureUrls(pictureSaveName, picture));
    	    }

    	    LOGGER.info("PictureUrl (FULL): " + dbPicture.getPictureUrls().get(UrlType.FULL).getUrl());
            pictureDaoLocal.merge(dbPicture);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    private static void savePictureFile(File source, File destination) throws Exception {
        byte[] buffer = new byte[(int) source.length()];
        InputStream in = new FileInputStream(source);
        in.read(buffer);
        FileOutputStream fileOutStream = new FileOutputStream(destination);
        fileOutStream.write(buffer);
        fileOutStream.flush();
        fileOutStream.close();
        in.close();
    }

    private static void createNormal(String source, String destination) throws IOException {
        String s = null;

        Process p = Runtime.getRuntime().exec(
                "/usr/bin/convert " + source
                + " -resize 600x500 -quality 85 -compress JPEG "
                + destination);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static void createLarge(String source, String destination) throws IOException {
        String s = null;

        Process p = Runtime.getRuntime().exec(
                "/usr/bin/convert " + source
                + " -resize 1000x1000 -quality 85 -compress JPEG "
                + destination);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static void createThumbnail(String source, String destination) throws IOException {
        String s = null;

        Process p = Runtime.getRuntime().exec(
                "/usr/bin/convert " + source
                + " -resize 200x143 -quality 85 -compress JPEG "
                + destination);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static Map<UrlType, PictureUrl> generatePictureUrls(String pictureName, Picture pictureObject) {
    	LOGGER.debug("generatePictureUrls(String pictureName, Picture pictureObject) aufgerufen.");
    	LOGGER.info("Variable pictureName: " + pictureName);

        Map<UrlType, PictureUrl> pictureUrls = new HashMap<UrlType, PictureUrl>();
        PictureUrl normalPictureUrl = new PictureUrl();
        normalPictureUrl.setUuid(UUID.randomUUID().toString());
        normalPictureUrl.setPicture(pictureObject);
        normalPictureUrl.setUrl(pictureName);
        normalPictureUrl.setUrlType(UrlType.NORMAL);

        PictureUrl largePictureUrl = new PictureUrl();
        largePictureUrl.setUuid(UUID.randomUUID().toString());
        largePictureUrl.setPicture(pictureObject);
        largePictureUrl.setUrl(pictureName);
        largePictureUrl.setUrlType(UrlType.LARGE);

        PictureUrl thumbnailPictureUrl = new PictureUrl();
        thumbnailPictureUrl.setUuid(UUID.randomUUID().toString());
        thumbnailPictureUrl.setPicture(pictureObject);
        thumbnailPictureUrl.setUrl(pictureName);
        thumbnailPictureUrl.setUrlType(UrlType.THUMBNAIL);

        PictureUrl fullPictureUrl = new PictureUrl();
        fullPictureUrl.setUuid(UUID.randomUUID().toString());
        fullPictureUrl.setPicture(pictureObject);
        fullPictureUrl.setUrl(pictureName);
        fullPictureUrl.setUrlType(UrlType.FULL);

        pictureUrls.put(UrlType.NORMAL, normalPictureUrl);
        pictureUrls.put(UrlType.LARGE, largePictureUrl);
        pictureUrls.put(UrlType.THUMBNAIL, thumbnailPictureUrl);
        pictureUrls.put(UrlType.FULL, fullPictureUrl);

        return pictureUrls;
    }

    private static void deletePictures(Map<UrlType, PictureUrl> pictureUrls) throws IOException {
    	String pathFULL = picturePath + "FULL/" + pictureUrls.get(UrlType.FULL).getUrl();
    	deleteFile(pathFULL);
    	String pathLARGE = picturePath + "LARGE/" + pictureUrls.get(UrlType.LARGE).getUrl();
    	deleteFile(pathLARGE);
    	String pathNORMAL = picturePath + "NORMAL/" + pictureUrls.get(UrlType.NORMAL).getUrl();
    	deleteFile(pathNORMAL);
    	String pathTHUMBNAIL = picturePath + "THUMBNAIL/" + pictureUrls.get(UrlType.THUMBNAIL).getUrl();
    	deleteFile(pathTHUMBNAIL);
    }// Ende deletePictures(Map<UrlType, PictureUrl> pictureUrls)

    private static void deleteFile(String source) throws IOException {
    	Path filePath = Paths.get(source);
		if (Files.exists(filePath) && !Files.isDirectory(filePath)
				&& Files.isRegularFile(filePath)) {
			// Lösche die Datei
        	Files.delete(filePath);
        	LOGGER.info("Datei: "+ source +" erfolgreich gelöscht");
        } else {
        	LOGGER.info("Konnte die Datei: "+ source +" nicht löschen.");
        }
    }// Ende deleteFile(String source)

}// Ende class