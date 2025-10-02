/**
 * 
 */
package de.tivsource.page.dao.request;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.request.Request;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class RequestDao implements RequestDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RequestDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.request.RequestDaoLocal#merge(de.tivsource.page.entity.request.Request)
     */
    @Override
    public void merge(Request request) {
        LOGGER.info("merge(Request request) aufgerufen");
        entityManager.merge(request);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.request.RequestDaoLocal#delete(de.tivsource.page.entity.request.Request)
     */
    @Override
    public void delete(Request request) {
        entityManager.remove(entityManager.find(Request.class, request.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.request.RequestDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Request findByUuid(String uuid) {
        return entityManager.find(Request.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.request.RequestDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Request> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Request r");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.request.RequestDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Request> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT r FROM Request r ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.request.RequestDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Request r");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
