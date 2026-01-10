/**
 * 
 */
package de.tivsource.page.dao.lecturer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.jpa.AvailableHints;

import de.tivsource.page.entity.lecturer.Lecturer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class LecturerDao implements LecturerDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LecturerDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#save(de.tivsource.page.entity.lecturer.Lecturer)
     */
    @Override
    public void save(Lecturer lecturer) {
        LOGGER.info("save(Lecturer lecturer) aufgerufen");
        entityManager.persist(lecturer);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#merge(de.tivsource.page.entity.lecturer.Lecturer)
     */
    @Override
    public void merge(Lecturer lecturer) {
        LOGGER.info("merge(Lecturer lecturer) aufgerufen");
        entityManager.merge(lecturer);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#delete(de.tivsource.page.entity.lecturer.Lecturer)
     */
    @Override
    public void delete(Lecturer lecturer) {
        entityManager.remove(entityManager.find(Lecturer.class, lecturer.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#isLecturerUrl(java.lang.String)
     */
    @Override
    public Boolean isLecturerUrl(String urlName) {
        Query query = entityManager.createQuery("select l from Lecturer l where l.technical = :urlName and l.visible = :visible order by l.uuid asc");
        query.setParameter("urlName", urlName);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#hasMenuEntry(java.lang.String)
     */
    @Override
    public Boolean hasMenuEntry(String uuid) {
        Lecturer lecturer = entityManager.find(Lecturer.class, uuid);
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.contentItem = :contentItem order by ce.uuid asc");
        query.setParameter("contentItem", lecturer);
        return (query.getResultList().size() > 0 ? true : false);
    }

    @Override
    public Boolean hasSubSumption(String uuid) {
        Lecturer lecturer = entityManager.find(Lecturer.class, uuid);
        Query query = entityManager.createQuery("select s from Subsumption s where :contentItem MEMBER OF s.contentItems order by s.uuid asc");
        query.setParameter("contentItem", lecturer);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Lecturer findByTechnical(String technical) {
        LOGGER.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select l from Lecturer l where l.technical = :technical");
        query.setParameter("technical", technical);
        Lecturer lecturer = query.getResultList().isEmpty() ? null : (Lecturer)query.setHint(AvailableHints.HINT_READ_ONLY, true).getSingleResult(); 
        if(lecturer != null) {
            Hibernate.initialize(lecturer.getDescriptionMap());
            Hibernate.initialize(lecturer.getContentMap());
            Hibernate.initialize(lecturer.getCssGroup());
            Hibernate.initialize(lecturer.getCssGroup().getFiles());
            Hibernate.unproxy(lecturer.getDescriptionMap());
            Hibernate.unproxy(lecturer.getContentMap());
            Hibernate.unproxy(lecturer.getCssGroup());
            Hibernate.unproxy(lecturer.getCssGroup().getFiles());
        }
        return lecturer;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Lecturer findByUuid(String uuid) {
        Lecturer lecturer = entityManager.find(Lecturer.class, uuid); 
        Hibernate.initialize(lecturer.getDescriptionMap());
        Hibernate.initialize(lecturer.getContentMap());
        Hibernate.initialize(lecturer.getCssGroup());
        Hibernate.initialize(lecturer.getCssGroup().getFiles());
        return lecturer;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Lecturer> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Lecturer l");
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<Lecturer> lecturerList = query.getResultList();
        for (Lecturer lecturer : lecturerList) {
            Hibernate.initialize(lecturer.getDescriptionMap());
        }
        return lecturerList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Lecturer> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT l FROM Lecturer l JOIN l.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<Lecturer> lecturerList = query.getResultList();
        for (Lecturer lecturer : lecturerList) {
            Hibernate.initialize(lecturer.getDescriptionMap());
        }
        return lecturerList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.lecturer.LecturerDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(l) from Lecturer l");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
