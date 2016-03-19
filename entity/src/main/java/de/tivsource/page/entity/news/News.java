/**
 * 
 */
package de.tivsource.page.entity.news;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 * 
 */
@Entity
public class News extends ContentItem {

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date releaseDate;

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}// Ende class

