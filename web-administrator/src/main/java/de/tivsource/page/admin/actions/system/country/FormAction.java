package de.tivsource.page.admin.actions.system.country;

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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.country.Country;
import de.tivsource.page.dao.country.CountryDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="countryAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/add_form.jsp")
  }),
  @TilesDefinition(name="countryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/edit_form.jsp")
  }),
  @TilesDefinition(name="countryDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 5351906951675405040L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    private Country country;

    private String uncheckCountry;

    @Autowired
    public FormAction(PropertyService propertyService) {
        super(propertyService);
    }

    public Country getCountry() {
        return country;
    }

    @StrutsParameter
    public void setUncheckCountry(String uncheckCountry) {
        this.uncheckCountry = uncheckCountry;
    }

    @Override
    @Actions({
        @Action(
            value = "editForm",
            results = { @Result(name = "success", type="tiles", location = "countryEditForm") }
        ),
        @Action(
            value = "addForm",
            results = { @Result(name = "success", type="tiles", location = "countryAddForm") }
        ),
        @Action(
            value = "deleteForm",
            results = { @Result(name = "success", type="tiles", location = "countryDeleteForm") }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        this.loadPageParameter();
        return SUCCESS;
    }// Ende execute()
	
    private void loadPageParameter() {
        if( uncheckCountry != null && uncheckCountry != "" && uncheckCountry.length() > 0) {
            country = countryDaoLocal.findByUuid(uncheckCountry);
        } else {
            country = new Country();
        }
    }// Ende loadPageParameter()
	
}// Ende class
