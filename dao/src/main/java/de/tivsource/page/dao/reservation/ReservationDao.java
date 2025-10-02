/**
 * 
 */
package de.tivsource.page.dao.reservation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.reservation.Reservation;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class ReservationDao implements ReservationDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ReservationDao.class);

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
        Query query = entityManager.createQuery("from Reservation r where r.event = :event");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("event", event);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Reservation> findAll(Event event, Integer start, Integer max, String field, String order) {
        String queryString = "select r from Reservation r where r.event = :event order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("event", event);
        return query.getResultList();
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#confirmationQueue(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> confirmationQueue(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Reservation r where r.confirmed = :confirmed");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("confirmed", false);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#confirmationQueue(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> confirmationQueue(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT r FROM Reservation r JOIN r.event.descriptionMap dm JOIN r.event.location.descriptionMap edm WHERE r.confirmed = :confirmed AND dm.language = 'DE' AND edm.language = 'DE' order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("confirmed", false);
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

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#countAll(de.tivsource.page.entity.event.Event)
     */
    @Override
    public Integer countAll(Event event) {
        Query query = entityManager.createQuery("Select Count(r) from Reservation r where r.event = :event");
        query.setParameter("event", event);
        return Integer.parseInt(query.getSingleResult().toString());
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#countConfirmationQueue()
	 */
	@Override
	public Integer countConfirmationQueue() {
        Query query = entityManager.createQuery("Select Count(r) from Reservation r where r.confirmed = :confirmed");
        query.setParameter("confirmed", false);
        return Integer.parseInt(query.getSingleResult().toString());
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#countQuantity(java.lang.String)
	 */
	@Override
	public Integer countQuantity(String uuid) {
        Query query = entityManager.createQuery("select sum(r.quantity) from Reservation r where r.event.uuid = :uuid group by r.event");
        query.setParameter("uuid", uuid);
        try {
        	String result = query.getSingleResult().toString();
        	return Integer.parseInt(result);
        } catch (Exception e) {
        	return 0;
        }
	}

}// Ende class
