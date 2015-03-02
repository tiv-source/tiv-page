package de.tivsource.page.admin.actions.page;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2470356424347699870L;

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
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pageAddForm"),
        				@Result(name = "error", type="tiles", location = "pageAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	if(page != null) {
    	    page.setUuid(UUID.randomUUID().toString());
    	    page.setModified(new Date());
    	    page.setCreated(new Date());
    	    
    	    page.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    page.getDescriptionMap().get(Language.DE).setNamingItem(page);
    	    page.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    
    	    page.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    page.getContentMap().get(Language.DE).setContentItem(page);
    	    page.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    page.getContentMap().get(Language.DE).setCreated(new Date());
    	    page.getContentMap().get(Language.DE).setModified(new Date());

    		pageDaoLocal.merge(page);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
