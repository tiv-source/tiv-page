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

    private Integer topNavigationOrder = 100;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean navigation;

    private Integer navigationOrder = 100;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean bottomNavigation;

    private Integer bottomNavigationOrder = 100;

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

    public Integer getTopNavigationOrder() {
        return topNavigationOrder;
    }

    public void setTopNavigationOrder(Integer topNavigationOrder) {
        this.topNavigationOrder = topNavigationOrder;
    }

    public Boolean getNavigation() {
        return navigation;
    }

    public void setNavigation(Boolean navigation) {
        this.navigation = navigation;
    }

    public Integer getNavigationOrder() {
        return navigationOrder;
    }

    public void setNavigationOrder(Integer navigationOrder) {
        this.navigationOrder = navigationOrder;
    }

    public Boolean getBottomNavigation() {
        return bottomNavigation;
    }

    public void setBottomNavigation(Boolean bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }

    public Integer getBottomNavigationOrder() {
        return bottomNavigationOrder;
    }

    public void setBottomNavigationOrder(Integer bottomNavigationOrder) {
        this.bottomNavigationOrder = bottomNavigationOrder;
    }

}// Ende class
