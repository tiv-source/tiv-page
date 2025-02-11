package de.tivsource.page.admin.actions.others.slider;

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
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.entity.slider.Slider;
import de.tivsource.page.entity.slider.SliderImage;
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
	private static final long serialVersionUID = 3555694597200777822L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ImageEditAction.class);

    @InjectEJB(name="SliderDao")
    private SliderDaoLocal sliderDaoLocal;

    private Slider slider;

    @StrutsParameter(depth=3)
    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    @Override
    @Actions({
        @Action(
        		value = "image", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckSlider", "%{slider.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(slider != null) {
    		LOGGER.info("UUID des Slider Objektes: " + slider.getUuid());
    		Slider dbSlider = sliderDaoLocal.findByUuid(slider.getUuid());

    		dbSlider.setModified(new Date());
    		dbSlider.setModifiedBy(remoteUser);
    		dbSlider.setModifiedAddress(remoteAddress);

    		dbSlider.getImage().setUploadFile(slider.getImage().getUploadFile());
    		dbSlider.getImage().generate();
    		dbSlider.getImage().setModified(new Date());
    		dbSlider.getImage().setModifiedAddress(remoteAddress);
    		dbSlider.getImage().setModifiedBy(remoteUser);

    		// TODO: Es werden keine Dateien gelöscht entwerde muss das noch hier hin oder es muss eine Aufräumfunktion geben.
            sliderDaoLocal.merge(dbSlider);
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
            this.slider = new Slider();
            this.slider.setImage(new SliderImage());
            this.slider.getImage().setSlider(this.slider);
            this.slider.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class