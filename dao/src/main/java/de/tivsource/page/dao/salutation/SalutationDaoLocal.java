/**
 * 
 */
package de.tivsource.page.dao.salutation;

import java.util.List;

import de.tivsource.page.common.salutation.Salutation;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface SalutationDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Salutation.
     * @param salutation - Salutation Objekt das gespeichert werden soll
     */
    public void save(Salutation salutation);

    /**
     * Methode zum verändern eines Objektes der Klasse Salutation.
     * @param salutation - Salutation Objekt das verändert werden soll
     */
    public void merge(Salutation salutation);
    
    /**
     * Methode zum löschen eines Objektes der Klasse Salutation.
     * @param salutation - zu löschendes Salutation Objekt
     */
    public void delete(Salutation salutation);

    public Salutation findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Salutation Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Salutation Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Salutation> - Liste von Salutation Objekten
     */
    public List<Salutation> findAll(Integer start, Integer max);

    public List<Salutation> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Salutation Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Salutation Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
