package de.tivsource.page.admin.actions.system.property;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="propertyProtectError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/property/protect_error.jsp")
  }),
  @TilesDefinition(name="propertyProtectUnchangeable", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/property/protect_unchangeable.jsp")
  })
})
public class ProtectAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7844615960862403846L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ProtectAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Property property;

    @StrutsParameter(depth=1)
    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    @Actions({
        @Action(
        		value = "protect", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "propertyProtectError"),
        				@Result(name = "error", type="tiles", location = "propertyProtectError"),
                        @Result(name = "unchangeable", type = "tiles", location = "propertyProtectUnchangeable")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(property != null) {
            LOGGER.info(property.getKey());
    	    Property dbProperty = propertyDaoLocal.findByKey(property.getKey());
            if(!dbProperty.getUnchangeable()) {
                dbProperty.setUnchangeable(true);
                dbProperty.setModified(new Date());
                dbProperty.setModifiedBy(remoteUser);
                dbProperty.setModifiedAddress(remoteAddress);
                propertyDaoLocal.merge(dbProperty);
                return SUCCESS;
            }
            return "unchangeable";
    	}
   		return ERROR;
    	
    }// Ende execute()
	
}// Ende class
