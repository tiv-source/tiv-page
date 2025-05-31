package de.tivsource.page.dao.exhibition;

import java.util.List;

import de.tivsource.page.entity.exhibition.Exhibition;
import jakarta.ejb.Local;

/**
 * 
 * @author Marc Michele
 *
 */
@Local
public interface ExhibitionDaoLocal {

    /**
     * Methode zum Speichern eines Objektes der Klasse Exhibition.
     * @param exhibition - Exhibition Objekt das gespeichert werden soll
     */
    public void save(Exhibition exhibition);

    /**
     * Methode zum updaten eines Objektes der Klasse Exhibition.
     * @param exhibition - Exhibition Objekt das verändert werden soll
     */
    public void merge(Exhibition exhibition);

    /**
     * Methode zum löschen eines Objektes der Klasse Exhibition.
     * @param exhibition - zu löschendes Exhibition Objekt
     */
    public void delete(Exhibition exhibition);

    /**
     * Metholde um zu überprüfen ob der angegebene technische Name zu einer
     * Exhibition gehört.
     * 
     * @param technical - Der technische Name der überprüft werden soll.
     * @return Boolean - true wenn es eine Exhibition zu dem technischen Namen gibt.
     */
    public Boolean isExhibitionTechnical(String technical);

    public Boolean hasMenuEntry(String uuid);

    public Boolean hasSubSumption(String uuid);

    /**
     * Gibt das Exhibition-Objekt zurück das den angegebenen technischen Namen
     * hat.
     * 
     * @param technical - technischer Name des Exhibition-Objektes.
     * @return Exhibition - Exhibition-Objekt das den technischen Namen hat.
     */
    public Exhibition findByTechnical(String technical);

    public Exhibition findByUuid(String uuid);

    /**
	 * Gibt alle Exhibition-Objekte zurück die es gibt auch die die noch nicht
	 * veröffentlicht worden sind, ASC sortiert.
	 *
	 * @param start - Startwert ab dem die Objekte ausgegeben werden sollen.
	 * @param max - Anzahl der Objekte die zurückgegeben werden sollen.
	 * @return List<Exhibition> - Liste mit Exhibition-Objekten.
	 */
    public List<Exhibition> findAllAsc(Integer start, Integer max);

    public List<Exhibition> findAll(Integer start, Integer max);

    /**
     * Methode die zum laden einer sortierten Liste von Exhibition-Objekten dient.
     * 
     * @param start - Startwert ab dem die Objekte ausgegeben werden sollen.
     * @param max - Anzahl der Objekte die zurückgegeben werden sollen.
     * @param field - Feld nachdem sortiert werden soll
     * @param order - Die Richtung nach der sortiert werden soll.
     * @return List<Exhibition> - Liste mit den sortierten Exhibition-Objekten.
     */
    public List<Exhibition> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode zu laden der aktuell sichtbaren Exhibition Objekte, dabei werden
     * nur Objekte geladen die nicht vor dem aktuellen Datum liegen. Das heißt
     * Objekte die in der Vergangenheit liegen werden nicht angezeigt.
     * 
     * @param start
     * @param max
     * @return
     */
    public List<Exhibition> findAllVisible(Integer start, Integer max);

    public Integer pageByTechnical(String technical);

    public Integer countAll();

    public Integer countAllVisible();

}// Ende class
