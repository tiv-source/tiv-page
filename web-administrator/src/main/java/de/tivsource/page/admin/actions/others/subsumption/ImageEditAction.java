package de.tivsource.page.admin.actions.others.subsumption;

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
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.entity.subsumption.Subsumption;
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
	private static final long serialVersionUID = -5115261352026533957L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ImageEditAction.class);

    @InjectEJB(name="SubsumptionDao")
    private SubsumptionDaoLocal subsumptionDaoLocal;

    private Subsumption subsumption;

    @StrutsParameter(depth=3)
    public Subsumption getSubsumption() {
        return subsumption;
    }

    public void setSubsumption(Subsumption subsumption) {
        this.subsumption = subsumption;
    }

    @Override
    @Actions({
        @Action(
        		value = "image", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckSubsumption", "%{subsumption.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageEditError"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(subsumption != null) {
    		LOGGER.info("UUID des Page Objektes: " + subsumption.getUuid());
    		Subsumption dbSubsumption = subsumptionDaoLocal.findByUuid(subsumption.getUuid());

    		dbSubsumption.setModified(new Date());
    		dbSubsumption.setModifiedBy(remoteUser);
    		dbSubsumption.setModifiedAddress(remoteAddress);

    		dbSubsumption.getImage().setUploadFile(subsumption.getImage().getUploadFile());
    		dbSubsumption.getImage().generate();
    		dbSubsumption.getImage().setModified(new Date());
    		dbSubsumption.getImage().setModifiedAddress(remoteAddress);
    		dbSubsumption.getImage().setModifiedBy(remoteUser);

    		// TODO: Es werden keine Dateien gelöscht entwerde muss das noch hier hin oder es muss eine Aufräumfunktion geben.
            subsumptionDaoLocal.merge(dbSubsumption);
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
            this.subsumption = new Subsumption();
            this.subsumption.setImage(new PictureItemImage());
            this.subsumption.getImage().setPictureItem(this.subsumption);
            this.subsumption.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class