/**
 * 
 */
package de.tivsource.page.dao.course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.jpa.AvailableHints;

import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
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
public class CourseDao implements CourseDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CourseDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#save(de.tivsource.page.entity.course.Course)
     */
    @Override
    public void save(Course course) {
        LOGGER.info("save(Course course) aufgerufen");
        entityManager.persist(course);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#merge(de.tivsource.page.entity.course.Course)
     */
    @Override
    public void merge(Course course) {
        LOGGER.info("merge(Course course) aufgerufen");
        entityManager.merge(course);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#delete(de.tivsource.page.entity.course.Course)
     */
    @Override
    public void delete(Course course) {
        entityManager.remove(entityManager.find(Course.class, course.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#isCourseUrl(java.lang.String)
     */
    @Override
    public Boolean isCourseUuid(String uuid) {
        Query query = entityManager.createQuery("select c from Course c where c.uuid = :uuid and c.visible = :visible order by c.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#hasMenuEntry(java.lang.String)
     */
    @Override
    public Boolean hasMenuEntry(String uuid) {
        Course course = entityManager.find(Course.class, uuid);
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.contentItem = :contentItem order by ce.uuid asc");
        query.setParameter("contentItem", course);
        return (query.getResultList().size() > 0 ? true : false);
    }

    @Override
    public Boolean hasSubSumption(String uuid) {
        Course course = entityManager.find(Course.class, uuid);
        Query query = entityManager.createQuery("select s from Subsumption s where :contentItem MEMBER OF s.contentItems order by s.uuid asc");
        query.setParameter("contentItem", course);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Course findByTechnical(String technical) {
        LOGGER.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select l from Course l where l.technical = :technical");
        query.setParameter("technical", technical);
        Course course = query.getResultList().isEmpty() ? null : (Course)query.setHint(AvailableHints.HINT_READ_ONLY, true).getSingleResult(); 
        if(course != null) {
            Hibernate.initialize(course.getDescriptionMap());
            Hibernate.initialize(course.getContentMap());
            Hibernate.initialize(course.getCssGroup());
            Hibernate.initialize(course.getCssGroup().getFiles());
            Hibernate.unproxy(course.getDescriptionMap());
            Hibernate.unproxy(course.getContentMap());
            Hibernate.unproxy(course.getCssGroup());
            Hibernate.unproxy(course.getCssGroup().getFiles());
            Hibernate.initialize(course.getLocation());
            Hibernate.initialize(course.getLocation().getDescriptionMap());
        }
        return course;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Course findByUuid(String uuid) {
        Course course = entityManager.find(Course.class, uuid);
        Hibernate.initialize(course.getLecturers());
        Set<Lecturer> lecturerList = course.getLecturers();
        for (Lecturer lecturer : lecturerList) {
            Hibernate.initialize(lecturer.getDescriptionMap());
            Hibernate.initialize(lecturer.getImage());
            Hibernate.initialize(lecturer.getCourses());
        }
        Hibernate.initialize(course.getUnits());
        Set<CourseUnit> courseUnitList = course.getUnits();
        for (CourseUnit courseUnit : courseUnitList) {
            Hibernate.initialize(courseUnit.getDescriptionMap());
            Hibernate.initialize(courseUnit.getContentMap());
            Hibernate.initialize(courseUnit.getImage());
        }
        Hibernate.initialize(course.getImage());
        Hibernate.initialize(course.getDescriptionMap());
        Hibernate.initialize(course.getContentMap());
        Hibernate.initialize(course.getCssGroup());
        Hibernate.initialize(course.getCssGroup().getFiles());
        Hibernate.initialize(course.getLocation());
        Hibernate.initialize(course.getLocation().getAddress());
        Hibernate.initialize(course.getLocation().getDescriptionMap());
        Hibernate.initialize(course.getRegistrations());
        return course;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Course> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Course l");
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<Course> courseList = query.getResultList();
        for (Course course : courseList) {
            Hibernate.initialize(course.getImage());
            Hibernate.initialize(course.getLecturers());
            Set<Lecturer> lecturerList = course.getLecturers();
            for (Lecturer lecturer : lecturerList) {
                Hibernate.initialize(lecturer.getDescriptionMap());
                Hibernate.initialize(lecturer.getImage());
            }
            Hibernate.initialize(course.getDescriptionMap());
            Hibernate.initialize(course.getLocation());
            Hibernate.initialize(course.getLocation().getDescriptionMap());
        }
        return courseList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Course> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT l FROM Course l JOIN l.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<Course> courseList = query.getResultList();
        for (Course course : courseList) {
            Hibernate.initialize(course.getImage());
            Hibernate.initialize(course.getLecturers());
            Set<Lecturer> lecturerList = course.getLecturers();
            for (Lecturer lecturer : lecturerList) {
                Hibernate.initialize(lecturer.getDescriptionMap());
                Hibernate.initialize(lecturer.getImage());
            }
            Hibernate.initialize(course.getDescriptionMap());
            Hibernate.initialize(course.getLocation());
            Hibernate.initialize(course.getLocation().getDescriptionMap());
        }
        return courseList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Course> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("FROM Course c WHERE c.visible = :visible AND c.startDate > :date ORDER BY c.startDate ASC");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.minusDays(2);
        query.setParameter("date", java.sql.Date.valueOf(tomorrow));
        List<Course> courseList = query.getResultList();
        for (Course course : courseList) {
            Hibernate.initialize(course.getImage());
            Hibernate.initialize(course.getDescriptionMap());
            Hibernate.initialize(course.getLecturers());
            Set<Lecturer> lecturerList = course.getLecturers();
            for (Lecturer lecturer : lecturerList) {
                Hibernate.initialize(lecturer.getDescriptionMap());
                Hibernate.initialize(lecturer.getImage());
            }
            Hibernate.initialize(course.getLocation());
            Hibernate.initialize(course.getLocation().getDescriptionMap());
            Hibernate.initialize(course.getRegistrations());
        }
        return courseList;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.course.CourseDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(c) from Course c");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(c) from Course c where c.visible = :visible AND c.startDate > :date");
        query.setParameter("visible", true);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.minusDays(2);
        query.setParameter("date", java.sql.Date.valueOf(tomorrow));
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public void updateLecturerSet(Course course, SortedSet<Lecturer> lecturerSet) {
        LOGGER.info("updateLecturerSet(Course course, SortedSet<Lecturer> lecturerSet) aufgerufen");
        Course dbCourse = entityManager.find(Course.class, course.getUuid());
        LOGGER.info("Anzahl der Objekte der Variable lecturerSet: " + lecturerSet.size());

        // Hole die Lecturer Objekte aus dem LecturerSet des Course Objektes und
        // speichere Sie in einem weiteren Set
        List<Lecturer> lecturerToSaveSet = new ArrayList<Lecturer>();
        for(Lecturer lecturer : dbCourse.getLecturers()) {
            LOGGER.info("Lecturer Objekt das zu lecturerToSaveSet hinzugefügt wird. UUID: "+ lecturer.getUuid());
            lecturerToSaveSet.add(lecturer);
        }
        LOGGER.info("Lecturer Objekte erfolgreich in ein externes Set gespeichert.");

        LOGGER.info("Anzahl der Objekte der Variable lecturerToSaveSet: " + lecturerToSaveSet.size());
        LOGGER.info("Lösche Objekte die erhalten bleiben sollen aus der Variable lecturerToSaveSet.");
        // Lösche die nicht geänderten Lecturer Objekte aus dem Set
        lecturerToSaveSet.removeAll(lecturerSet);
        LOGGER.info("Anzahl der Objekte der Variable lecturerToSaveSet nach dem löschen: " + lecturerToSaveSet.size());
        
        // Lösche die Lecturer Objekte aus dem LecturerSet des Course Objektes
        // und speichere die Lecturer Objekte
        for(Lecturer lecturer : lecturerToSaveSet) {
            LOGGER.info("Lecturer Objekt das aus dem Course Objekt entfernt wird. UUID: "+ lecturer.getUuid());
            dbCourse.removeLecturer(lecturer);
            LOGGER.info("Anzahl der Objekte nach dem entfernen des Lecturer Objektes aus Set: " + dbCourse.getLecturers().size());
            entityManager.merge(lecturer);
        }
        LOGGER.info("Lecturer Objekte erfolgreich aus dem Course Objekt entfernt und gespeichert.");

        LOGGER.info("Anzahl der Lecturer Objekte in der Varibale lecturerSet: " + lecturerSet.size());
        LOGGER.info("Lösche alle schon vorhanden Lecturer Objekte aus dem zu speichernden lecturerSet");
        // Lösche alle schon vorhanden Lecturer Objekte aus dem zu speichernden LecturerSet
        lecturerSet.removeAll(dbCourse.getLecturers());
        LOGGER.info("Anzahl der Lecturer Objekte in der Varibale lecturerSet nach dem löschen: " + lecturerSet.size());

        // Füge die neuen Allergen Objekte dem Article Objekt hinzu
        for(Lecturer lecturer : lecturerSet) {
            LOGGER.info("Lecturer Objekt das dem Course Objekt hinzugefügt wird. UUID: "+ lecturer.getUuid());
            Lecturer dbLecturer = entityManager.find(Lecturer.class, lecturer.getUuid());
            dbCourse.addLecturer(dbLecturer);
            LOGGER.info("Anzahl der Objekte nach dem hinzufügen des Lecturer Objektes in das Set: " + dbCourse.getLecturers().size());
            entityManager.merge(dbLecturer);
        }
        LOGGER.info("Neue Lecturer Objekte erfolgreich hinzugefügt.");
        entityManager.merge(dbCourse);
        LOGGER.info("Course Objekt erfolgreich aktualisiert.");
    }

    @Override
    public void removeCourseUnit(Course course, String courseUnitUuid) {
        Course dbCourse = entityManager.find(Course.class, course.getUuid());
        dbCourse.setModified(new Date());
        dbCourse.setModifiedAddress(course.getModifiedAddress());
        dbCourse.setModifiedBy(course.getModifiedBy());
        LOGGER.info("Anzahl der Kurseinheiten im Set: " + dbCourse.getUnits().size());

        CourseUnit toDelete = null;
        // Durchlaufe alle Einheiten des Kurses
        for(CourseUnit dbCourseUnit : dbCourse.getUnits()) {
            // Wenn die Einheit der gesuchten entspricht
            if(dbCourseUnit.getUuid().compareTo(courseUnitUuid) == 0) {
                toDelete = dbCourseUnit;
            }// Ende if()
        }// Ende for()
        
        dbCourse.getUnits().remove(toDelete);
        LOGGER.info("Anzahl der Kurseinheiten im Set: " + dbCourse.getUnits().size());
        entityManager.merge(dbCourse);
        entityManager.remove(toDelete);
    }

    @Override
    public Integer registrations(Course course) {
        Query query = entityManager.createQuery("SELECT Count(r) FROM Registration r WHERE "
                + "(r.course = :course AND r.status = :predeclared) OR"
                + "(r.course = :course AND r.status = :declared) OR"
                + "(r.course = :course AND r.status = :confirmed) OR"
                + "(r.course = :course AND r.status = :paid) OR"
                + "(r.course = :course AND r.status = :waiting)");
        query.setParameter("course", course);
        query.setParameter("predeclared", RegistrationStatus.PREDECLARED);
        query.setParameter("declared", RegistrationStatus.DECLARED);
        query.setParameter("confirmed", RegistrationStatus.CONFIRMED);
        query.setParameter("paid", RegistrationStatus.PAID);
        query.setParameter("waiting", RegistrationStatus.WAITING);
        LOGGER.info("Summe der eingetragenen, bestätigten und bezahlten Anmeldungen: " + query.getSingleResult().toString());
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
