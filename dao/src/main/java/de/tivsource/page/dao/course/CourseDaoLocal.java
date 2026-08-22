/**
 * 
 */
package de.tivsource.page.dao.course;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.lecturer.Lecturer;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface CourseDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Course.
     * @param course - Course Objekt das gespeichert werden soll
     */
    public void save(Course course);

    public void saveOrUpdate(Course course);

    /**
     * Methode zum verändern eines Objektes der Klasse Course.
     * @param course - Course Objekt das verändert werden soll
     */
    public void merge(Course course);

    /**
     * Methode zum löschen eines Objektes der Klasse Course.
     * @param course - zu löschendes Course Objekt
     */
    public void delete(Course course);

    /**
     * Metholde um zu überprüfen ob der angegebene uuid zu einem Course gehört.
     * 
     * @param uuid - Dis uuid die überprüft werden soll.
     * @return Boolean - true wenn es ein Course gibt.
     */
    public Boolean isCourseUuid(String uuid);

    public Boolean hasMenuEntry(String uuid);

    public Boolean hasSubSumption(String uuid);

    /**
     * Methode zum laden eines Objektes der Klasse Course anhand der
     * Benutzernames.
     * 
     * @param coursename - Benutzername des Objektes das geladen werden soll.
     * @return Course - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public Course findByTechnical(String coursename);

    public Optional<Course> findByUuid(String uuid);

    public Optional<Course> findByUuidWithAllRelations(String uuid);

    /**
     * Methode zum laden einer Liste von Course Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Course Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Course> - Liste von Course Objekten
     */
    public List<Course> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Course Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Course> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode zum Laden einer Listen von Course Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenden Objekte. Diese
     * Methode liefert nur die Objekte bei denen das Attribut visible=true 
     * gesetzt ist.
     * 
     * @param start
     * @param max
     * @return
     */
    public List<Course> findAllVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Course Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Course Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

    public void updateLecturerSet(Course course, SortedSet<Lecturer> lecturerSet);

    public void removeCourseUnit(Course course, String courseUnitUuid);

    public Integer registrations(Course course);

}// Ende interface
