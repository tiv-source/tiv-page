package de.tivsource.page.admin.actions.others.exhibition;

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
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.entity.exhibition.Exhibition;
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
    private static final long serialVersionUID = 6976207436479132631L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ImageEditAction.class);

    @InjectEJB(name="ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    private Exhibition exhibition;

    @StrutsParameter(depth=3)
    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

    @Override
    @Actions({
        @Action(
        		value = "image", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckExhibition", "%{exhibition.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageForm")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(exhibition != null) {
    		LOGGER.info("UUID des Page Objektes: " + exhibition.getUuid());
    		Exhibition dbExhibition = exhibitionDaoLocal.findByUuid(exhibition.getUuid());

    		dbExhibition.setModified(new Date());
    		dbExhibition.setModifiedBy(remoteUser);
    		dbExhibition.setModifiedAddress(remoteAddress);

    		dbExhibition.getImage().setUploadFile(exhibition.getImage().getUploadFile());
    		dbExhibition.getImage().generate();
    		dbExhibition.getImage().setModified(new Date());
    		dbExhibition.getImage().setModifiedAddress(remoteAddress);
    		dbExhibition.getImage().setModifiedBy(remoteUser);

    		// TODO: Es werden keine Dateien gelöscht entwerde muss das noch hier hin oder es muss eine Aufräumfunktion geben.
            exhibitionDaoLocal.merge(dbExhibition);
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
            this.exhibition = new Exhibition();
            this.exhibition.setImage(new PictureItemImage());
            this.exhibition.getImage().setPictureItem(this.exhibition);
            this.exhibition.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class