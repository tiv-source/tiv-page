/**
 * 
 */
package de.tivsource.page.entity.news;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.enumeration.IconType;

/**
 * @author Marc Michele
 * 
 */
@Entity
public class News extends ContentItem {

	private IconType icon;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date releaseDate;

	public News() {
		super();
	}

	public IconType getIcon() {
		return icon;
	}

	public void setIcon(IconType icon) {
		this.icon = icon;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}// Ende class

