/**
 * 
 */
package de.tivsource.page.dao.captcha;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import de.tivsource.page.common.captcha.Captcha;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class CaptchaDao implements CaptchaDaoLocal {

    private static final Logger logger = Logger.getLogger("INFO");

    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#merge(de.czastka.entity.captcha.Captcha)
     */
    @Override
    public void merge(Captcha captcha) {
        logger.info("merge(Captcha captcha) aufgerufen");
        captcha.setModified(new Date());
        entityManager.merge(captcha);
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#delete(de.czastka.entity.captcha.Captcha)
     */
    @Override
    public void delete(Captcha captcha) {
        entityManager.remove(entityManager.find(Captcha.class, captcha.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Captcha findByUuid(String uuid) {
        return entityManager.find(Captcha.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Captcha> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Captcha c order by c.uuid asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Captcha> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT c FROM Captcha c ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(c) from Captcha c");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.captcha.CaptchaDaoLocal#random()
     */
    @Override
    public Captcha random() {
        Random random = new Random();
        int next = random.nextInt(countAll());
        logger.info("Captcha Random Number: " + next);
        return findAll(next, 1).get(0);
    }

}// Ende class
