package de.tivsource.page.admin.actions.page;

import org.apache.log4j.Logger;
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
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4373324222455911836L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(FormAction.class);

	@InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

	private Page page;

	private String uncheckPage;

	private String lang;

	public Page getPage() {
        return page;
    }

	public void setPage(String uncheckPage) {
        this.uncheckPage = uncheckPage;
    }

	public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "pageEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "pageAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "pageDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {

		if( uncheckPage != null && uncheckPage != "" && uncheckPage.length() > 0) {
			page = pageDaoLocal.findByUuid(uncheckPage);
		} else {
			page = new Page();
		}

	}// Ende loadPageParameter()
	
}// Ende class
