package de.tivsource.page.admin.actions.newsletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
    @TilesDefinition(name="newsletter", extend = "adminTemplate", putAttributes = {
        @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/newsletter.jsp"),
        @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/newsletter.jsp")
        
    })
})
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6738975738920612510L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @Autowired
    public IndexAction(PropertyService propertyService) {
        super(propertyService);
    }

    @Override
    @Actions({ 
        @Action(
            value = "index", 
            results = { @Result(name = "success", type = "tiles", location = "newsletter") }
        ) 
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        return SUCCESS;
    }// Ende execute()

}// Ende class
