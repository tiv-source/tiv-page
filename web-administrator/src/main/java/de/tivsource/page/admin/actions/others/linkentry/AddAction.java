package de.tivsource.page.admin.actions.others.linkentry;

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
import de.tivsource.page.common.menuentry.LinkEntry;
import de.tivsource.page.dao.linkentry.LinkEntryDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="linkEntryAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/linkentry/add_form.jsp")
  }),
  @TilesDefinition(name="linkEntryAddError",  extend = "adminTemplate", putAttributes = {
	@TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
	@TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
	@TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/linkentry/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1043526042620901395L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="LinkEntryDao")
    private LinkEntryDaoLocal linkEntryDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;
    
    private LinkEntry linkEntry;

    public LinkEntry getLinkEntry() {
        return linkEntry;
    }

    public void setLinkEntry(LinkEntry linkEntry) {
        this.linkEntry = linkEntry;
    }
    
	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "linkEntryAddForm"),
        				@Result(name = "error", type="tiles", location = "linkEntryAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(linkEntry != null) {
    	    linkEntry.setUuid(UUID.randomUUID().toString());
    	    linkEntry.setTechnical("LE_" + linkEntry.getUuid());
    	    linkEntry.setModified(new Date());
    	    linkEntry.setCreated(new Date());
    	    linkEntry.setModifiedBy(remoteUser);
    	    linkEntry.setModifiedAddress(remoteAddress);

    	    linkEntry.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    linkEntry.getDescriptionMap().get(Language.DE).setNamingItem(linkEntry);
    	    linkEntry.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    linkEntry.getDescriptionMap().get(Language.DE).setName(linkEntry.getDescriptionMap().get(Language.DE).getName());
    	    linkEntry.getDescriptionMap().get(Language.DE).setDescription(linkEntry.getDescriptionMap().get(Language.DE).getDescription());
    	    linkEntry.getDescriptionMap().get(Language.DE).setKeywords(linkEntry.getDescriptionMap().get(Language.DE).getKeywords());

            linkEntry.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            linkEntry.getDescriptionMap().get(Language.EN).setNamingItem(linkEntry);
            linkEntry.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    linkEntry.getDescriptionMap().get(Language.EN).setName(linkEntry.getDescriptionMap().get(Language.DE).getName());
    	    linkEntry.getDescriptionMap().get(Language.EN).setDescription(linkEntry.getDescriptionMap().get(Language.DE).getDescription());
    	    linkEntry.getDescriptionMap().get(Language.EN).setKeywords(linkEntry.getDescriptionMap().get(Language.DE).getKeywords());

    		linkEntryDaoLocal.merge(linkEntry);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

}// Ende class
