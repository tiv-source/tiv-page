/**
 * 
 */
package de.tivsource.page.dao.linkentry;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.menuentry.LinkEntry;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class LinkEntryDao implements LinkEntryDaoLocal {

	/*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LinkEntryDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#save(de.tivsource.portal.common.menuentry.LinkEntry)
	 */
	@Override
	public void save(LinkEntry linkEntry) {
        LOGGER.info("save(LinkEntry linkEntry) aufgerufen");
        linkEntry.setCreated(new Date());
        entityManager.persist(linkEntry);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#merge(de.tivsource.portal.common.menuentry.LinkEntry)
	 */
	@Override
	public void merge(LinkEntry linkEntry) {
        LOGGER.info("merge(LinkEntry linkEntry) aufgerufen");
        entityManager.merge(linkEntry);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#delete(de.tivsource.portal.common.menuentry.LinkEntry)
	 */
	@Override
	public void delete(LinkEntry linkEntry) {
		entityManager.remove(entityManager.find(LinkEntry.class, linkEntry.getUuid()));
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#isLinkEntryUrl(java.lang.String)
	 */
	@Override
	public Boolean isLinkEntryUrl(String urlName) {
        Query query = entityManager.createQuery("select le from LinkEntry le where le.technical = :urlName and le.visible = 'Y' order by le.uuid asc");
        query.setParameter("urlName", urlName);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#findByTechnical(java.lang.String)
	 */
	@Override
	public LinkEntry findByTechnical(String technical) {
        LOGGER.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select le from LinkEntry le where le.technical = :technical");
        query.setParameter("technical", technical);
        return (LinkEntry)query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public LinkEntry findByUuid(String uuid) {
		return entityManager.find(LinkEntry.class, uuid);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LinkEntry> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from LinkEntry le");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LinkEntry> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT le FROM LinkEntry le JOIN le.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.dao.linkentry.LinkEntryDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(le) from LinkEntry le");
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class
