/**
 * 
 */
package de.tivsource.page.dao.contententry;

import java.util.List;

import jakarta.ejb.Local;

import de.tivsource.page.common.menuentry.ContentEntry;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ContentEntryDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse ContentEntry.
     * @param contentEntry - ContentEntry Objekt das gespeichert werden soll
     */
    public void save(ContentEntry contentEntry);

    /**
     * Methode zum verändern eines Objektes der Klasse ContentEntry.
     * @param contentEntry - ContentEntry Objekt das verändert werden soll
     */
    public void merge(ContentEntry contentEntry);

    /**
     * Methode zum löschen eines Objektes der Klasse ContentEntry.
     * @param contentEntry - zu löschendes ContentEntry Objekt
     */
    public void delete(ContentEntry contentEntry);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (ContentEntry) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (ContentEntry) ist.
     */
    public Boolean isContentEntryUrl(String urlName);
    
    /**
     * Methode zum laden eines Objektes der Klasse ContentEntry anhand der
     * Benutzernames.
     * 
     * @param contentEntryname - Benutzername des Objektes das geladen werden soll.
     * @return ContentEntry - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public ContentEntry findByTechnical(String technical);

    public ContentEntry findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von ContentEntry Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen ContentEntry Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<ContentEntry> - Liste von ContentEntry Objekten
     */
    public List<ContentEntry> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von ContentEntry Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<ContentEntry> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller ContentEntry Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der ContentEntry Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
