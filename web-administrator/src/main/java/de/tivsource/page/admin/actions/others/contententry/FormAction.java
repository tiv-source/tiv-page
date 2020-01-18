package de.tivsource.page.admin.actions.others.contententry;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.menuentry.ContentEntry;
import de.tivsource.page.dao.contententry.ContentEntryDaoLocal;
import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.contentitem.ContentItem;

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
  @TilesDefinition(name="contentEntryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/edit_form.jsp")
  }),
  @TilesDefinition(name="contentEntryDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contententry/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5131003689523423888L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

	@InjectEJB(name="ContentEntryDao")
    private ContentEntryDaoLocal contentEntryDaoLocal;

	@InjectEJB(name="ContentItemDao")
    private ContentItemDaoLocal contentItemDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

	private ContentEntry contentEntry;

	private String uncheckContentEntry;

	private List<ContentItem> contentItems;
	
	public ContentEntry getContentEntry() {
        return contentEntry;
    }

	public void setContentEntry(String uncheckContentEntry) {
        this.uncheckContentEntry = uncheckContentEntry;
    }

    @Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "contentEntryEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "contentEntryAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "contentEntryDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	contentItems = contentItemDaoLocal.findAllUnassigned(0, contentItemDaoLocal.countAllUnassigned());

    	this.loadPageParameter();
    	
    	return SUCCESS;
    }// Ende execute()

    public List<ContentItem> getContentItems() {
    	return contentItems;
    }

	private void loadPageParameter() {

		if( uncheckContentEntry != null && uncheckContentEntry != "" && uncheckContentEntry.length() > 0) {
			contentEntry = contentEntryDaoLocal.findByUuid(uncheckContentEntry);
			// Füge das aktuelle ContentItem hinzu damit es auch in der Liste auftaucht.
			contentItems.add(contentEntry.getContentItem());
		} else {
			contentEntry = new ContentEntry();
		}

	}// Ende loadPageParameter()

}// Ende class
