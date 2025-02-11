package de.tivsource.page.admin.actions.maintenance.cssgroup;

import java.util.Arrays;
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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssGroupAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/add_form.jsp")
  }),
  @TilesDefinition(name="cssGroupEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/edit_form.jsp")
  }),
  @TilesDefinition(name="cssGroupDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 6831699478826775351L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="CSSFileDao")
    private CSSFileDaoLocal cssFileDaoLocal;

    @InjectEJB(name="CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    private CSSGroup cssGroup;

    private String uncheckCssGroup;

    public CSSGroup getCssGroup() {
        return cssGroup;
    }

	/**
     * @param uncheckCssGroup the uncheckCssGroup to set
     */
    @StrutsParameter
    public void setUncheckCssGroup(String uncheckCssGroup) {
        this.uncheckCssGroup = uncheckCssGroup;
    }

    @Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "cssGroupEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "cssGroupAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "cssGroupDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<CSSFile> getCssFileList() {
	    return cssFileDaoLocal.findAll(0, cssFileDaoLocal.countAll());
	}

	public List<Language> getLanguageList() {
		return Arrays.asList(Language.values());
	}

	private void loadPageParameter() {
		if( uncheckCssGroup != null && uncheckCssGroup != "" && uncheckCssGroup.length() > 0) {
		    cssGroup = cssGroupDaoLocal.findByUuid(uncheckCssGroup);
		} else {
		    cssGroup = new CSSGroup();
		}
	}// Ende loadPageParameter()

}// Ende class
