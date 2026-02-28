package de.tivsource.page.admin.actions.system.salutation;

import java.util.Date;
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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="salutationAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/salutation/add_form.jsp")
  }),
  @TilesDefinition(name="salutationAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/salutation/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -5931989554244640623L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;
    
    private Salutation salutation;

    @StrutsParameter(depth=3)
	public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "salutationAddForm"),
        				@Result(name = "error", type="tiles", location = "salutationAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(salutation != null) {
    	    salutation.setUuid(UUID.randomUUID().toString());
    	    salutation.setModified(new Date());
    	    salutation.setCreated(new Date());
    	    salutation.setModifiedAddress(remoteAddress);
    	    salutation.setModifiedBy(remoteUser);

    	    salutation.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    salutation.getDescriptionMap().get(Language.DE).setSalutation(salutation);;
    	    salutation.getDescriptionMap().get(Language.DE).setLocale(Language.DE);
    	    String noLineBreaks = salutation.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    salutation.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    salutation.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    salutation.getDescriptionMap().get(Language.EN).setSalutation(salutation);
    	    salutation.getDescriptionMap().get(Language.EN).setLocale(Language.EN);
    	    
    	    salutationDaoLocal.merge(salutation);

            return SUCCESS;
    	}
   		return ERROR;
    }// Ende execute()



}// Ende class
