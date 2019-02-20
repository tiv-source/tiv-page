package de.tivsource.page.admin.actions.others.linkentry;

import java.util.Date;

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
  @TilesDefinition(name="linkEntryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/linkentry/edit_form.jsp")
  }),
  @TilesDefinition(name="linkEntryEditError", extend = "adminTemplate", putAttributes = {
	@TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
	@TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
	@TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/linkentry/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2459909610299610689L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

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
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "linkEntryEditForm"),
        				@Result(name = "error",   type = "tiles", location = "linkEntryEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(linkEntry != null) {
    		LOGGER.info(linkEntry.getTechnical());
    		LinkEntry dbLinkEntry = linkEntryDaoLocal.findByUuid(linkEntry.getUuid());
    		dbLinkEntry.setTechnical("LE_" + linkEntry.getUuid());

    		dbLinkEntry.getDescriptionMap().get(Language.DE).setName(linkEntry.getDescriptionMap().get(Language.DE).getName());
    		dbLinkEntry.getDescriptionMap().get(Language.DE).setDescription(linkEntry.getDescriptionMap().get(Language.DE).getDescription());
    		dbLinkEntry.getDescriptionMap().get(Language.DE).setKeywords(linkEntry.getDescriptionMap().get(Language.DE).getKeywords());
            
    		dbLinkEntry.getDescriptionMap().get(Language.EN).setName(linkEntry.getDescriptionMap().get(Language.EN).getName());
    		dbLinkEntry.getDescriptionMap().get(Language.EN).setDescription(linkEntry.getDescriptionMap().get(Language.EN).getDescription());
    		dbLinkEntry.getDescriptionMap().get(Language.EN).setKeywords(linkEntry.getDescriptionMap().get(Language.EN).getKeywords());

    		dbLinkEntry.setBottomNavigation(linkEntry.getBottomNavigation());
    		dbLinkEntry.setBottomNavigationOrder(linkEntry.getBottomNavigationOrder());
    		dbLinkEntry.setNavigation(linkEntry.getNavigation());
    		dbLinkEntry.setNavigationOrder(linkEntry.getNavigationOrder());
    		dbLinkEntry.setResponsiveNavigation(linkEntry.getResponsiveNavigation());
    		dbLinkEntry.setResponsiveNavigationOrder(linkEntry.getResponsiveNavigationOrder());
    		dbLinkEntry.setTopNavigation(linkEntry.getTopNavigation());
    		dbLinkEntry.setTopNavigationOrder(linkEntry.getTopNavigationOrder());

    		dbLinkEntry.setModified(new Date());
    		dbLinkEntry.setVisible(linkEntry.getVisible());
    		dbLinkEntry.setUrl(linkEntry.getUrl());
    		dbLinkEntry.setModifiedBy(remoteUser);
    		dbLinkEntry.setModifiedAddress(remoteAddress);
    		linkEntryDaoLocal.merge(dbLinkEntry);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
