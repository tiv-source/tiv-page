/**
 * 
 */
package de.tivsource.page.dao.location;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class LocationDao implements LocationDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LocationDao.class);

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

	@Override
	public Boolean isLocation(String uuid) {
        Query query = entityManager.createQuery("select l from Location l where l.uuid = ?1 and l.visible = 'Y' order by l.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
	}

    @Override
    public Boolean isEventLocation(String uuid) {
        Query query = entityManager.createQuery("select l from Location l where l.uuid = ?1 and l.visible = 'Y' and l.event = 'Y' order by l.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Location findByUuid(String uuid) {
        return entityManager.find(Location.class, uuid);
    }

    @Override
    public Location findByUuidWidthEvents(String uuid) {
        Location location = entityManager.find(Location.class, uuid);
        LOGGER.debug("Größe der Eventliste: " + location.getEvents().size());
        return location;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("SELECT l FROM Location l");
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
        String queryString = "SELECT DISTINCT l FROM Location l JOIN l.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAllEventLocation() {
        String queryString = "select l from Location l where l.event = 'Y' order by l.order";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("FROM Location l WHERE l.visible = 'Y' AND l.inLocationList = 'Y' ORDER BY l.order ASC");
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

    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(l) from Location l where l.visible = 'Y'");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public void removeOpeningHour(Integer index, String location) {

        Location dbLocation = entityManager.find(Location.class, location);
        
        List<OpeningHour> openingHours = new ArrayList<OpeningHour>(dbLocation.getOpeningHours());
        LOGGER.info("Anzahl der OpeningHours in der Liste: " + openingHours.size());

        OpeningHour openingHour = openingHours.get(index);
        dbLocation.getOpeningHours().remove(openingHour);

        LOGGER.info("Anzahl der OpeningHours im Set: " + dbLocation.getOpeningHours().size());

        entityManager.merge(dbLocation);
        entityManager.remove(openingHour);

    }



}// Ende class
