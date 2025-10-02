/**
 * 
 */
package de.tivsource.page.dao.cssfile;

import java.util.List;

import de.tivsource.page.common.css.CSSFile;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface CSSFileDaoLocal {

    /**
     * Methode zum Speichern eines Objektes der Klasse CSSFile.
     * @param cssFile - CSSFile Objekt das gespeichert werden soll
     */
    public void save(CSSFile cssFile);

    /**
     * Methode zum verändern eines Objektes der Klasse CSSFile.
     * @param role - CSSFile Objekt das verändert werden soll
     */
    public void merge(CSSFile cssFile);

    /**
     * Methode zum löschen eines Objektes der Klasse CSSFile.
     * @param role - zu löschendes CSSFile Objekt
     */
    public void delete(CSSFile cssFile);

    public Boolean hasReferences(String uuid);
    
    public CSSFile findByUuid(String uuid);
    
    /**
     * Methode zum laden einer Liste von CSSFile Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen CSSFile Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<CSSFile> - Liste von CSSFile Objekten
     */
    public List<CSSFile> findAll(Integer start, Integer max);

    public List<CSSFile> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller CSSFile Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der CSSFile Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
