/**
 * 
 */
package de.tivsource.page.dao.province;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.province.Province;
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
public class ProvinceDao implements ProvinceDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ProvinceDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#save(de.tivsource.page.common.province.Province)
     */
    @Override
    public void save(Province province) {
        LOGGER.info("save(Province province) aufgerufen");
        entityManager.persist(province);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#merge(de.tivsource.page.common.province.Province)
     */
    @Override
    public void merge(Province province) {
        LOGGER.info("merge(Province province) aufgerufen");
        entityManager.merge(province);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#delete(de.tivsource.page.common.province.Province)
     */
    @Override
    public void delete(Province province) {
        Province dbProvince = entityManager.find(Province.class, province.getUuid());
        Country dbCountry = dbProvince.getCountry();
        dbCountry.getProvinceSet().remove(dbProvince);
        entityManager.merge(dbCountry);
        dbProvince.setCountry(null);
        entityManager.remove(entityManager.merge(dbProvince));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#findByCode(java.lang.String)
     */
    @Override
    public Province findByCode(String code) {
        LOGGER.info("findByCode(String code) aufgerufen.");
        try {
            Query query = entityManager.createQuery("select p from Province p where c.code = :code");
            query.setParameter("code", code);
            return (Province)query.getSingleResult();
        } catch(NoResultException e) {
                return null;
        }
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Province findByUuid(String uuid) {
        return entityManager.find(Province.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Province> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Province p");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Province> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT p FROM Province p JOIN p.descriptionMap dm WHERE dm.locale = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.province.ProvinceDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(p) from Province p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
