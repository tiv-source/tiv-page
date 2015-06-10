/**
 * 
 */
package de.tivsource.page.entity.page;

import javax.persistence.Basic;
import javax.persistence.Column;
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

	@Column(unique = true)
	private String technical;

	@Basic
	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean special;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean topNavigation;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean navigation;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean bottomNavigation;

	public String getTechnical() {
		return technical;
	}

	public void setTechnical(String technical) {
		this.technical = technical;
	}

	public Boolean getSpecial() {
		return special;
	}

	public void setSpecial(Boolean special) {
		this.special = special;
	}

    public Boolean getTopNavigation() {
        return topNavigation;
    }

    public void setTopNavigation(Boolean topNavigation) {
        this.topNavigation = topNavigation;
    }

    public Boolean getNavigation() {
        return navigation;
    }

    public void setNavigation(Boolean navigation) {
        this.navigation = navigation;
    }

    public Boolean getBottomNavigation() {
        return bottomNavigation;
    }

    public void setBottomNavigation(Boolean bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }

}// Ende class
