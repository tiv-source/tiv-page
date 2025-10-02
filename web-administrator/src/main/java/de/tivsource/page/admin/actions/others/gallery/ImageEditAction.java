package de.tivsource.page.admin.actions.others.gallery;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
public class ImageEditAction extends EmptyAction implements UploadedFilesAware {

    /**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -1273453443853067369L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ImageEditAction.class);

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Gallery gallery;

    @StrutsParameter(depth=3)
    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    @Override
    @Actions({
        @Action(
        		value = "image", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckGallery", "%{gallery.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(gallery != null) {
    		LOGGER.info("UUID des Gallery Objektes: " + gallery.getUuid());
    		Gallery dbGallery = galleryDaoLocal.findByUuid(gallery.getUuid());

    		dbGallery.setModified(new Date());
    		dbGallery.setModifiedBy(remoteUser);
    		dbGallery.setModifiedAddress(remoteAddress);

    		dbGallery.getImage().setUploadFile(gallery.getImage().getUploadFile());
    		dbGallery.getImage().generate();
    		dbGallery.getImage().setModified(new Date());
    		dbGallery.getImage().setModifiedAddress(remoteAddress);
    		dbGallery.getImage().setModifiedBy(remoteUser);

    		// TODO: Es werden keine Dateien gelöscht entwerde muss das noch hier hin oder es muss eine Aufräumfunktion geben.
            galleryDaoLocal.merge(dbGallery);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.gallery = new Gallery();
            this.gallery.setImage(new PictureItemImage());
            this.gallery.getImage().setPictureItem(this.gallery);
            this.gallery.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class