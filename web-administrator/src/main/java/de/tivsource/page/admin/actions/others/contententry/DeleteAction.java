package de.tivsource.page.admin.actions.others.contententry;

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
import de.tivsource.page.common.menuentry.ContentEntry;
import de.tivsource.page.dao.contententry.ContentEntryDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="contentEntryDeleteError", extend = "adminTemplate", putAttributes = {
	@TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
	@TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/delete_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -8831104942787907704L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="ContentEntryDao")
    private ContentEntryDaoLocal contentEntryDaoLocal;

    private ContentEntry contentEntry;

    @StrutsParameter(depth=3)
    public ContentEntry getContentEntry() {
        return contentEntry;
    }

    public void setContentEntry(ContentEntry contentEntry) {
        this.contentEntry = contentEntry;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "contentEntryDeleteError"),
        				@Result(name = "error", type="tiles", location = "contentEntryDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(contentEntry != null) {
    		ContentEntry dbContentEntry = contentEntryDaoLocal.findByUuid(contentEntry.getUuid());
    		dbContentEntry.setModified(new Date());
    		dbContentEntry.setModifiedBy(remoteUser);
    		dbContentEntry.setModifiedAddress(remoteAddress);
    		contentEntryDaoLocal.merge(dbContentEntry);
    		contentEntryDaoLocal.delete(dbContentEntry);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
