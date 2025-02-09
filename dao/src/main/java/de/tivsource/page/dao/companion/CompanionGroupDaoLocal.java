/**
 * 
 */
package de.tivsource.page.dao.companion;

import java.util.List;

import jakarta.ejb.Local;

import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * @author Marc Michele
 *
 */
@Local
public interface CompanionGroupDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse CompanionGroup.
     * @param companionGroup - CompanionGroup Objekt das gespeichert werden soll
     */
    public void save(CompanionGroup companionGroup);

    /**
     * Methode zum verändern eines Objektes der Klasse CompanionGroup.
     * @param companionGroup - CompanionGroup Objekt das verändert werden soll
     */
    public void merge(CompanionGroup companionGroup);

    /**
     * Methode zum löschen eines Objektes der Klasse CompanionGroup.
     * @param companionGroup - zu löschendes CompanionGroup Objekt
     */
    public void delete(CompanionGroup companionGroup);

    public Boolean isCompanionGroup(String uuid);

    public Boolean isCompanionGroupTechnical(String technical);

    public Boolean hasReferences(String uuid);

    public CompanionGroup findByUuid(String uuid);

    public CompanionGroup findByTechnical(String technical);

    /**
     * Methode zum laden einer Liste von CompanionGroup Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen CompanionGroup Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<CompanionGroup> - Liste von CompanionGroup Objekten
     */
    public List<CompanionGroup> findAll(Integer start, Integer max);

    public List<CompanionGroup> findAll(Integer start, Integer max, String field, String order);

    public List<CompanionGroup> findAllVisible(Integer start, Integer max);
    
    /**
     * Methode die die Anzahl aller CompanionGroup Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der CompanionGroup Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
