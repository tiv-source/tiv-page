package de.tivsource.page.admin.actions.others.contententry;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import com.opensymphony.xwork2.Preparable;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.menuentry.ContentEntry;
import de.tivsource.page.dao.contententry.ContentEntryDaoLocal;
import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="contentEntryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/edit_form.jsp")
  }),
  @TilesDefinition(name="contentEntryEditError", extend = "adminTemplate", putAttributes = {
	@TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
	@TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
	@TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction implements Preparable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 413937413466123241L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="ContentEntryDao")
    private ContentEntryDaoLocal contentEntryDaoLocal;

	@InjectEJB(name="ContentItemDao")
    private ContentItemDaoLocal contentItemDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private ContentEntry contentEntry;

    private List<ContentItem> contentItems;

    public ContentEntry getContentEntry() {
        return contentEntry;
    }

	public void setContentEntry(ContentEntry contentEntry) {
        this.contentEntry = contentEntry;
    }

    public List<ContentItem> getContentItems() {
    	return contentItems;
    }

	@Override
    public void prepare() {
    	contentItems = contentItemDaoLocal.findAllUnassigned(0, contentItemDaoLocal.countAllUnassigned());
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "contentEntryEditForm"),
        				@Result(name = "error",   type = "tiles", location = "contentEntryEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(contentEntry != null) {
    		LOGGER.info(contentEntry.getTechnical());
    		ContentEntry dbContentEntry = contentEntryDaoLocal.findByUuid(contentEntry.getUuid());
    		contentItems.add(dbContentEntry.getContentItem());
    		dbContentEntry.setContentItem(contentEntry.getContentItem());
    		dbContentEntry.setTechnical("CI_" + contentEntry.getContentItem().getTechnical());

    		dbContentEntry.getDescriptionMap().get(Language.DE).setName(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getName());
    		dbContentEntry.getDescriptionMap().get(Language.DE).setDescription(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getDescription());
    		dbContentEntry.getDescriptionMap().get(Language.DE).setKeywords(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getKeywords());
            
    		dbContentEntry.getDescriptionMap().get(Language.EN).setName(contentEntry.getContentItem().getDescriptionMap().get(Language.EN).getName());
    		dbContentEntry.getDescriptionMap().get(Language.EN).setDescription(contentEntry.getContentItem().getDescriptionMap().get(Language.EN).getDescription());
    		dbContentEntry.getDescriptionMap().get(Language.EN).setKeywords(contentEntry.getContentItem().getDescriptionMap().get(Language.EN).getKeywords());

    		dbContentEntry.setBottomNavigation(contentEntry.getBottomNavigation());
    		dbContentEntry.setBottomNavigationOrder(contentEntry.getBottomNavigationOrder());
    		dbContentEntry.setNavigation(contentEntry.getNavigation());
    		dbContentEntry.setNavigationOrder(contentEntry.getNavigationOrder());
    		dbContentEntry.setResponsiveNavigation(contentEntry.getResponsiveNavigation());
    		dbContentEntry.setResponsiveNavigationOrder(contentEntry.getResponsiveNavigationOrder());
    		dbContentEntry.setTopNavigation(contentEntry.getTopNavigation());
    		dbContentEntry.setTopNavigationOrder(contentEntry.getTopNavigationOrder());
    		
    		dbContentEntry.setModified(new Date());
    		dbContentEntry.setVisible(contentEntry.getVisible());
    		dbContentEntry.setModifiedBy(remoteUser);
    		dbContentEntry.setModifiedAddress(remoteAddress);
    		contentEntryDaoLocal.merge(dbContentEntry);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
