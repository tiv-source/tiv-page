/**
 * 
 */
package de.tivsource.page.dao.registration;

import java.util.List;

import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.registration.Registration;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface RegistrationDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Registration.
     * @param registration - Registration Objekt das gespeichert werden soll
     */
    public void save(Registration registration);

    /**
     * Methode zum verändern eines Objektes der Klasse Registration.
     * @param registration - Registration Objekt das verändert werden soll
     */
    public void merge(Registration registration);

    /**
     * Methode zum löschen eines Objektes der Klasse Registration.
     * @param registration - zu löschendes Registration Objekt
     */
    public void delete(Registration registration);

    public Registration findByUuid(String uuid);

    public Registration findUserToken(String userToken);

    /**
     * Methode zum laden einer Liste von Registration Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Registration Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Registration> - Liste von Registration Objekten
     */
    public List<Registration> findAll(Integer start, Integer max);

    public List<Registration> findAll(RegistrationStatus status, Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Registration Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Registration> findAll(Integer start, Integer max, String field, String order);

    public List<Registration> findAll(RegistrationStatus status, Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Registration Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Registration Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAll(RegistrationStatus status);

}// Ende interface
