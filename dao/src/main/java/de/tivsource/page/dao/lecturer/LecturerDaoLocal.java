/**
 * 
 */
package de.tivsource.page.dao.lecturer;

import java.util.List;

import de.tivsource.page.entity.lecturer.Lecturer;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface LecturerDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Lecturer.
     * @param lecturer - Lecturer Objekt das gespeichert werden soll
     */
    public void save(Lecturer lecturer);

    /**
     * Methode zum verändern eines Objektes der Klasse Lecturer.
     * @param lecturer - Lecturer Objekt das verändert werden soll
     */
    public void merge(Lecturer lecturer);

    /**
     * Methode zum löschen eines Objektes der Klasse Lecturer.
     * @param lecturer - zu löschendes Lecturer Objekt
     */
    public void delete(Lecturer lecturer);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (Lecturer) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (Lecturer) ist.
     */
    public Boolean isLecturerUrl(String urlName);

    public Boolean hasMenuEntry(String uuid);

    public Boolean hasSubSumption(String uuid);

    public Boolean hasCourse(String uuid);

    /**
     * Methode zum laden eines Objektes der Klasse Lecturer anhand der
     * Benutzernames.
     * 
     * @param lecturername - Benutzername des Objektes das geladen werden soll.
     * @return Lecturer - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public Lecturer findByTechnical(String lecturername);

    public Lecturer findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Lecturer Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Lecturer Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Lecturer> - Liste von Lecturer Objekten
     */
    public List<Lecturer> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Lecturer Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Lecturer> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode zum Laden einer Listen von Lecturer Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenden Objekte. Diese
     * Methode liefert nur die Objekte bei denen das Attribut visible=true 
     * gesetzt ist.
     * 
     * @param start
     * @param max
     * @return
     */
    public List<Lecturer> findAllVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Lecturer Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Lecturer Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
