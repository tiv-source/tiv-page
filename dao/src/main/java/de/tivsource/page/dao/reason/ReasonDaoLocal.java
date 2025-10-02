/**
 * 
 */
package de.tivsource.page.dao.reason;

import java.util.List;

import de.tivsource.page.entity.request.Reason;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ReasonDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse Reason.
     * @param reason - Reason Objekt das verändert werden soll
     */
    public void merge(Reason reason);

    /**
     * Methode zum löschen eines Objektes der Klasse Reason.
     * @param reason - zu löschendes Reason Objekt
     */
    public void delete(Reason reason);

    /**
     * Metholde um zu überprüfen ob der angegebene UUID zu einem Reason Objekt
     * gehört.
     * 
     * @param uuid - Die UUID die überprüft werden soll.
     * @return Boolean - true wenn die angegeben UUID zu einem Reason Objekt gehört.
     */
    public Boolean isReasonUuid(String uuid);
    
    /**
     * Methode zum laden eines Objektes der Klasse Reason anhand der
     * Uuid.
     * 
     * @param uuid - UUID des Objektes das geladen werden soll.
     * @return Reason - Objekt das die angegebene UUID besitzt.
     */
    public Reason findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Reason Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Reason Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Reason> - Liste von Reason Objekten
     */
    public List<Reason> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Reason Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Reason> findAll(Integer start, Integer max, String field, String order);

    public List<Reason> findAllVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Reason Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Reason Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
