/**
 * 
 */
package de.tivsource.page.dao.appointment;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.appointment.Appointment;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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
        Query query = entityManager.createQuery("select ap from Appointment ap where ap.uuid = :uuid and ap.visible = :visible order by ap.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#hasMenuEntry(java.lang.String)
     */
    @Override
    public Boolean hasMenuEntry(String uuid) {
        Appointment appointment = entityManager.find(Appointment.class, uuid);
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.contentItem = :contentItem order by ce.uuid asc");
        query.setParameter("contentItem", appointment);
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
        String queryString = "SELECT ap FROM Appointment ap WHERE ap.pointInTime > :date AND ap.visible = :visible AND ap.visibleFrom < :date ORDER BY ap.pointInTime asc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        query.setParameter("date", new Date());
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#findAllArchiveVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAllArchiveVisible(Integer start, Integer max) {
        String queryString = "SELECT ap FROM Appointment ap WHERE ap.pointInTime < :date AND ap.visible = :visible ORDER BY ap.pointInTime desc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        query.setParameter("date", new Date());
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
        Query query = entityManager.createQuery("Select Count(ap) from Appointment ap WHERE ap.pointInTime > :date AND ap.visible = :visible");
        query.setParameter("date", new Date());
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.appointment.AppointmentDaoLocal#countAllArchiveVisible()
     */
    @Override
    public Integer countAllArchiveVisible() {
        Query query = entityManager.createQuery("Select Count(ap) from Appointment ap WHERE ap.pointInTime < :date AND ap.visible = :visible");
        query.setParameter("date", new Date());
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
