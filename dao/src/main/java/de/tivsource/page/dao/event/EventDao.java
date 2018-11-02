/**
 * 
 */
package de.tivsource.page.dao.event;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.event.Event;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class EventDao implements EventDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EventDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#save(de.tivsource.page.entity.event.Event)
     */
    @Override
    public void save(Event event) {
        LOGGER.info("save(Event event) aufgerufen");
        entityManager.persist(event);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#merge(de.tivsource.page.entity.event.Event)
     */
    @Override
    public void merge(Event event) {
        LOGGER.info("merge(Event event) aufgerufen");
        entityManager.merge(event);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#delete(de.tivsource.page.entity.event.Event)
     */
    @Override
    public void delete(Event event) {
        entityManager.remove(entityManager.find(Event.class, event.getUuid()));
    }

    @Override
    public Boolean isEvent(String uuid) {
        Query query = entityManager.createQuery("select e from Event e where e.uuid = :uuid and e.visible = 'Y' and e.reservation = 'Y' order by e.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Event findByUuid(String uuid) {
        return entityManager.find(Event.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Event> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Event e order by e.beginning asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Event> findAll(Integer start, Integer max, String field, String order) {
    	LOGGER.info("findAll(Integer start, Integer max, String field, String order) aufgerufen");
    	LOGGER.info("Inhalt start: " + start);
    	LOGGER.info("Inhalt max: " + max);
    	LOGGER.info("Inhalt field: " + field);
    	LOGGER.info("Inhalt order: " + order);
        String queryString = "SELECT DISTINCT e FROM Event e JOIN e.descriptionMap dm JOIN e.location.descriptionMap edm WHERE dm.language = 'DE' AND edm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> findAllActive(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Event e where e.ending > :date order by e.beginning asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("date", new Date());
        return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> findAllActive(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT e FROM Event e JOIN e.descriptionMap dm JOIN e.location.descriptionMap edm WHERE dm.language = 'DE' AND edm.language = 'DE' AND e.ending > :date ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("date", new Date());
        return query.getResultList();
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> findAll(String uuid, Integer start, Integer max) {
        Query query = entityManager.createQuery("from Event e where e.location.uuid = :uuid and e.visible = 'Y' and e.beginning > :date order by e.beginning asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("uuid", uuid);
        query.setParameter("date", new Date());
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.event.EventDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(e) from Event e");
        return Integer.parseInt(query.getSingleResult().toString());
    }

	@Override
	public Integer countAllActive() {
        Query query = entityManager.createQuery("Select Count(e) from Event e where e.ending > :date");
        query.setParameter("date", new Date());
        return Integer.parseInt(query.getSingleResult().toString());
	}

    @Override
    public Integer countAll(String uuid) {
        Query query = entityManager.createQuery("Select Count(e) from Event e where e.location.uuid = :uuid and e.visible = 'Y' and e.beginning > :date");
        query.setParameter("uuid", uuid);
        query.setParameter("date", new Date());
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
