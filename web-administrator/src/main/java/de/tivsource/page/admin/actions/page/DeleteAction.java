package de.tivsource.page.admin.actions.page;

import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -3899799655003992292L;

	/**
	 * Statischer Logger der Klasse.
	 */
	private static final Logger LOGGER = Logger.getLogger("INFO");

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    private Page page;

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
        				@Result(name = "input", type="tiles", location = "pageDeleteForm"),
        				@Result(name = "error", type="tiles", location = "pageDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	if(page != null) {
    		Page dbPage = pageDaoLocal.findByUuid(page.getUuid());
    		pageDaoLocal.delete(dbPage);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
