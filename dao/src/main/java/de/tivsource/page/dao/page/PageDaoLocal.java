/**
 * 
 */
package de.tivsource.page.dao.page;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.page.Page;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PageDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Page.
     * @param page - Page Objekt das gespeichert werden soll
     */
    public void save(Page page);

    /**
     * Methode zum verändern eines Objektes der Klasse Page.
     * @param page - Page Objekt das verändert werden soll
     */
    public void merge(Page page);

    /**
     * Methode zum löschen eines Objektes der Klasse Page.
     * @param page - zu löschendes Page Objekt
     */
    public void delete(Page page);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (Page) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (Page) ist.
     */
    public Boolean isPageUrl(String urlName);
    
    /**
     * Methode zum laden eines Objektes der Klasse Page anhand der
     * Benutzernames.
     * 
     * @param pagename - Benutzername des Objektes das geladen werden soll.
     * @return Page - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public Page findByTechnical(String pagename);

    public Page findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Page Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Page Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Page> - Liste von Page Objekten
     */
    public List<Page> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Page Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Page> findAll(Integer start, Integer max, String field, String order);

    public List<Page> findAllTopNavigation();

    public List<Page> findAllNavigation();

    public List<Page> findAllBottomNavigation();

    public List<Page> findAllResponsiveNavigation();

    /**
     * Methode die die Anzahl aller Page Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Page Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
