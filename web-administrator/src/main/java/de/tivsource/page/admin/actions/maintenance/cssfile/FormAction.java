package de.tivsource.page.admin.actions.maintenance.cssfile;

import java.util.List;

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
import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssFileAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/add_form.jsp")
  }),
  @TilesDefinition(name="cssFileEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/edit_form.jsp")
  }),
  @TilesDefinition(name="cssFileDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -3282620598068445927L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @Autowired
    @Qualifier("cssFileDaoLocal")
    private CSSFileDaoLocal cssFileDaoLocal;

    private CSSFile cssFile;

    private String uncheckCssFile;

    private List<Object[]> revisions = null;

    @Autowired
    public FormAction(PropertyService propertyService) {
        super(propertyService);
    }

    public CSSFile getCssFile() {
        return cssFile;
    }

	/**
     * @param uncheckCssFile the uncheckCssFile to set
     */
    @StrutsParameter
    public void setUncheckCssFile(String uncheckCssFile) {
        this.uncheckCssFile = uncheckCssFile;
    }

	@Override
    @Actions({
        @Action(
            value = "editForm",
            results = { @Result(name = "success", type="tiles", location = "cssFileEditForm") }
        ),
        @Action(
            value = "addForm",
            results = { @Result(name = "success", type="tiles", location = "cssFileAddForm") }
        ),
        @Action(
            value = "deleteForm",
            results = { @Result(name = "success", type="tiles", location = "cssFileDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<Object[]> getRevisions() {
        return revisions;
    }

	private void loadPageParameter() {
		if( uncheckCssFile != null && uncheckCssFile != "" && uncheckCssFile.length() > 0) {
		    // TODO: Check ob UUID auch zu einer Seite gehört !!!
		    cssFile = cssFileDaoLocal.findByUuid(uncheckCssFile);
            revisions = cssFileDaoLocal.getRevisionsByUuid(uncheckCssFile);
		} else {
		    cssFile = new CSSFile();
		}
	}// Ende loadPageParameter()

}// Ende class
