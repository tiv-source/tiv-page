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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="salutationEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/salutation/edit_form.jsp")
  }),
  @TilesDefinition(name="salutationEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/salutation/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 8863179398893375277L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;
    
    private Salutation salutation;

    private String lang = "DE";

    @Autowired
    public EditAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=3)
    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "salutationEditForm"),
        				@Result(name = "error",   type = "tiles", location = "salutationEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(salutation != null) {
    		LOGGER.info("Salutation UUID: " + salutation.getUuid());
    		Salutation dbSalutation = salutationDaoLocal.findByUuid(salutation.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                salutation.getDescriptionMap().put(Language.DE, dbSalutation.getDescriptionObject(Language.DE));
                String noLineBreaks = salutation.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbSalutation.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbSalutation.getDescriptionMap().get(Language.EN).setKeywords(salutation.getKeywords(Language.EN));
                dbSalutation.getDescriptionMap().get(Language.EN).setName(salutation.getName(Language.EN));
                dbSalutation.getDescriptionMap().get(Language.EN).setLongSalutation(salutation.getLongSalutation(Language.EN));
            } else {
                String noLineBreaks = salutation.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbSalutation.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbSalutation.getDescriptionMap().get(Language.DE).setKeywords(salutation.getKeywords(Language.DE));;
                dbSalutation.getDescriptionMap().get(Language.DE).setName(salutation.getName(Language.DE));
                dbSalutation.getDescriptionMap().get(Language.DE).setLongSalutation(salutation.getLongSalutation(Language.DE));
            }

            dbSalutation.setVisible(salutation.getVisible());
    		dbSalutation.setModifiedAddress(remoteAddress);
    		dbSalutation.setModified(new Date());
    		dbSalutation.setModifiedBy(remoteUser);

    		salutationDaoLocal.merge(dbSalutation);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
