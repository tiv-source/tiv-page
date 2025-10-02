/**
 * 
 */
package de.tivsource.page.dao.menuentry;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.menuentry.MenuEntry;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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
                "from MenuEntry me where me.topNavigation = :topNavigation "
                + "and me.visible = :visible "
                + "order by me.topNavigationOrder asc"
                );
        query.setParameter("topNavigation", true);
        query.setParameter("visible", true);
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
                "from MenuEntry me where me.navigation = :navigation "
                + "and me.visible = :visible "
                + "order by me.navigationOrder asc"
                );
        query.setParameter("navigation", true);
        query.setParameter("visible", true);
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
                "from MenuEntry me where me.bottomNavigation = :bottomNavigation "
                + "and me.visible = :visible "
                + "order by me.bottomNavigationOrder asc"
                );
        query.setParameter("bottomNavigation", true);
        query.setParameter("visible", true);
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
                "from MenuEntry me where me.responsiveNavigation = :responsiveNavigation "
                + "and me.visible = :visible "
                + "order by me.responsiveNavigationOrder asc"
                );
        query.setParameter("responsiveNavigation", true);
        query.setParameter("visible", true);
        return query.getResultList();
	}

}// Ende class
