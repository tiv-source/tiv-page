/**
 * 
 */
package de.tivsource.page.dao.location;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import de.tivsource.page.entity.location.Location;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class LocationDao implements LocationDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(LocationDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#save(com.sun.xml.internal.bind.v2.runtime.Location)
     */
    @Override
    public void save(Location location) {
        LOGGER.info("save(Location location) aufgerufen");
        entityManager.persist(location);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#merge(com.sun.xml.internal.bind.v2.runtime.Location)
     */
    @Override
    public void merge(Location location) {
        LOGGER.info("merge(Location location) aufgerufen");
        entityManager.merge(location);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#delete(com.sun.xml.internal.bind.v2.runtime.Location)
     */
    @Override
    public void delete(Location location) {
        entityManager.remove(entityManager.find(Location.class, location.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Location findByUuid(String uuid) {
        return entityManager.find(Location.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Location l");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "select l from Location l order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(l) from Location l");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
