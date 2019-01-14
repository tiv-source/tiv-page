/**
 * 
 */
package de.tivsource.page.common.menuentry;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 *
 */
@Entity
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
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("/");
	// TODO: Attribute technical zu NamingItem	stringBuffer.append(contentItem.getTechnical());
		stringBuffer.append("/");
		stringBuffer.append("index.html");
		return stringBuffer.toString();
	}

}// Ende class
