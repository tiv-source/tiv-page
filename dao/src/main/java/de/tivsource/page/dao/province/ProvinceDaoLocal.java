/**
 * 
 */
package de.tivsource.page.dao.province;

import java.util.List;

import de.tivsource.page.common.province.Province;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ProvinceDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Province.
     * @param province - Province Objekt das gespeichert werden soll
     */
    public void save(Province province);

    /**
     * Methode zum verändern eines Objektes der Klasse Province.
     * @param province - Province Objekt das verändert werden soll
     */
    public void merge(Province province);
    
    /**
     * Methode zum löschen eines Objektes der Klasse Province.
     * @param province - zu löschendes Province Objekt
     */
    public void delete(Province province);

    /**
     * Methode zum laden eines Objektes der Klasse Province anhand des ISO
     * 3166-2 Codes.
     * 
     * @param code - ISO 3166-2 Code anhand dessen das Objekt der Klasse
     *            Province geladen werden soll
     * @return Province - Objekt das den angegebenen ISO 3166-2 Code besitzt.
     */
    public Province findByCode(String code);

    public Province findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Province Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Province Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Province> - Liste von Province Objekten
     */
    public List<Province> findAll(Integer start, Integer max);

    public List<Province> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Province Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Province Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
