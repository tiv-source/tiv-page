/**
 * 
 */
package de.tivsource.page.common.menuentry;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.namingitem.NamingItem;
import jakarta.persistence.Basic;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MenuEntry extends NamingItem {

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean topNavigation;

    private Integer topNavigationOrder = 100;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean navigation;

    private Integer navigationOrder = 100;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean bottomNavigation;

    private Integer bottomNavigationOrder = 100;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean responsiveNavigation;

    private Integer responsiveNavigationOrder = 100;

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

	public Boolean getResponsiveNavigation() {
		return responsiveNavigation;
	}

	public void setResponsiveNavigation(Boolean responsiveNavigation) {
		this.responsiveNavigation = responsiveNavigation;
	}

	public Integer getResponsiveNavigationOrder() {
		return responsiveNavigationOrder;
	}

	public void setResponsiveNavigationOrder(Integer responsiveNavigationOrder) {
		this.responsiveNavigationOrder = responsiveNavigationOrder;
	}

	public abstract String getUrl();

}// Ende class
