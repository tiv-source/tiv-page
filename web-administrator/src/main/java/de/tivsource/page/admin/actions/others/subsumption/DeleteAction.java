package de.tivsource.page.admin.actions.others.subsumption;

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
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.entity.subsumption.Subsumption;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="subsumptionDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/delete_form.jsp")
  }),
  @TilesDefinition(name="subsumptionDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/delete_error.jsp")
  }),
  @TilesDefinition(name="subsumptionDatabaseError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/database_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6684036086884671212L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="SubsumptionDao")
    private SubsumptionDaoLocal subsumptionDaoLocal;

    private Subsumption subsumption;

    public Subsumption getSubsumption() {
        return subsumption;
    }

    public void setSubsumption(Subsumption subsumption) {
        this.subsumption = subsumption;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "subsumptionDeleteForm"),
        				@Result(name = "error", type="tiles", location = "subsumptionDeleteError"),
        				@Result(name = "database", type="tiles", location = "subsumptionDatabaseError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(subsumption != null) {
    	    Subsumption dbSubsumption = subsumptionDaoLocal.findByUuid(subsumption.getUuid());
    		if(!subsumptionDaoLocal.hasMenuEntry(dbSubsumption.getUuid())) {
                dbSubsumption.setModified(new Date());
                dbSubsumption.setModifiedBy(remoteUser);
                dbSubsumption.setModifiedAddress(remoteAddress);
                subsumptionDaoLocal.merge(dbSubsumption);
                subsumptionDaoLocal.delete(dbSubsumption);
                return SUCCESS;
    		} else {
    		    return "database";
    		}
    	}
  		return ERROR;

    }// Ende execute()
	
}// Ende class
