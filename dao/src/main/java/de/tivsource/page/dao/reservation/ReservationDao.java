/**
 * 
 */
package de.tivsource.page.dao.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class ReservationDao implements ReservationDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(ReservationDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#save(de.tivsource.page.entity.reservation.Reservation)
     */
    @Override
    public void save(Reservation reservation) {
        LOGGER.info("save(Reservation reservation) aufgerufen");
        entityManager.persist(reservation);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#merge(de.tivsource.page.entity.reservation.Reservation)
     */
    @Override
    public void merge(Reservation reservation) {
        LOGGER.info("merge(Reservation reservation) aufgerufen");
        entityManager.merge(reservation);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#delete(de.tivsource.page.entity.reservation.Reservation)
     */
    @Override
    public void delete(Reservation reservation) {
        entityManager.remove(entityManager.find(Reservation.class, reservation.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Reservation findByUuid(String uuid) {
        return entityManager.find(Reservation.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reservation> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Reservation r");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reservation> findAll(Integer start, Integer max, String field,
            String order) {
        String queryString = "select r from Reservation r order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reservation> findAll(Event event, Integer start, Integer max) {
        Query query = entityManager.createQuery("from Reservation r where r.event = ?1");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("1", event);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reservation> findAll(Event event, Integer start, Integer max, String field,
            String order) {
        String queryString = "select r from Reservation r where r.event = ?1 order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("1", event);
        return query.getResultList();
    }
    
    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Reservation r");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer countAll(Event event) {
        Query query = entityManager.createQuery("Select Count(r) from Reservation r where r.event = ?1");
        query.setParameter("1", event);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
