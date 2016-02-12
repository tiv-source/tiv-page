/**
 * 
 */
package de.tivsource.page.dao.vacancy;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * @author Marc Michele
 *
 */
@Local
public interface VacancyDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Vacancy.
     * @param vacancy - Vacancy Objekt das gespeichert werden soll
     */
    public void save(Vacancy vacancy);

    /**
     * Methode zum verändern eines Objektes der Klasse Vacancy.
     * @param vacancy - Vacancy Objekt das verändert werden soll
     */
    public void merge(Vacancy vacancy);

    /**
     * Methode zum löschen eines Objektes der Klasse Vacancy.
     * @param vacancy - zu löschendes Vacancy Objekt
     */
    public void delete(Vacancy vacancy);

    public Boolean isVacancy(String uuid);

    /**
     * Methode zum laden eines Objektes der Klasse Vacancy, anhand der UUID des
     * Objektes.
     * 
     * @param uuid - UUID des Objektes das geladen werden soll
     * @return Vacany - Objekt das die angegebene UUID besitzt
     */
    public Vacancy findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Vacancy Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Vacancy Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Vacancy> - Liste von Vacancy Objekten
     */
    public List<Vacancy> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Vacancy Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Vacancy> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Vacancy Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Vacancy Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
