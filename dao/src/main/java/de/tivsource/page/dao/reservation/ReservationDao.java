/**
 * 
 */
package de.tivsource.page.dao.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

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
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Reservation findByUuid(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Reservation> findAll(Integer start, Integer max) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public List<Reservation> findAll(Integer start, Integer max, String field,
            String order) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.reservation.ReservationDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        // TODO Auto-generated method stub
        return null;
    }

}
