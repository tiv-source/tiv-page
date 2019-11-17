package de.tivsource.page.admin.actions.maintenance.cssfile;

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
import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssFileDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/delete_form.jsp")
  }),
  @TilesDefinition(name="cssFileDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/delete_error.jsp")
  }),
  @TilesDefinition(name="cssFileReferences", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/references.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -4785386555841300052L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="CSSFileDao")
    private CSSFileDaoLocal cssFileDaoLocal;

    private CSSFile cssFile;

    public CSSFile getCssFile() {
        return cssFile;
    }

    public void setCssFile(CSSFile cssFile) {
        this.cssFile = cssFile;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "cssFileDeleteForm"),
        				@Result(name = "error", type="tiles", location = "cssFileDeleteError"),
        				@Result(name = "references", type="tiles", location = "cssFileReferences")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");


        if(cssFile != null) {
            if(!cssFileDaoLocal.hasReferences(cssFile.getUuid())) {
            	CSSFile dbCssFile = cssFileDaoLocal.findByUuid(cssFile.getUuid());
                cssFileDaoLocal.delete(dbCssFile);
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
