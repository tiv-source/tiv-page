package de.tivsource.page.admin.actions.others.slider;

import java.util.Date;
import java.util.UUID;

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
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.entity.slider.Slider;
import de.tivsource.page.entity.slider.SliderImage;

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
public class AddAction extends EmptyAction {

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

    private SliderImage sliderImage;

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public SliderImage getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(SliderImage sliderImage) {
        this.sliderImage = sliderImage;
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

            sliderImage.setSlider(slider);
            sliderImage.setUuid(UUID.randomUUID().toString());
            sliderImage.generate();
            sliderImage.setCreated(new Date());
            sliderImage.setModified(new Date());
            sliderImage.setModifiedAddress(remoteAddress);
            sliderImage.setModifiedBy(remoteUser);
            slider.setImage(sliderImage);

            slider.setCreated(new Date());
            slider.setModified(new Date());
            slider.setModifiedAddress(remoteAddress);
            slider.setModifiedBy(remoteUser);
            sliderDaoLocal.merge(slider);

            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }// Ende execute()

}// Ende class
