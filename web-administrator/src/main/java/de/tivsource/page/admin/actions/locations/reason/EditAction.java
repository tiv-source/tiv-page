package de.tivsource.page.admin.actions.locations.reason;

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
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reason.ReasonDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.request.Reason;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="reasonEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reason/edit_form.jsp")
  }),
  @TilesDefinition(name="reasonEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reason/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 3868709731406017338L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="ReasonDao")
    private ReasonDaoLocal reasonDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Reason reason;

    private String lang = "DE";

    /**
     * @return the reason
     */
    @StrutsParameter(depth=3)
    public Reason getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "reasonEditForm"),
        				@Result(name = "error",   type = "tiles", location = "reasonEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(reason != null) {
    		LOGGER.info("UUID der FeedbackOption: " + reason.getUuid());
    		Reason dbReason = reasonDaoLocal.findByUuid(reason.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                reason.getDescriptionMap().put(Language.DE, dbReason.getDescriptionObject(Language.DE));
                String noLineBreaks = reason.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbReason.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbReason.getDescriptionMap().get(Language.EN).setKeywords(reason.getDescriptionObject(Language.EN).getKeywords());
                dbReason.getDescriptionMap().get(Language.EN).setName(reason.getDescriptionObject(Language.EN).getName());
            } else {
        	    String noLineBreaks = reason.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
        	    dbReason.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbReason.getDescriptionMap().get(Language.DE).setKeywords(reason.getDescriptionObject(Language.DE).getKeywords());;
                dbReason.getDescriptionMap().get(Language.DE).setName(reason.getDescriptionObject(Language.DE).getName());
            }

            dbReason.setOrderNumber(reason.getOrderNumber());
            dbReason.setModifiedAddress(remoteAddress);
    		dbReason.setModified(new Date());
    		dbReason.setModifiedBy(remoteUser);
    		dbReason.setVisible(reason.getVisible());
    		dbReason.setTechnical(reason.getTechnical());

    		reasonDaoLocal.merge(dbReason);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
