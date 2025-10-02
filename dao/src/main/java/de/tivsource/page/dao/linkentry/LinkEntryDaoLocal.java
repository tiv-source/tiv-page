/**
 * 
 */
package de.tivsource.page.dao.linkentry;

import java.util.List;

import de.tivsource.page.common.menuentry.LinkEntry;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface LinkEntryDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse LinkEntry.
     * @param linkEntry - LinkEntry Objekt das gespeichert werden soll
     */
    public void save(LinkEntry linkEntry);

    /**
     * Methode zum verändern eines Objektes der Klasse LinkEntry.
     * @param linkEntry - LinkEntry Objekt das verändert werden soll
     */
    public void merge(LinkEntry linkEntry);

    /**
     * Methode zum löschen eines Objektes der Klasse LinkEntry.
     * @param linkEntry - zu löschendes LinkEntry Objekt
     */
    public void delete(LinkEntry linkEntry);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (LinkEntry) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (LinkEntry) ist.
     */
    public Boolean isLinkEntryUrl(String urlName);
    
    /**
     * Methode zum laden eines Objektes der Klasse LinkEntry anhand der
     * Benutzernames.
     * 
     * @param technical - Benutzername des Objektes das geladen werden soll.
     * @return LinkEntry - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public LinkEntry findByTechnical(String technical);

    public LinkEntry findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von LinkEntry Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen LinkEntry Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<LinkEntry> - Liste von LinkEntry Objekten
     */
    public List<LinkEntry> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von LinkEntry Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<LinkEntry> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller LinkEntry Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der LinkEntry Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
