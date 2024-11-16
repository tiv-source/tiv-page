/**
 * 
 */
package de.tivsource.page.dao.menuentry;

import java.util.List;

import de.tivsource.page.common.menuentry.MenuEntry;
import jakarta.ejb.Local;

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
