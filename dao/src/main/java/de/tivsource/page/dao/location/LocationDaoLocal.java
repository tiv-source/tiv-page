/**
 * 
 */
package de.tivsource.page.dao.location;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.location.Location;

/**
 * @author Marc Michele
 *
 */
@Local
public interface LocationDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Location.
     * @param location - Location Objekt das gespeichert werden soll
     */
    public void save(Location location);

    /**
     * Methode zum verändern eines Objektes der Klasse Location.
     * @param location - Location Objekt das verändert werden soll
     */
    public void merge(Location location);

    /**
     * Methode zum löschen eines Objektes der Klasse Location.
     * @param location - zu löschendes Location Objekt
     */
    public void delete(Location location);

    public Location findByUuid(String uuid);

    public Location findByUuidWidthEvents(String uuid);

    /**
     * Methode zum laden einer Liste von Location Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Location Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Location> - Liste von Location Objekten
     */
    public List<Location> findAll(Integer start, Integer max);

    public List<Location> findAll(Integer start, Integer max, String field, String order);

    public List<Location> findAllEventLocation();

    public List<Location> findAllVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Location Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Location Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

    public void removeOpeningHour(Integer index, String location);


}// Ende interface
