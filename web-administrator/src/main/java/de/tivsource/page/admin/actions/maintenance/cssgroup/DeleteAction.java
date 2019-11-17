package de.tivsource.page.admin.actions.maintenance.cssgroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssGroupDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/delete_form.jsp")
  }),
  @TilesDefinition(name="cssGroupDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/delete_error.jsp")
  }),
  @TilesDefinition(name="cssGroupReferences", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/references.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 8847714905311859127L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    private CSSGroup cssGroup;

    /**
	 * @return the cssGroup
	 */
	public CSSGroup getCssGroup() {
		return cssGroup;
	}

	/**
	 * @param cssGroup the cssGroup to set
	 */
	public void setCssGroup(CSSGroup cssGroup) {
		this.cssGroup = cssGroup;
	}

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "cssGroupDeleteForm"),
        				@Result(name = "error", type="tiles", location = "cssGroupDeleteError"),
        				@Result(name = "references", type="tiles", location = "cssGroupReferences")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");


        if(cssGroup != null) {
            if(!cssGroupDaoLocal.hasReferences(cssGroup.getUuid())) {
            	CSSGroup dbCSSGroup = cssGroupDaoLocal.findByUuid(cssGroup.getUuid());
                cssGroupDaoLocal.delete(dbCSSGroup);
                return SUCCESS;
            }
            else {
            	return "references";
            }
    	}
        else {
    		return ERROR;
    	}

    }// Ende execute()
	
}// Ende class
