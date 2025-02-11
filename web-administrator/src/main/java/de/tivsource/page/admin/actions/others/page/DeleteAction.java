package de.tivsource.page.admin.actions.others.page;

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
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pageDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/delete_error.jsp")
  }),
  @TilesDefinition(name="pageDatabaseError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/database_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1007882449894584851L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    private Page page;

    @StrutsParameter(depth=3)
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pageDeleteError"),
        				@Result(name = "error", type="tiles", location = "pageDeleteError"),
        				@Result(name = "database", type="tiles", location = "pageDatabaseError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(page != null) {
    		Page dbPage = pageDaoLocal.findByUuid(page.getUuid());
    		if(!pageDaoLocal.hasMenuEntry(dbPage.getUuid())) {
                dbPage.setModified(new Date());
                dbPage.setModifiedBy(remoteUser);
                dbPage.setModifiedAddress(remoteAddress);
                pageDaoLocal.merge(dbPage);
                pageDaoLocal.delete(dbPage);
                return SUCCESS;
    		}
    		return "database";
    	}
  		return ERROR;

    }// Ende execute()
	
}// Ende class
