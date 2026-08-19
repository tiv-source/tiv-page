package de.tivsource.page.admin.actions.system.country;

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
  @TilesDefinition(name="countryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/edit_form.jsp")
  }),
  @TilesDefinition(name="countryEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 257630093490687722L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    private Country country;

    @Autowired
    public EditAction(PropertyService propertyService) {
        super(propertyService);
    }

    /**
     * @return the country
     */
    @StrutsParameter(depth=3)
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    @Actions({
        @Action(
            value = "edit",
            results = {
                    @Result(name = "success", type = "redirectAction", location = "index.html"),
                    @Result(name = "input",   type = "tiles", location = "countryEditForm"),
                    @Result(name = "error",   type = "tiles", location = "countryEditError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(country != null) {
            LOGGER.info("Country: " + country.getAlpha2());
            Country dbCountry = countryDaoLocal.findByUuid(country.getUuid());
            dbCountry.setAlpha2(country.getAlpha2());
            dbCountry.setAlpha3(country.getAlpha3());
            dbCountry.setAreaCode(country.getAreaCode());
            dbCountry.setLanguage(country.getLanguage());
            dbCountry.setNumeric(country.getNumeric());
            dbCountry.getDescription("de").setName(country.getDescription("de").getName());
            dbCountry.getDescription("en").setName(country.getDescription("en").getName());
            dbCountry.setModified(new Date());
            dbCountry.setModifiedBy(remoteUser);
            dbCountry.setModifiedAddress(remoteAddress);
            countryDaoLocal.merge(dbCountry);
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }// Ende execute()

}// Ende class
