/**
 * 
 */
package de.tivsource.page.dao.slider;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.slider.Slider;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class SliderDao implements SliderDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SliderDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#save(de.tivsource.page.entity.slider.Slider)
     */
    @Override
    public void save(Slider slider) {
        LOGGER.info("save(Slider slider) aufgerufen");
        entityManager.persist(slider);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#merge(de.tivsource.page.entity.slider.Slider)
     */
    @Override
    public void merge(Slider slider) {
        LOGGER.info("merge(Slider slider) aufgerufen");
        entityManager.merge(slider);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#delete(de.tivsource.page.entity.slider.Slider)
     */
    @Override
    public void delete(Slider slider) {
        entityManager.remove(entityManager.find(Slider.class, slider.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Slider findByUuid(String uuid) {
        return entityManager.find(Slider.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Slider> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Slider s");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Slider> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT s FROM Slider s ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Slider> findAllVisible(Integer start, Integer max, String page) {
        String queryString = "SELECT s FROM Slider s WHERE s.visible = :visible and s.page = :page ORDER BY s.orderNumber desc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        query.setParameter("page", page);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(s) from Slider s");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderDaoLocal#countAllVisible(java.lang.String)
     */
    @Override
    public Integer countAllVisible(String page) {
        Query query = entityManager.createQuery("Select Count(s) from Slider s WHERE s.visible = :visible and s.page = :page");
        query.setParameter("visible", true);
        query.setParameter("page", page);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
