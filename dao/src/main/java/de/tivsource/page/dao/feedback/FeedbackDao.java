/**
 * 
 */
package de.tivsource.page.dao.feedback;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.feedback.Feedback;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class FeedbackDao implements FeedbackDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FeedbackDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackDaoLocal#merge(de.tivsource.page.entity.feedback.Feedback)
     */
    @Override
    public void merge(Feedback feedback) {
        LOGGER.info("merge(Feedback feedback) aufgerufen");
        entityManager.merge(feedback);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackDaoLocal#delete(de.tivsource.page.entity.feedback.Feedback)
     */
    @Override
    public void delete(Feedback feedback) {
        entityManager.remove(entityManager.find(Feedback.class, feedback.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Feedback findByUuid(String uuid) {
        return entityManager.find(Feedback.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Feedback> findAll(Integer start, Integer max) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public List<Feedback> findAll(Integer start, Integer max, String field,
            String order) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.feedback.FeedbackDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        // TODO Auto-generated method stub
        return null;
    }

}
