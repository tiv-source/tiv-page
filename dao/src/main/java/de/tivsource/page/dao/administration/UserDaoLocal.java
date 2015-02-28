/**
 * 
 */
package de.tivsource.page.dao.administration;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.administration.User;

/**
 * @author Marc Michele
 *
 */
@Local
public interface UserDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse User.
     * @param user - User Objekt das gespeichert werden soll
     */
    public void save(User user);

    /**
     * Methode zum verändern eines Objektes der Klasse User.
     * @param user - User Objekt das verändert werden soll
     */
    public void merge(User user);

    /**
     * Methode zum löschen eines Objektes der Klasse User.
     * @param user - zu löschendes User Objekt
     */
    public void delete(User user);

    /**
     * Methode zum laden eines Objektes der Klasse User anhand der
     * Benutzernames.
     * 
     * @param username - Benutzername des Objektes das geladen werden soll.
     * @return User - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public User findByUsername(String username);
    
    /**
     * Methode zum laden einer Liste von User Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen User Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<User> - Liste von User Objekten
     */
    public List<User> findAll(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller User Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der User Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
