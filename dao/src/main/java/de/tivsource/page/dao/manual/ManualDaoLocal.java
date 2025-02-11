/**
 * 
 */
package de.tivsource.page.dao.manual;

import java.util.List;

import de.tivsource.page.entity.manual.Manual;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ManualDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Manual.
     * @param manual - Manual Objekt das gespeichert werden soll
     */
    public void save(Manual manual);

    /**
     * Methode zum verändern eines Objektes der Klasse Manual.
     * @param manual - Manual Objekt das verändert werden soll
     */
    public void merge(Manual manual);

    /**
     * Methode zum löschen eines Objektes der Klasse Manual.
     * @param manual - zu löschendes Manual Objekt
     */
    public void delete(Manual manual);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (Manual) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (Manual) ist.
     */
    public Boolean isManualUuid(String uuid);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Anleitung (Manual) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Anleitung (Manual) ist.
     */
    public Boolean isManualUrl(String urlName);

    public Boolean hasMenuEntry(String uuid);

    /**
     * Methode zum laden eines Objektes der Klasse Manual anhand der
     * Uuid.
     * 
     * @param uuid - UUID des Objektes das geladen werden soll.
     * @return Manual - Objekt das die angegebene UUID besitzt.
     */
    public Manual findByUuid(String uuid);

    /**
     * Methode zum laden eines Objektes der Klasse Manual anhand des
     * technischen Namens.
     * 
     * @param technical - Technischer Name des Objektes das geladen werden soll.
     * @return Manual - Objekt das den angegebenen technischen Namen besitzt.
     */
    public Manual findByTechnical(String technical);

    /**
     * Methode zum laden einer Liste von Manual Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Manual Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Manual> - Liste von Manual Objekten
     */
    public List<Manual> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Manual Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Manual> findAll(Integer start, Integer max, String field, String order);

    public List<Manual> findAllVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Manual Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Manual Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
