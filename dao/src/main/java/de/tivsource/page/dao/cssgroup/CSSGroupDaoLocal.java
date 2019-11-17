/**
 * 
 */
package de.tivsource.page.dao.cssgroup;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.common.css.CSSGroup;

/**
 * @author Marc Michele
 *
 */
@Local
public interface CSSGroupDaoLocal {

    /**
     * Methode zum Speichern eines Objektes der Klasse CSSGroup.
     * @param cssGroup - CSSGroup Objekt das gespeichert werden soll
     */
    public void save(CSSGroup cssGroup);

    /**
     * Methode zum verändern eines Objektes der Klasse CSSGroup.
     * @param role - CSSGroup Objekt das verändert werden soll
     */
    public void merge(CSSGroup cssGroup);

    /**
     * Methode zum löschen eines Objektes der Klasse CSSGroup.
     * @param role - zu löschendes CSSGroup Objekt
     */
    public void delete(CSSGroup cssGroup);

    public void deleteFiles(CSSGroup cssGroup);
    
    public Boolean hasReferences(String uuid);

    public CSSGroup findByUuid(String uuid);
    
    /**
     * Methode zum laden einer Liste von CSSGroup Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen CSSGroup Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<CSSGroup> - Liste von CSSGroup Objekten
     */
    public List<CSSGroup> findAll(Integer start, Integer max);

    public List<CSSGroup> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller CSSGroup Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der CSSGroup Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
