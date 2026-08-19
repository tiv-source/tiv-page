package de.tivsource.page.admin.actions.system.province;

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

import de.tivsource.ejb3plugin.InjectEJB;
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
  @TilesDefinition(name="provinceEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/province/edit_form.jsp")
  }),
  @TilesDefinition(name="provinceDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/province/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 4567884800181920666L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="ProvinceDao")
    private ProvinceDaoLocal provinceDaoLocal;

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    private Province province;

    private String uncheckedProvince;

    @Autowired
    public FormAction(PropertyService propertyService) {
        super(propertyService);
    }

    public Province getProvince() {
        return province;
    }

    @StrutsParameter
    public void setUncheckedProvince(String uncheckedProvince) {
        this.uncheckedProvince = uncheckedProvince;
    }

    @Override
    @Actions({
        @Action(
            value = "editForm",
            results = { @Result(name = "success", type="tiles", location = "provinceEditForm") }
        ),
        @Action(
            value = "addForm",
            results = { @Result(name = "success", type="tiles", location = "provinceAddForm") }
        ),
        @Action(
            value = "deleteForm",
            results = { @Result(name = "success", type="tiles", location = "provinceDeleteForm") }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        this.loadPageParameter();
        return SUCCESS;
    }// Ende execute()

    public List<Country> getCountryList() {
        return countryDaoLocal.findAll(0, countryDaoLocal.countAll());
    }

    private void loadPageParameter() {
        if( uncheckedProvince != null && uncheckedProvince != "" && uncheckedProvince.length() > 0) {
            province = provinceDaoLocal.findByUuid(uncheckedProvince);
        } else {
            province = new Province();
        }
    }// Ende loadPageParameter()
	
}// Ende class
