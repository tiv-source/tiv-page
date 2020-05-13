package de.tivsource.page.admin.actions.locations.reason;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.reason.ReasonDaoLocal;
import de.tivsource.page.entity.request.Reason;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="reasonDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reason/delete_form.jsp")
  }),
  @TilesDefinition(name="reasonDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reason/delete_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2674786870877961693L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="ReasonDao")
    private ReasonDaoLocal reasonDaoLocal;

    private Reason reason;

    /**
     * @return the reason
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    @Override
    @Actions({
        @Action(
            value = "delete",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input", type="tiles", location = "reasonDeleteForm"),
                @Result(name = "error", type="tiles", location = "reasonDeleteError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	if(reason != null) {
    	    Reason dbReason = reasonDaoLocal.findByUuid(reason.getUuid());
    	    reasonDaoLocal.delete(dbReason);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
