package de.tivsource.page.admin.actions.others.slider;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

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
@TilesDefinitions({
  @TilesDefinition(name="sliderAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/slider/add_form.jsp")
  }),
  @TilesDefinition(name="sliderAddError",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/slider/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -1758318695882961832L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "SliderDao")
    private SliderDaoLocal sliderDaoLocal;

    private Slider slider;

    @StrutsParameter(depth=2)
    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    @Override
    @Actions({
        @Action(
            value = "add",
            results = {
                    @Result(name = "success", type = "redirectAction", location = "index.html"),
                    @Result(name = "input", type="tiles", location = "sliderAddForm"),
                    @Result(name = "error", type="tiles", location = "sliderAddError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(slider != null) {
            slider.setUuid(UUID.randomUUID().toString());

            slider.setCreated(new Date());
            slider.setModified(new Date());
            slider.setModifiedAddress(remoteAddress);
            slider.setModifiedBy(remoteUser);

            slider.getImage().setUuid(UUID.randomUUID().toString());
            slider.getImage().generate();
            slider.getImage().setCreated(new Date());
            slider.getImage().setModified(new Date());
            slider.getImage().setModifiedAddress(remoteAddress);
            slider.getImage().setModifiedBy(remoteUser);

            sliderDaoLocal.merge(slider);

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
