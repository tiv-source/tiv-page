/**
 * 
 */
package de.tivsource.page.dao.reservation;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ReservationDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Reservation.
     * @param reservation - Reservation Objekt das gespeichert werden soll
     */
    public void save(Reservation reservation);

    /**
     * Methode zum verändern eines Objektes der Klasse Reservation.
     * @param reservation - Reservation Objekt das verändert werden soll
     */
    public void merge(Reservation reservation);

    /**
     * Methode zum löschen eines Objektes der Klasse Reservation.
     * @param reservation - zu löschendes Reservation Objekt
     */
    public void delete(Reservation reservation);

    public Reservation findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Reservation Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Reservation Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Reservation> - Liste von Reservation Objekten
     */
    public List<Reservation> findAll(Integer start, Integer max);

    public List<Reservation> findAll(Integer start, Integer max, String field, String order);

    public List<Reservation> findAll(Event event, Integer start, Integer max);

    public List<Reservation> findAll(Event event, Integer start, Integer max, String field, String order);

    public List<Reservation> confirmationQueue(Integer start, Integer max);

    public List<Reservation> confirmationQueue(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Reservation Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Reservation Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAll(Event event);

    public Integer countConfirmationQueue();

    /**
     * 
     * @param uuid - UUID des Event Objektes für die die Anzahl ermittelt werden soll.
     * @return Integer - Anzahl der Personen die für dieses Event reserviert haben. 
     */
    public Integer countQuantity(String uuid);

}// Ende interface
