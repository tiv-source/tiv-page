/**
 * 
 */
package de.tivsource.page.dao.event;

import java.util.List;

import de.tivsource.page.entity.event.Event;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface EventDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Event.
     * @param event - Event Objekt das gespeichert werden soll
     */
    public void save(Event event);

    /**
     * Methode zum verändern eines Objektes der Klasse Event.
     * @param event - Event Objekt das verändert werden soll
     */
    public void merge(Event event);

    /**
     * Methode zum löschen eines Objektes der Klasse Event.
     * @param event - zu löschendes Event Objekt
     */
    public void delete(Event event);

    public Boolean isEvent(String uuid);

    public Event findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Event Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Event Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Event> - Liste von Event Objekten
     */
    public List<Event> findAll(Integer start, Integer max);

    public List<Event> findAll(Integer start, Integer max, String field, String order);

    /**
	 * Methode die eine Liste von Event Objekten zurück liefert, die noch aktiv
	 * sind, dass heisst die noch nicht beendet wurden, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Event Objekte.
     *
	 * @param start
	 * @param max
	 * @return
	 */
    public List<Event> findAllActive(Integer start, Integer max);

    public List<Event> findAllActive(Integer start, Integer max, String field, String order);

    /**
     * Methode zum laden einer Liste von Event Objekten die zu einer bestimmten
     * Location gehören.
     * 
     * @param uuid - Uuid der Location
     * @param start - Startwert der Liste
     * @param max - Maximal Anzahl an Objekten in der Liste
     * @return List<Event> - Liste mit den Event Objekten
     */
    public List<Event> findAll(String uuid, Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Event Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Event Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllActive();

    /**
     * Methode die die Anzahl aller Event Objekte die zu einer bestimmten
     * Location gehören zurück liefert.
     *
     * @return Integer - Anzahl der Event Objekte die zu der Location gehören.
     *         
     */
    public Integer countAll(String uuid);

}// Ende interface
