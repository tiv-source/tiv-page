package de.tivsource.page.admin.actions.system.province;

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
import de.tivsource.page.common.province.Province;
import de.tivsource.page.dao.province.ProvinceDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="provinceDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/province/delete_form.jsp")
  }),
  @TilesDefinition(name="provinceDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/province/delete_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -1535935130505224276L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="ProvinceDao")
    private ProvinceDaoLocal provinceDaoLocal;
    
    private Province province;

    @Autowired
    public DeleteAction(PropertyService propertyService) {
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
            value = "delete",
            results = {
                    @Result(name = "success", type = "redirectAction", location = "index.html"),
                    @Result(name = "input", type="tiles", location = "provinceDeleteForm"),
                    @Result(name = "error", type="tiles", location = "provinceDeleteError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(province != null) {
            Province dbProvince = provinceDaoLocal.findByUuid(province.getUuid());
            dbProvince.setModified(new Date());
            dbProvince.setModifiedBy(remoteUser);
            dbProvince.setModifiedAddress(remoteAddress);
            provinceDaoLocal.merge(dbProvince);
            provinceDaoLocal.delete(dbProvince);
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }// Ende execute()

}// Ende class
