/**
 * 
 */
package de.tivsource.page.dao.appointment;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.appointment.Appointment;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class AppointmentDao implements AppointmentDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AppointmentDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#save(de.tivsource.page.entity.appointment.Appointment)
     */
    @Override
    public void save(Appointment appointment) {
        LOGGER.info("save(Appointment appointment) aufgerufen");
        entityManager.persist(appointment);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#merge(de.tivsource.page.entity.appointment.Appointment)
     */
    @Override
    public void merge(Appointment appointment) {
        LOGGER.info("merge(Appointment appointment) aufgerufen");
        entityManager.merge(appointment);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#delete(de.tivsource.page.entity.appointment.Appointment)
     */
    @Override
    public void delete(Appointment appointment) {
        entityManager.remove(entityManager.find(Appointment.class, appointment.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#isAppointmentUuid(java.lang.String)
     */
    @Override
    public Boolean isAppointmentUuid(String uuid) {
        Query query = entityManager.createQuery("select ap from Appointment ap where ap.uuid = ?1 and ap.visible = 'Y' order by ap.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Appointment findByUuid(String uuid) {
        return entityManager.find(Appointment.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Appointment ap ORDER by ap.uuid");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT ap FROM Appointment ap JOIN ap.descriptionMap dm  WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAllVisible(Integer start, Integer max) {
        String queryString = "SELECT ap FROM Appointment ap WHERE ap.pointInTime > ?1 AND ap.visible = 'Y' ORDER BY ap.pointInTime asc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("1", new Date());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAllArchiveVisible(Integer start, Integer max) {
        String queryString = "SELECT ap FROM Appointment ap WHERE ap.pointInTime < ?1 AND ap.visible = 'Y' ORDER BY ap.pointInTime asc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("1", new Date());
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(ap) from Appointment ap");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(ap) from Appointment ap WHERE ap.pointInTime > ?1 AND ap.visible = 'Y'");
        query.setParameter("1", new Date());
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer countAllArchiveVisible() {
        Query query = entityManager.createQuery("Select Count(ap) from Appointment ap WHERE ap.pointInTime < ?1 AND ap.visible = 'Y'");
        query.setParameter("1", new Date());
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
