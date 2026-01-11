/**
 * 
 */
package de.tivsource.page.dao.location;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.OpeningHour;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.location.LocationDaoLocal#isLocation(java.lang.String)
	 */
	@Override
	public Boolean isLocation(String uuid) {
        Query query = entityManager.createQuery("select l from Location l where l.uuid = :uuid and l.visible = :visible order by l.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#isEventLocation(java.lang.String)
     */
    @Override
    public Boolean isEventLocation(String uuid) {
        Query query = entityManager.createQuery("select l from Location l where l.uuid = :uuid and l.visible = :visible and l.event = :event order by l.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        query.setParameter("event", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    @Override
    public Boolean hasEvents(String uuid) {
        Location location = entityManager.find(Location.class, uuid);
        Query query = entityManager.createQuery("SELECT e FROM Event e WHERE e.location = :location order by e.uuid asc");
        query.setParameter("location", location);
        return (query.getResultList().size() > 0 ? true : false);
    }

    @Override
    public Boolean hasVacancies(String uuid) {
        Location location = entityManager.find(Location.class, uuid);
        Query query = entityManager.createQuery("SELECT v FROM Vacancy v WHERE v.location = :location order by v.uuid asc");
        query.setParameter("location", location);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Location findByUuid(String uuid) {
        Location location = entityManager.find(Location.class, uuid);
        Hibernate.initialize(location.getImage());
        Hibernate.initialize(location.getDescriptionMap());
        Hibernate.initialize(location.getCssGroup());
        Hibernate.initialize(location.getCssGroup().getFiles());
        Hibernate.initialize(location.getOpeningHours());
        return location;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findByUuidWidthEvents(java.lang.String)
     */
    @Override
    public Location findByUuidWidthEvents(String uuid) {
        Location location = entityManager.find(Location.class, uuid);
        Hibernate.initialize(location.getImage());
        Hibernate.initialize(location.getDescriptionMap());
        Hibernate.initialize(location.getEvents());
        Hibernate.initialize(location.getCssGroup());
        Hibernate.initialize(location.getCssGroup().getFiles());
        Hibernate.initialize(location.getOpeningHours());
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
        List<Location> locationList = query.getResultList();
        for (Location location : locationList) {
            Hibernate.initialize(location.getImage());
            Hibernate.initialize(location.getDescriptionMap());
            Hibernate.initialize(location.getAddress());
            Hibernate.initialize(location.getContactDetails());
        }
        return locationList;
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
        List<Location> locationList = query.getResultList();
        for (Location location : locationList) {
            Hibernate.initialize(location.getImage());
            Hibernate.initialize(location.getDescriptionMap());
        }
        return locationList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findAllEventLocation()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAllEventLocation() {
        String queryString = "select l from Location l where l.event = :visible order by l.orderNumber";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("visible", true);
        List<Location> locationList = query.getResultList();
        for (Location location : locationList) {
            Hibernate.initialize(location.getImage());
            Hibernate.initialize(location.getDescriptionMap());
        }
        return locationList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Location> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("FROM Location l WHERE l.visible = :visible AND l.inLocationList = :inLocationList ORDER BY l.orderNumber ASC");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        query.setParameter("inLocationList", true);
        List<Location> locationList = query.getResultList();
        for (Location location : locationList) {
            Hibernate.initialize(location.getImage());
            Hibernate.initialize(location.getDescriptionMap());
            Hibernate.initialize(location.getOpeningHours());
            Hibernate.initialize(location.getAddress());
            Hibernate.initialize(location.getContactDetails());
        }
        return locationList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(l) from Location l");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(l) from Location l where l.visible = :visible AND l.inLocationList = :inLocationList");
        query.setParameter("visible", true);
        query.setParameter("inLocationList", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.location.LocationDaoLocal#removeOpeningHour(java.lang.Integer, java.lang.String)
     */
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
