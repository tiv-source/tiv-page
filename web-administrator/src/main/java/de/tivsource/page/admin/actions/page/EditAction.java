package de.tivsource.page.admin.actions.page;

import java.util.Date;
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
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2569545967755018736L;

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
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "pageEditForm"),
        				@Result(name = "error",   type = "tiles", location = "pageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	if(page != null) {
    		LOGGER.info(page.getTechnical());
    		Page dbPage = pageDaoLocal.findByUuid(page.getUuid());
    		
    		dbPage.getContentMap().get(Language.DE).setContent(page.getContent(Language.DE));
    		dbPage.getContentMap().get(Language.DE).setModified(new Date());
    		
    		dbPage.getDescriptionMap().get(Language.DE).setDescription(page.getDescription(Language.DE));
    		dbPage.getDescriptionMap().get(Language.DE).setKeywords(page.getKeywords(Language.DE));;
    		dbPage.getDescriptionMap().get(Language.DE).setName(page.getName(Language.DE));
    		
    		dbPage.setModified(new Date());
    		dbPage.setSpecial(page.getSpecial());
    		dbPage.setTechnical(page.getTechnical());
    		dbPage.setVisible(page.getVisible());

    		pageDaoLocal.merge(dbPage);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()


}// Ende class
