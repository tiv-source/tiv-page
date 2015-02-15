/**
 * 
 */
package de.tivsource.page.dao.administration;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.administration.Role;

/**
 * @author Marc Michele
 *
 */
@Local
public interface RoleDaoLocal {

    /**
     * Methode zum Speichern eines Objektes der Klasse Role.
     * @param role - Role Objekt das gespeichert werden soll
     */
    public void save(Role role);

    /**
     * Methode zum verändern eines Objektes der Klasse Role.
     * @param role - Role Objekt das verändert werden soll
     */
    public void merge(Role role);

    /**
     * Methode zum löschen eines Objektes der Klasse Role.
     * @param role - zu löschendes Role Objekt
     */
    public void delete(Role role);

    /**
     * Methode zum laden eines Objektes der Klasse Role anhand des technischen
     * Names.
     *
     * @param technical - technischer Name des Objektes das geladen werden soll.
     * @return Role - Das geladene Role Objekt.
     */
    public Role findByTechnical(String technical);

    /**
     * Methode zum laden einer Liste von Role Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Role Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Role> - Liste von Role Objekten
     */
    public List<Role> findAll(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Role Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Role Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
