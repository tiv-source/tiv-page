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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.country.Country;
import de.tivsource.page.dao.country.CountryDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="countryDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/delete_form.jsp")
  }),
  @TilesDefinition(name="countryDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/country/delete_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -5562077802891547914L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;
    
    private Country country;

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
            value = "delete",
            results = {
                    @Result(name = "success", type = "redirectAction", location = "index.html"),
                    @Result(name = "input", type="tiles", location = "countryDeleteForm"),
                    @Result(name = "error", type="tiles", location = "countryDeleteError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(country != null) {
            Country dbCountry = countryDaoLocal.findByUuid(country.getUuid());
            dbCountry.setModified(new Date());
            dbCountry.setModifiedBy(remoteUser);
            dbCountry.setModifiedAddress(remoteAddress);
            countryDaoLocal.merge(dbCountry);
            countryDaoLocal.delete(dbCountry);
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }// Ende execute()

}// Ende class
