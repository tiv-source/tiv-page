package de.tivsource.page.admin.actions.others.manual;

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
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.entity.manual.Manual;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="manualDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/delete_error.jsp")
  }),
  @TilesDefinition(name="manualMenuEntryError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/menuentry_error.jsp")
  }),
  @TilesDefinition(name="manualSubSumptionError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/subsumption_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -8955242912078594369L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    private Manual manual;

    @StrutsParameter(depth=3)
    public Manual getManual() {
        return manual;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
    }

	@Override
    @Actions({
        @Action(
            value = "delete",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input", type="tiles", location = "manualDeleteError"),
                @Result(name = "error", type="tiles", location = "manualDeleteError"),
                @Result(name = "menuentry", type="tiles", location = "manualMenuEntryError"),
                @Result(name = "subsumption", type="tiles", location = "manualSubSumptionError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(manual != null) {
    		Manual dbManual = manualDaoLocal.findByUuid(manual.getUuid());
    		if(!manualDaoLocal.hasMenuEntry(dbManual.getUuid())) {
    		    if(!manualDaoLocal.hasSubSumption(dbManual.getUuid())) {
                    dbManual.setModified(new Date());
                    dbManual.setModifiedBy(remoteUser);
                    dbManual.setModifiedAddress(remoteAddress);
                    manualDaoLocal.merge(dbManual);
                    manualDaoLocal.delete(dbManual);
                    return SUCCESS;
    		    }
    		    return "subsumption";
    		}
    		return "menuentry";
    	}
   		return ERROR;
    	
    }// Ende execute()
	
}// Ende class
