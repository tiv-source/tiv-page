/**
 * 
 */
package de.tivsource.page.dao.request;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.request.Request;

/**
 * @author Marc Michele
 *
 */
@Local
public interface RequestDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse Request.
     * @param request - Request Objekt das verändert werden soll
     */
    public void merge(Request request);

    /**
     * Methode zum löschen eines Objektes der Klasse Request.
     * @param request - zu löschendes Request Objekt
     */
    public void delete(Request request);
    
    /**
     * Methode zum laden eines Objektes der Klasse Request anhand der
     * Uuid.
     * 
     * @param uuid - UUID des Objektes das geladen werden soll.
     * @return Request - Objekt das die angegebene UUID besitzt.
     */
    public Request findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Request Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Request Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Request> - Liste von Request Objekten
     */
    public List<Request> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Request Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Request> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Request Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Request Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
