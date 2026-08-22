package de.tivsource.page.admin.actions.system.province;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.province.Province;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.province.ProvinceDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="provinceAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/province/add_form.jsp")
  }),
  @TilesDefinition(name="provinceAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/province/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -7248219992720508453L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @Autowired
    @Qualifier("provinceDaoLocal")
    private ProvinceDaoLocal provinceDaoLocal;

    @Autowired
    @Qualifier("countryDaoLocal")
    private CountryDaoLocal countryDaoLocal;

    private Province province;

    @Autowired
    public AddAction(PropertyService propertyService) {
        super(propertyService);
    }

    /**
     * @return the province
     */
    @StrutsParameter(depth=3)
    public Province getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    @Actions({
        @Action(
            value = "add",
            results = {
                    @Result(name = "success", type = "redirectAction", location = "index.html"),
                    @Result(name = "input", type="tiles", location = "provinceAddForm"),
                    @Result(name = "error", type="tiles", location = "provinceAddError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(province != null) {
        	if(province.getCode().length() > 3) {
        		LOGGER.info("Country Alpha2-Code: " + province.getCountry().getAlpha2());
        		if(!province.getCode().startsWith(province.getCountry().getAlpha2() + "-")) {
        			addFieldError("province.code", getText("province.validation.startwith.code"));
        			return INPUT;
        		}
        	}
            province.setUuid(UUID.randomUUID().toString());
            province.getDescription("de").setUuid(UUID.randomUUID().toString());
            province.getDescription("de").setProvince(province);
            province.getDescription("de").setLocale("de");
            province.getDescription("en").setUuid(UUID.randomUUID().toString());
            province.getDescription("en").setProvince(province);
            province.getDescription("en").setLocale("en");
            province.setCreated(new Date());
            province.setModified(new Date());
            province.setModifiedBy(remoteUser);
            province.setModifiedAddress(remoteAddress);
            provinceDaoLocal.merge(province);
            return SUCCESS;
        }
        return ERROR;
    }// Ende execute()

    public List<Country> getCountryList() {
        return countryDaoLocal.findAll(0, countryDaoLocal.countAll());
    }

}// Ende class
