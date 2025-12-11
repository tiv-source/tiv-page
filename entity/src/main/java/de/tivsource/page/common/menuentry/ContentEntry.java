/**
 * 
 */
package de.tivsource.page.common.menuentry;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = ContentEntry.TABLE)
@Table(appliesTo = ContentEntry.TABLE, fetch = FetchMode.SELECT)
public class ContentEntry extends MenuEntry {

    public static final String TABLE = "ContentEntry";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(table = TABLE, name = "contentItem_uuid")
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
