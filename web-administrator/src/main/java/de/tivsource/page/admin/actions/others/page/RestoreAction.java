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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pageRestoreError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/restore_error.jsp")
  })
})
public class RestoreAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 6177022662492674271L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Page page;

    private String uncheckedPage;

    private String revision;

    @StrutsParameter
    public void setUncheckedPage(String uncheckedPage) {
        this.uncheckedPage = uncheckedPage;
    }

    @StrutsParameter
    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    @Actions({
        @Action(
        		value = "restore", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "pageRestoreError"),
        				@Result(name = "error",   type = "tiles", location = "pageRestoreError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
        LOGGER.info("Inhalt uncheckedPage: " + uncheckedPage);
        LOGGER.info("Inhalt revision: " + revision);
        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(
    	    uncheckedPage != null &&
    	    uncheckedPage.length() > 35 &&
    	    revision != null &&
    	    revision.length() > 0
    	) {
    	    Page dbPage = pageDaoLocal.findByUuid(uncheckedPage);
    		page = pageDaoLocal.findRevisionByUuid(uncheckedPage, revision);
    		CSSGroup cssGroup = cssGroupDaoLocal.findByUuid(page.getCssGroup().getUuid());
    		page.setCssGroup(cssGroup);
    		page.setModified(new Date());
    		page.setModifiedBy(remoteUser);
    		page.setVersion(dbPage.getVersion());
    		page.setModifiedAddress(remoteAddress);
    		pageDaoLocal.merge(page);
            return SUCCESS;
    	}
   		return ERROR;
    }// Ende execute()

}// Ende class
