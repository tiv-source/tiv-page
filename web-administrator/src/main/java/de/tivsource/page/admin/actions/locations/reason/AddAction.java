package de.tivsource.page.admin.actions.locations.reason;

import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.reason.ReasonDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.request.Reason;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="reasonAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reason/add_form.jsp")
  }),
  @TilesDefinition(name="reasonAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reason/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 6475518811127973831L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="ReasonDao")
    private ReasonDaoLocal reasonDaoLocal;

    private Reason reason;

	/**
     * @return the reason
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "reasonAddForm"),
        				@Result(name = "error", type="tiles", location = "reasonAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(reason != null) {
    	    reason.setUuid(UUID.randomUUID().toString());
    	    reason.setModified(new Date());
    	    reason.setCreated(new Date());
    	    reason.setModifiedBy(remoteUser);
    	    reason.setModifiedAddress(remoteAddress);

    	    reason.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    reason.getDescriptionMap().get(Language.DE).setNamingItem(reason);
    	    reason.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = reason.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    reason.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
    	    // Erstelle englische Beschreibung.
    	    Description descriptionEN = new Description();
    	    descriptionEN.setUuid(UUID.randomUUID().toString());
    	    descriptionEN.setNamingItem(reason);
    	    descriptionEN.setLanguage(Language.EN);
    	    descriptionEN.setDescription(reason.getDescriptionMap().get(Language.DE).getDescription());
    	    descriptionEN.setKeywords(reason.getDescriptionMap().get(Language.DE).getKeywords());
    	    descriptionEN.setName(reason.getDescriptionMap().get(Language.DE).getName());
            reason.getDescriptionMap().put(Language.EN, descriptionEN);

    		reasonDaoLocal.merge(reason);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

}// Ende class
