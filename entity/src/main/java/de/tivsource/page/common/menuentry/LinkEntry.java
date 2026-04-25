/**
 * 
 */
package de.tivsource.page.common.menuentry;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = LinkEntry.TABLE)
public class LinkEntry extends MenuEntry {

    public static final String TABLE = "LinkEntry";

    @Column(table = TABLE)
	private String url;

	/* (non-Javadoc)
	 * @see de.tivsource.portal.common.menuentry.MenuEntry#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}// Ende class
