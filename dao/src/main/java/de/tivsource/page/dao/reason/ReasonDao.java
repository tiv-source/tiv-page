/**
 * 
 */
package de.tivsource.page.dao.reason;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.request.Reason;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class ReasonDao implements ReasonDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ReasonDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#merge(de.tivsource.page.entity.request.Reason)
     */
    @Override
    public void merge(Reason reason) {
        LOGGER.info("merge(Reason reason) aufgerufen");
        entityManager.merge(reason);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#delete(de.tivsource.page.entity.request.Reason)
     */
    @Override
    public void delete(Reason reason) {
        entityManager.remove(entityManager.find(Reason.class, reason.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#isReasonUuid(java.lang.String)
     */
    @Override
    public Boolean isReasonUuid(String uuid) {
        Query query = entityManager.createQuery("select r from Reason r where r.uuid = :uuid and r.visible = :visible order by r.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Reason findByUuid(String uuid) {
        return entityManager.find(Reason.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reason> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Reason r");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reason> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT r FROM Reason r JOIN r.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reason> findAllVisible(Integer start, Integer max) {
        String queryString = "SELECT r FROM Reason r WHERE r.visible = :visible ORDER BY r.orderNumber, r.created desc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Reason r");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reason.ReasonDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(r) from Reason r WHERE r.visible = :visible");
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
