/**
 * 
 */
package de.tivsource.page.dao.contententry;

import java.util.Date;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.menuentry.ContentEntry;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class ContentEntryDao implements ContentEntryDaoLocal {

	/*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ContentEntryDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#save(de.tivsource.portal.common.menuentry.ContentEntry)
	 */
	@Override
	public void save(ContentEntry contentEntry) {
        LOGGER.info("save(ContentEntry contentEntry) aufgerufen");
        contentEntry.setCreated(new Date());
        entityManager.persist(contentEntry);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#merge(de.tivsource.portal.common.menuentry.ContentEntry)
	 */
	@Override
	public void merge(ContentEntry contentEntry) {
        LOGGER.info("merge(ContentEntry contentEntry) aufgerufen");
        LOGGER.trace("Name des zu speichernden ContentEntry Objektes: " + contentEntry.getName("de"));
        LOGGER.trace("Name des abhängigen ContentItem Objektes: " + contentEntry.getContentItem().getName("de"));
        entityManager.merge(contentEntry);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#delete(de.tivsource.portal.common.menuentry.ContentEntry)
	 */
	@Override
	public void delete(ContentEntry contentEntry) {
		entityManager.remove(entityManager.find(ContentEntry.class, contentEntry.getUuid()));
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#isContentEntryUrl(java.lang.String)
	 */
	@Override
	public Boolean isContentEntryUrl(String urlName) {
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.technical = :urlName and ce.visible = :visible order by ce.uuid asc");
        query.setParameter("urlName", urlName);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#findByTechnical(java.lang.String)
	 */
	@Override
	public ContentEntry findByTechnical(String technical) {
        LOGGER.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.technical = :technical");
        query.setParameter("technical", technical);
        return (ContentEntry)query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public ContentEntry findByUuid(String uuid) {
		return entityManager.find(ContentEntry.class, uuid);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentEntry> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from ContentEntry ce");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentEntry> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT ce FROM ContentEntry ce JOIN ce.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.contententry.ContentEntryDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(ce) from ContentEntry ce");
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class
