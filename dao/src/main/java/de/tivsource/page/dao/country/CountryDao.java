/**
 * 
 */
package de.tivsource.page.dao.country;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.country.Country;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class CountryDao implements CountryDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CountryDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#save(de.tivsource.page.common.country.Country)
     */
    @Override
    public void save(Country country) {
        LOGGER.info("save(Country country) aufgerufen");
        entityManager.persist(country);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#merge(de.tivsource.page.common.country.Country)
     */
    @Override
    public void merge(Country country) {
        LOGGER.info("merge(Country country) aufgerufen");
        entityManager.merge(country);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#delete(de.tivsource.page.common.country.Country)
     */
    @Override
    public void delete(Country country) {
        entityManager.remove(entityManager.find(Country.class, country.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#findByAlpha2(java.lang.String)
     */
    @Override
    public Country findByAlpha2(String alpha2) {
        LOGGER.info("findByAlpha2(String alpha2) aufgerufen.");
        try {
            Query query = entityManager.createQuery("select c from Country c where c.alpha2 = :alpha2");
            query.setParameter("alpha2", alpha2);
            return (Country)query.getSingleResult();
        } catch(NoResultException e) {
                return null;
        }
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Country findByUuid(String uuid) {
        return entityManager.find(Country.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Country> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Country c");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Country> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT c FROM Country c JOIN c.descriptionMap dm WHERE dm.locale = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.country.CountryDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(c) from Country c");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
