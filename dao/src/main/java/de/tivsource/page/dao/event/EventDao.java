/**
 * 
 */
package de.tivsource.page.dao.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(EventDao.class);

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
        Query query = entityManager.createQuery("from Event e");
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
        String queryString = "select e from Event e order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
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

}// Ende class