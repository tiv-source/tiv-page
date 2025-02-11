package de.tivsource.page.admin.actions.others.exhibition;

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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.enumeration.CityType;
import de.tivsource.page.entity.enumeration.CountryType;
import de.tivsource.page.entity.exhibition.Exhibition;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="exhibitionAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/add_form.jsp")
  }),
  @TilesDefinition(name="exhibitionEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/edit_form.jsp")
  }),
  @TilesDefinition(name="exhibitionDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/delete_form.jsp")
  }),
  @TilesDefinition(name="imageForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/image_form.jsp")
  })
})
public class FormAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1610328819020449373L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    private Exhibition exhibition;

    private String uncheckExhibition;

    private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    public Exhibition getExhibition() {
        return exhibition;
    }

    @StrutsParameter
    public void setUncheckExhibition(String uncheckExhibition) {
        this.uncheckExhibition = uncheckExhibition;
    }

    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
            value = "editForm",
            results = { @Result(name = "success", type="tiles", location = "exhibitionEditForm") }
        ),
        @Action(
            value = "addForm",
            results = { @Result(name = "success", type="tiles", location = "exhibitionAddForm") }
        ),
        @Action(
            value = "deleteForm",
            results = { @Result(name = "success", type="tiles", location = "exhibitionDeleteForm") }
        ),
        @Action(
            value = "imageForm", 
            results = { @Result(name = "success", type="tiles", location = "imageForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	// Hole Action Locale
    	this.getLanguageFromActionContext();
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<CountryType> getCountryList() {
        return Arrays.asList(CountryType.values());
    }

    public List<CityType> getCityList() {
        return Arrays.asList(CityType.values());
    }

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    private void loadPageParameter() {
        if( uncheckExhibition != null && uncheckExhibition != "" && uncheckExhibition.length() > 0) {
            exhibition = exhibitionDaoLocal.findByUuid(uncheckExhibition);
        } else {
            exhibition = new Exhibition();
        }
    }// Ende loadPageParameter()

}// Ende class
