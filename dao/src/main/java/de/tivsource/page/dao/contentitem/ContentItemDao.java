/**
 * 
 */
package de.tivsource.page.dao.contentitem;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class ContentItemDao implements ContentItemDaoLocal {

	/*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ContentItemDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contentitem.ContentItemDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public ContentItem findByUuid(String uuid) {
		LOGGER.info("findByUuid(String uuid) aufgerufen");
		return entityManager.find(ContentItem.class, uuid);
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.contentitem.ContentItemDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ContentItem> findAllVisible(String uuid, Integer start, Integer max) {
        LOGGER.info("findAll(Integer start, Integer max) aufgerufen");
        Query query = entityManager.createQuery("from ContentItem ci where ci.uuid != :uuid and ci.visible = :visible");
        query.setParameter("uuid", uuid);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        return query.getResultList();
    }

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contentitem.ContentItemDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentItem> findAllUnassigned(Integer start, Integer max) {
		LOGGER.info("findAllUnassigned(Integer start, Integer max) aufgerufen");
        Query query = entityManager.createQuery("from ContentItem ci where ci.uuid not in (select ce.contentItem.uuid from ContentEntry ce)");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.contentitem.ContentItemDaoLocal#countAll()
     */
    @Override
    public Integer countAllVisible(String uuid) {
        Query query = entityManager.createQuery("Select Count(ci) from ContentItem ci where ci.uuid != :uuid and ci.visible = :visible");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contentitem.ContentItemDaoLocal#countAll()
	 */
	@Override
	public Integer countAllUnassigned() {
        Query query = entityManager.createQuery("Select Count(ci) from ContentItem ci where ci.uuid not in (select uuid from ContentEntry ce)");
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class
