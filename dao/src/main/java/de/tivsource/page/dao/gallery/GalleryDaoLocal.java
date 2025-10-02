/**
 * 
 */
package de.tivsource.page.dao.gallery;

import java.util.List;

import de.tivsource.page.entity.gallery.Gallery;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface GalleryDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Gallery.
     * @param gallery - Gallery Objekt das gespeichert werden soll
     */
    public void save(Gallery gallery);

    /**
     * Methode zum verändern eines Objektes der Klasse Gallery.
     * @param gallery - Gallery Objekt das verändert werden soll
     */
    public void merge(Gallery gallery);

    /**
     * Methode zum löschen eines Objektes der Klasse Gallery.
     * @param gallery - zu löschendes Gallery Objekt
     */
    public void delete(Gallery gallery);

    public Boolean isGallery(String uuid);

    public Boolean isGalleryTechnical(String technical);
    
    public Boolean hasReferences(String uuid);

    public Boolean hasMenuEntry(String uuid);

    public Boolean hasSubSumption(String uuid);

    public Gallery findByUuid(String uuid);

    public Gallery findByTechnical(String technical);

    /**
     * Methode zum laden einer Liste von Gallery Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Gallery Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Gallery> - Liste von Gallery Objekten
     */
    public List<Gallery> findAll(Integer start, Integer max);

    public List<Gallery> findAll(Integer start, Integer max, String field, String order);

    public List<Gallery> findAllVisible(Integer start, Integer max);
    
    /**
     * Methode die die Anzahl aller Gallery Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Gallery Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
