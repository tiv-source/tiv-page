/**
 * 
 */
package de.tivsource.page.dao.menuentry;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.menuentry.MenuEntry;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class MenuEntryDao implements MenuEntryDaoLocal {
    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MenuEntryDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.menuentry.MenuDaoLocal#findAllTopNavigation()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntry> findAllTopNavigation() {
		LOGGER.info("findAllTopNavigation() aufgerufen");
        Query query = entityManager.createQuery(
                "from MenuEntry me where me.topNavigation = 'Y' "
                + "and me.visible = 'Y' "
                + "order by me.topNavigationOrder asc"
                );
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.menuentry.MenuDaoLocal#findAllNavigation()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntry> findAllNavigation() {
		LOGGER.info("findAllNavigation() aufgerufen");
        Query query = entityManager.createQuery(
                "from MenuEntry me where me.navigation = 'Y' "
                + "and me.visible = 'Y' "
                + "order by me.navigationOrder asc"
                );
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.menuentry.MenuDaoLocal#findAllBottomNavigation()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntry> findAllBottomNavigation() {
		LOGGER.info("findAllBottomNavigation() aufgerufen");
        Query query = entityManager.createQuery(
                "from MenuEntry me where me.bottomNavigation = 'Y' "
                + "and me.visible = 'Y' "
                + "order by me.bottomNavigationOrder asc"
                );
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.menuentry.MenuDaoLocal#findAllResponsiveNavigation()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntry> findAllResponsiveNavigation() {
		LOGGER.info("findAllResponsiveNavigation() aufgerufen");
        Query query = entityManager.createQuery(
                "from MenuEntry me where me.responsiveNavigation = 'Y' "
                + "and me.visible = 'Y' "
                + "order by me.responsiveNavigationOrder asc"
                );
        return query.getResultList();
	}

}// Ende class
