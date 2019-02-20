/**
 * 
 */
package de.tivsource.page.entity.page;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.hibernate.search.annotations.Indexed;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 * 
 */
@Indexed
@Entity
public class Page extends ContentItem {

	@Basic
	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean special;

	public Boolean getSpecial() {
		return special;
	}

	public void setSpecial(Boolean special) {
		this.special = special;
	}

}// Ende class
