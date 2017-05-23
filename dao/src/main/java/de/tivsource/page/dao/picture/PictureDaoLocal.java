/**
 * 
 */
package de.tivsource.page.dao.picture;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.picture.Picture;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PictureDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Picture.
     * @param picture - Picture Objekt das gespeichert werden soll
     */
    public void save(Picture picture);

    /**
     * Methode zum verändern eines Objektes der Klasse Picture.
     * @param picture - Picture Objekt das verändert werden soll
     */
    public void merge(Picture picture);

    /**
     * Methode zum löschen eines Objektes der Klasse Picture.
     * @param picture - zu löschendes Picture Objekt
     */
    public void delete(Picture picture);

    public void delete(String pictureUrlUuid);

    public Boolean isPicture(String uuid);

    public Boolean hasReferences(String uuid);

    public Picture findByUuid(String uuid);

    /**
	 * Methode die alle Picture Objekt zurückliefert die zu der angegebenen
	 * Galler uuid gehören.
	 * 
	 * @param uuid - UUID des Gallery Objektes zu dem die Picture Objekte gehören
	 *            sollen.
	 * @return List<Picture> - Liste mit den Picture Objekten die zu dem Gallery
	 *         Obejket gehören von dem die angegebenen UUID stammt
	 */
    public List<Picture> findAll(String uuid);

    /**
     * Methode zum laden einer Liste von Picture Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Picture Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Picture> - Liste von Picture Objekten
     */
    public List<Picture> findAll(Integer start, Integer max);

    public List<Picture> findAll(Integer start, Integer max, String field, String order);
    
    public List<Picture> findAll(Integer start, Integer max, String galleryUuid, String field, String order);

    public Picture findPreviousPicture(Integer start, String galleryUuid);
    public Picture findCurrentPicture(Integer start, String galleryUuid);
    public Picture findNextPicture(Integer start, String galleryUuid);
    

    /**
     * Methode die die Anzahl aller Picture Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Picture Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllInGallery(String galleryUuid);

}// Ende interface
