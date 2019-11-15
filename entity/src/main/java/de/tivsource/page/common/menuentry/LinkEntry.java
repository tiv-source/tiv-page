/**
 * 
 */
package de.tivsource.page.common.menuentry;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class LinkEntry extends MenuEntry {

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
