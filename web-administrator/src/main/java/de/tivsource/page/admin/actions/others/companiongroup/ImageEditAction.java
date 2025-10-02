package de.tivsource.page.admin.actions.others.companiongroup;

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
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;
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
	private static final long serialVersionUID = 1821284906744944681L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ImageEditAction.class);

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    private CompanionGroup companionGroup;

    @StrutsParameter(depth=3)
    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

    public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
    }

    @Override
    @Actions({
        @Action(
        		value = "image", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckPage", "%{companionGroup.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companionGroup != null) {
    		LOGGER.info("UUID des Page Objektes: " + companionGroup.getUuid());
    		CompanionGroup dbCompanionGroup = companionGroupDaoLocal.findByUuid(companionGroup.getUuid());

    		dbCompanionGroup.setModified(new Date());
    		dbCompanionGroup.setModifiedBy(remoteUser);
    		dbCompanionGroup.setModifiedAddress(remoteAddress);

    		dbCompanionGroup.getImage().setUploadFile(companionGroup.getImage().getUploadFile());
    		dbCompanionGroup.getImage().generate();
    		dbCompanionGroup.getImage().setModified(new Date());
    		dbCompanionGroup.getImage().setModifiedAddress(remoteAddress);
    		dbCompanionGroup.getImage().setModifiedBy(remoteUser);

    		// TODO: Es werden keine Dateien gelöscht entwerde muss das noch hier hin oder es muss eine Aufräumfunktion geben.
            companionGroupDaoLocal.merge(dbCompanionGroup);
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
            this.companionGroup = new CompanionGroup();
            this.companionGroup.setImage(new PictureItemImage());
            this.companionGroup.getImage().setPictureItem(this.companionGroup);
            this.companionGroup.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class