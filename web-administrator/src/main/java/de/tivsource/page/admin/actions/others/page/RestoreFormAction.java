package de.tivsource.page.admin.actions.others.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pageRestoreForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/restore_form.jsp")
  })
})
public class RestoreFormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4815836065256123074L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreFormAction.class);

	@Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

	private Page page;

	private String uncheckedPage;

	private String revision;

    @Autowired
    public RestoreFormAction(PropertyService propertyService) {
        super(propertyService);
    }

	public Page getPage() {
        return page;
    }

	@StrutsParameter
	public void setUncheckedPage(String uncheckedPage) {
        this.uncheckedPage = uncheckedPage;
    }

    public String getUncheckedPage() {
        return uncheckedPage;
    }

    @StrutsParameter
    public void setRevision(String revision) {
        this.revision = revision;
    }

	public String getRevision() {
        return revision;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    @Actions({
        @Action(
        		value = "restoreForm", 
        		results = { @Result(name = "success", type="tiles", location = "pageRestoreForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {
		if( uncheckedPage != null && uncheckedPage != "" && uncheckedPage.length() > 0) {
		    // TODO: Check ob UUID auch zu einer Seite gehört !!!
		    page = pageDaoLocal.findRevisionByUuid(uncheckedPage, revision);
		}
	}// Ende loadPageParameter()

}// Ende class
