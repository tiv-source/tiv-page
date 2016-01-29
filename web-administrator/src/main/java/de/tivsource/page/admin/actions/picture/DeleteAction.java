package de.tivsource.page.admin.actions.picture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.log4j.Logger;
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
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2086102480376636224L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteAction.class);

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Picture picture;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pictureDeleteForm"),
        				@Result(name = "error", type="tiles", location = "pictureDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	if(picture != null) {
    		Picture dbPicture = pictureDaoLocal.findByUuid(picture.getUuid());
    		deletePictures(dbPicture.getPictureUrls());
    	    pictureDaoLocal.delete(dbPicture);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    private static void deletePictures(Map<UrlType, PictureUrl> pictureUrls) throws IOException {
    	String picturePath = "/srv/www/htdocs/pictures/";
    	String pathFULL = picturePath + "FULL/" + pictureUrls.get(UrlType.FULL).getUrl();
    	deleteFile(pathFULL);
    	String pathLARGE = picturePath + "LARGE/" + pictureUrls.get(UrlType.LARGE).getUrl();
    	deleteFile(pathLARGE);
    	String pathNORMAL = picturePath + "NORMAL/" + pictureUrls.get(UrlType.NORMAL).getUrl();
    	deleteFile(pathNORMAL);
    	String pathTHUMBNAIL = picturePath + "THUMBNAIL/" + pictureUrls.get(UrlType.THUMBNAIL).getUrl();
    	deleteFile(pathTHUMBNAIL);
    }

    private static void deleteFile(String source) throws IOException {
        String s = null;

        Process p = Runtime.getRuntime().exec(
                "/bin/rm " + source
                );

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }

}// Ende class