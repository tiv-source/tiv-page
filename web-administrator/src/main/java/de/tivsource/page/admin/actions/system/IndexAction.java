package de.tivsource.page.admin.actions.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.page.admin.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
    @TilesDefinition(name="system", extend = "adminTemplate", putAttributes = {
        @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
        @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/system.jsp")
        
    })
})
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -6456476945155050186L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @Override
    @Actions({ 
        @Action(
            value = "index", 
            results = { @Result(name = "success", type = "tiles", location = "system") }
        ) 
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        return SUCCESS;
    }// Ende execute()

}// Ende class