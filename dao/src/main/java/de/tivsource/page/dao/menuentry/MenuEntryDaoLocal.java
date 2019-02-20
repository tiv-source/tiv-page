/**
 * 
 */
package de.tivsource.page.dao.menuentry;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.common.menuentry.MenuEntry;

/**
 * @author Marc Michele
 *
 */
@Local
public interface MenuEntryDaoLocal {

    public List<MenuEntry> findAllTopNavigation();

    public List<MenuEntry> findAllNavigation();

    public List<MenuEntry> findAllBottomNavigation();

    public List<MenuEntry> findAllResponsiveNavigation();

}// Ende interface
