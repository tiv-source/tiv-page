/**
 * 
 */
package de.tivsource.page.common.menuentry;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "ContentEntry")
public class ContentEntry extends MenuEntry {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contentItem_uuid")
	private ContentItem contentItem;

    /**
	 * @return the contentItem
	 */
	public ContentItem getContentItem() {
		return contentItem;
	}

	/**
	 * @param contentItem the contentItem to set
	 */
	public void setContentItem(ContentItem contentItem) {
		this.contentItem = contentItem;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.common.menuentry.MenuEntry#getUrl()
	 */
	@Override
	public String getUrl() {
		return contentItem.getUrl();
	}

}// Ende class
