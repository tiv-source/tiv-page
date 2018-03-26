package de.tivsource.page.admin.actions.others.picture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.entity.picture.PictureUrl;
import de.tivsource.page.enumeration.UrlType;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pictureAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/picture/add_form.jsp")
  })
})
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5152279376430754651L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

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
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pictureAddForm"),
        				@Result(name = "error", type="tiles", location = "pictureAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(picture != null) {
    	    picture.setUuid(UUID.randomUUID().toString());
    	    picture.setModified(new Date());
    	    picture.setCreated(new Date());
    	    picture.setModifiedBy(remoteUser);
    	    picture.setModifiedAddress(remoteAddress);

    	    picture.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    picture.getDescriptionMap().get(Language.DE).setPicture(picture);
    	    picture.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = picture.getDescriptionMap().get(Language.DE).getDescription().replaceAll("(\\r|\\n)", "");
    	    picture.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    picture.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    picture.getDescriptionMap().get(Language.EN).setPicture(picture);
    	    picture.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    picture.getDescriptionMap().get(Language.EN).setName(picture.getDescriptionMap().get(Language.DE).getName());
    	    picture.getDescriptionMap().get(Language.EN).setDescription(picture.getDescriptionMap().get(Language.DE).getDescription());
    	    picture.getDescriptionMap().get(Language.EN).setKeywords(picture.getDescriptionMap().get(Language.DE).getKeywords());

    	    
    	    
    	    if(file != null) {

                // Pfad in dem die Bild Datei gespeichert wird.
    	    	String generatePath = "/var/www/html/pictures/";
                String uploadPath = generatePath + "FULL/";

                // Name der Bild Datei die erstellt werden soll. 
                String pictureSaveName = DigestUtils.shaHex("Hier ist das Geheimniss."
                    + file.getName() + new Date() + "Noch ein bischen.")
                    + ".png";

                File fullPictureFileToCreate = new File(uploadPath, pictureSaveName);
                // Wenn die Datei noch nicht existiert wird Sie erstellt.
                if (!fullPictureFileToCreate.exists()) {
                    savePictureFile(file, fullPictureFileToCreate);
                }

            	createNormal(uploadPath + pictureSaveName, generatePath
            		+ "NORMAL/" + pictureSaveName);
            	createThumbnail(uploadPath + pictureSaveName, generatePath
            		+ "THUMBNAIL/" + pictureSaveName);
            	createLarge(uploadPath + pictureSaveName, generatePath
            		+ "LARGE/" + pictureSaveName);

            	// Setzte die Urls in das Bild.
            	picture.setPictureUrls(generatePictureUrls(pictureSaveName, picture));
    	    }

    	    // Speichere Bild in der Datenbank
    	    pictureDaoLocal.merge(picture);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

	public List<Gallery> getGalleryList() {
		return galleryDaoLocal.findAll(0, galleryDaoLocal.countAll());
	}

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
    
}// Ende class
