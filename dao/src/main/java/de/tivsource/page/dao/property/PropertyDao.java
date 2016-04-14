/**
 * 
 */
package de.tivsource.page.dao.property;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.property.Property;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PropertyDao implements PropertyDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PropertyDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#save(de.tivsource.page.entity.property.Property)
     */
    @Override
    public void save(Property property) {
        LOGGER.info("save(Property property) aufgerufen");
        entityManager.persist(property);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#merge(de.tivsource.page.entity.property.Property)
     */
    @Override
    public void merge(Property property) {
        LOGGER.info("merge(Property property) aufgerufen");
        entityManager.merge(property);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#delete(de.tivsource.page.entity.property.Property)
     */
    @Override
    public void delete(Property property) {
        entityManager.remove(entityManager.find(Property.class, property.getKey()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#findByKey(java.lang.String)
     */
    @Override
    public Property findByKey(String key) {
        return entityManager.find(Property.class, key);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Property> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Property p");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Property> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "select p from Property p order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.property.PropertyDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(p) from Property p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
