/**
 * 
 */
package de.tivsource.page.dao.feedback;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.feedback.FeedbackOption;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class FeedbackOptionDao implements FeedbackOptionDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FeedbackOptionDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#merge(de.tivsource.page.entity.feedback.FeedbackOption)
     */
    @Override
    public void merge(FeedbackOption feedbackOption) {
        LOGGER.info("merge(FeedbackOption feedbackOption) aufgerufen");
        entityManager.merge(feedbackOption);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#delete(de.tivsource.page.entity.feedback.FeedbackOption)
     */
    @Override
    public void delete(FeedbackOption feedbackOption) {
        entityManager.remove(entityManager.find(FeedbackOption.class, feedbackOption.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#isFeedbackOption(java.lang.String)
     */
    @Override
    public Boolean isFeedbackOption(String uuid) {
        Query query = entityManager.createQuery("select fo from FeedbackOption fo where fo.uuid = ?1 and fo.visible = 'Y' order by fo.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#hasReferences(java.lang.String)
     */
    @Override
    public Boolean hasReferences(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public FeedbackOption findByUuid(String uuid) {
        return entityManager.find(FeedbackOption.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FeedbackOption> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from FeedbackOption fo order by fo.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FeedbackOption> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT fo FROM FeedbackOption fo JOIN fo.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FeedbackOption> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("from FeedbackOption fo where fo.visible = ?1 order by fo.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("1", true);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(fo) from FeedbackOption fo");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(fo) from FeedbackOption fo where fo.visible = ?1");
        query.setParameter("1", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
