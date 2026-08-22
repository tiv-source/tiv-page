package de.tivsource.page.admin.actions.system.salutation;

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
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="salutationDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/salutation/add_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -8665382623990061364L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @Autowired
    @Qualifier("salutationDaoLocal")
    private SalutationDaoLocal salutationDaoLocal;
    
    private Salutation salutation;

    @Autowired
    public DeleteAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=1)
	public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "salutationDeleteError"),
        				@Result(name = "error", type="tiles", location = "salutationDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(salutation != null) {
    		Salutation dbSalutation = salutationDaoLocal.findByUuid(salutation.getUuid());
            
    		dbSalutation.setModifiedAddress(remoteAddress);
            dbSalutation.setModified(new Date());
            dbSalutation.setModifiedBy(remoteUser);

            salutationDaoLocal.merge(dbSalutation);
    		salutationDaoLocal.delete(dbSalutation);
            return SUCCESS;
    	}
  		return ERROR;
    }// Ende execute()
	
}// Ende class
