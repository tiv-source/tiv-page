/**
 * 
 */
package de.tivsource.page.dao.property;

import java.util.List;

import de.tivsource.page.entity.property.Property;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PropertyDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Property.
     * @param property - Property Objekt das gespeichert werden soll
     */
    public void save(Property property);

    /**
     * Methode zum verändern eines Objektes der Klasse Property.
     * @param property - Property Objekt das verändert werden soll
     */
    public void merge(Property property);

    /**
     * Methode zum löschen eines Objektes der Klasse Property.
     * @param property - zu löschendes Property Objekt
     */
    public void delete(Property property);

    public Property findByKey(String key);

    /**
     * Methode zum laden einer Liste von Property Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Property Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Property> - Liste von Property Objekten
     */
    public List<Property> findAll(Integer start, Integer max);

    public List<Property> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Property Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Property Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
