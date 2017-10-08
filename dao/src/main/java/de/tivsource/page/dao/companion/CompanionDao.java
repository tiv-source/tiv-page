/**
 * 
 */
package de.tivsource.page.dao.companion;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.companion.Companion;
import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class CompanionDao implements CompanionDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CompanionDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#save(de.tivsource.page.entity.companion.Companion)
     */
    @Override
    public void save(Companion companion) {
        LOGGER.info("save(Companion companion) aufgerufen");
        entityManager.persist(companion);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#merge(de.tivsource.page.entity.companion.Companion)
     */
    @Override
    public void merge(Companion companion) {
        LOGGER.info("merge(Companion companion) aufgerufen");
        entityManager.merge(companion);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#delete(de.tivsource.page.entity.companion.Companion)
     */
    @Override
    public void delete(Companion companion) {
        entityManager.remove(entityManager.find(Companion.class, companion.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#isCompanionUuid(java.lang.String)
     */
    @Override
    public Boolean isCompanionUuid(String uuid) {
        Query query = entityManager.createQuery("select c from Companion c where c.uuid = ?1 and c.visible = 'Y' order by c.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Companion findByUuid(String uuid) {
        return entityManager.find(Companion.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Companion> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Companion c ORDER by c.name");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Companion> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT c FROM Companion c ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Companion> findAllVisible(Integer start, Integer max) {
        String queryString = "SELECT c FROM Companion c WHERE c.visible = 'Y' ORDER BY c.name asc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Companion> findAllVisible(Integer start, Integer max, CompanionGroup companionGroup) {
        String queryString = "SELECT c FROM Companion c WHERE c.visible = 'Y' and c.group = ?1 ORDER BY c.name asc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("1", companionGroup);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(c) from Companion c");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(c) from Companion c WHERE c.visible = 'Y'");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer countAllVisible(CompanionGroup companionGroup) {
        Query query = entityManager.createQuery("Select Count(c) from Companion c WHERE c.visible = 'Y' and c.group = ?1");
        query.setParameter("1", companionGroup);
        return Integer.parseInt(query.getSingleResult().toString());
    }



}// Ende class
