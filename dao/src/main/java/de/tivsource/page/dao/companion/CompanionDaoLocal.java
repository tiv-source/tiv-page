/**
 * 
 */
package de.tivsource.page.dao.companion;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.companion.Companion;
import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * @author Marc Michele
 *
 */
@Local
public interface CompanionDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Companion.
     * @param companion - Companion Objekt das gespeichert werden soll
     */
    public void save(Companion companion);

    /**
     * Methode zum verändern eines Objektes der Klasse Companion.
     * @param companion - Companion Objekt das verändert werden soll
     */
    public void merge(Companion companion);

    /**
     * Methode zum löschen eines Objektes der Klasse Companion.
     * @param companion - zu löschendes Companion Objekt
     */
    public void delete(Companion companion);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (Companion) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (Companion) ist.
     */
    public Boolean isCompanionUuid(String uuid);
    
    /**
     * Methode zum laden eines Objektes der Klasse Companion anhand der
     * Uuid.
     * 
     * @param uuid - UUID des Objektes das geladen werden soll.
     * @return Companion - Objekt das die angegebene UUID besitzt.
     */
    public Companion findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Companion Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Companion Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Companion> - Liste von Companion Objekten
     */
    public List<Companion> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Companion Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Companion> findAll(Integer start, Integer max, String field, String order);

    public List<Companion> findAllVisible(Integer start, Integer max);

    public List<Companion> findAllVisible(Integer start, Integer max, CompanionGroup companionGroup);
    
    /**
     * Methode die die Anzahl aller Companion Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Companion Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();
    
    public Integer countAllVisible(CompanionGroup companionGroup);

}// Ende interface
