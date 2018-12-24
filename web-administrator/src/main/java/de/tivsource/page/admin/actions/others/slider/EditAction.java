package de.tivsource.page.admin.actions.others.slider;

import java.util.Date;

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
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.entity.slider.Slider;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="sliderEditForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/slider/edit_form.jsp")
  }),
  @TilesDefinition(name="sliderEditError",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/slider/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6472541996524122642L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name = "SliderDao")
    private SliderDaoLocal sliderDaoLocal;

    private Slider slider;

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "sliderEditForm"),
        				@Result(name = "error",   type = "tiles", location = "sliderEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(slider != null) {
    		LOGGER.info("UUID des Sliders: " + slider.getUuid());
    		Slider dbSlider = sliderDaoLocal.findByUuid(slider.getUuid());

    		dbSlider.setClickable(slider.getClickable());
    		dbSlider.setModified(new Date());
    		dbSlider.setModifiedAddress(remoteAddress);
    		dbSlider.setModifiedBy(remoteUser);
    		dbSlider.setName(slider.getName());
    		dbSlider.setOrderNumber(slider.getOrderNumber());
    		dbSlider.setPage(slider.getPage());
    		dbSlider.setUrl(slider.getUrl());
    		dbSlider.setVisible(slider.getVisible());

    		sliderDaoLocal.merge(dbSlider);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
