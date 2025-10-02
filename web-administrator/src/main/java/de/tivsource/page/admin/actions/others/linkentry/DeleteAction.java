package de.tivsource.page.admin.actions.others.linkentry;

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
import de.tivsource.page.common.menuentry.LinkEntry;
import de.tivsource.page.dao.linkentry.LinkEntryDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="linkEntryDeleteError", extend = "adminTemplate", putAttributes = {
	@TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
	@TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/linkentry/delete_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 2784414111723220045L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="LinkEntryDao")
    private LinkEntryDaoLocal linkEntryDaoLocal;

    private LinkEntry linkEntry;

    @StrutsParameter(depth=3)
    public LinkEntry getLinkEntry() {
        return linkEntry;
    }

    public void setLinkEntry(LinkEntry linkEntry) {
        this.linkEntry = linkEntry;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "linkEntryDeleteError"),
        				@Result(name = "error", type="tiles", location = "linkEntryDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(linkEntry != null) {
    		LinkEntry dbLinkEntry = linkEntryDaoLocal.findByUuid(linkEntry.getUuid());
    		dbLinkEntry.setModified(new Date());
    		dbLinkEntry.setModifiedBy(remoteUser);
    		dbLinkEntry.setModifiedAddress(remoteAddress);
    		linkEntryDaoLocal.merge(dbLinkEntry);
    		linkEntryDaoLocal.delete(dbLinkEntry);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
