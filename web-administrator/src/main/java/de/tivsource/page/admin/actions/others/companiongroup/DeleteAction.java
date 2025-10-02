package de.tivsource.page.admin.actions.others.companiongroup;

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
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="companionGroupDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companiongroup/delete_error.jsp")
  }),
  @TilesDefinition(name="companionGroupReferencesError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companiongroup/references_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6941950482152018018L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    private CompanionGroup companionGroup;

    @StrutsParameter(depth=3)
    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

    public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "companionGroupDeleteError"),
        				@Result(name = "error", type="tiles", location = "companionGroupDeleteError"),
                        @Result(name = "references", type="tiles", location = "companionGroupReferencesError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companionGroup != null) {
    	    CompanionGroup dbCompanionGroup = companionGroupDaoLocal.findByUuid(companionGroup.getUuid());
  	        if(!companionGroupDaoLocal.hasReferences(dbCompanionGroup.getUuid())) {
                dbCompanionGroup.setModified(new Date());
                dbCompanionGroup.setModifiedBy(remoteUser);
                dbCompanionGroup.setModifiedAddress(remoteAddress);
                companionGroupDaoLocal.merge(dbCompanionGroup);
                companionGroupDaoLocal.delete(dbCompanionGroup);
                return SUCCESS;
  	        }
   	        return "references";
    	}
    	return ERROR;

    }// Ende execute()
	
}// Ende class
