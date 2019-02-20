package de.tivsource.page.admin.actions.others.contententry;

import java.util.Date;
import java.util.List;
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
  @TilesDefinition(name="contentEntryAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/add_form.jsp")
  }),
  @TilesDefinition(name="contentEntryAddError",  extend = "adminTemplate", putAttributes = {
	@TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
	@TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
	@TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements Preparable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 8805512413826287971L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

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
    	contentItems = contentItemDaoLocal.findAll(0, contentItemDaoLocal.countAll());
    }
    
	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "contentEntryAddForm"),
        				@Result(name = "error", type="tiles", location = "contentEntryAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(contentEntry != null) {

    	    contentEntry.setUuid(UUID.randomUUID().toString());
    	    contentEntry.setTechnical("CI_" + contentEntry.getContentItem().getTechnical());
    	    contentEntry.setModified(new Date());
    	    contentEntry.setCreated(new Date());
    	    contentEntry.setModifiedBy(remoteUser);
    	    contentEntry.setModifiedAddress(remoteAddress);

    	    contentEntry.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    contentEntry.getDescriptionMap().get(Language.DE).setNamingItem(contentEntry);
    	    contentEntry.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    contentEntry.getDescriptionMap().get(Language.DE).setName(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getName());
    	    contentEntry.getDescriptionMap().get(Language.DE).setDescription(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getDescription());
    	    contentEntry.getDescriptionMap().get(Language.DE).setKeywords(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getKeywords());

            contentEntry.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            contentEntry.getDescriptionMap().get(Language.EN).setNamingItem(contentEntry);
            contentEntry.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    contentEntry.getDescriptionMap().get(Language.EN).setName(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getName());
    	    contentEntry.getDescriptionMap().get(Language.EN).setDescription(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getDescription());
    	    contentEntry.getDescriptionMap().get(Language.EN).setKeywords(contentEntry.getContentItem().getDescriptionMap().get(Language.DE).getKeywords());
           

    		contentEntryDaoLocal.merge(contentEntry);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

}// Ende class
