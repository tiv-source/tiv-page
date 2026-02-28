/**
 * 
 */
package de.tivsource.page.dao.country;

import java.util.List;

import de.tivsource.page.common.country.Country;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface CountryDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Country.
     * @param country - Country Objekt das gespeichert werden soll
     */
    public void save(Country country);

    /**
     * Methode zum verändern eines Objektes der Klasse Country.
     * @param country - Country Objekt das verändert werden soll
     */
    public void merge(Country country);
    
    /**
     * Methode zum löschen eines Objektes der Klasse Country.
     * @param country - zu löschendes Country Objekt
     */
    public void delete(Country country);

    /**
     * Methode zum laden eines Objektes der Klasse Country anhand der
     * Benutzernames.
     * 
     * @param countryname - Benutzername des Objektes das geladen werden soll.
     * @return Country - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public Country findByAlpha2(String alpha2);

    public Country findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Country Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Country Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Country> - Liste von Country Objekten
     */
    public List<Country> findAll(Integer start, Integer max);

    public List<Country> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Country Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Country Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
