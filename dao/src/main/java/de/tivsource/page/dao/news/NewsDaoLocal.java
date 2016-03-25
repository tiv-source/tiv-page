/**
 * 
 */
package de.tivsource.page.dao.news;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.news.News;

/**
 * @author Marc Michele
 *
 */
@Local
public interface NewsDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse News.
     * @param news - News Objekt das gespeichert werden soll
     */
    public void save(News news);

    /**
     * Methode zum verändern eines Objektes der Klasse News.
     * @param news - News Objekt das verändert werden soll
     */
    public void merge(News news);

    /**
     * Methode zum löschen eines Objektes der Klasse News.
     * @param news - zu löschendes News Objekt
     */
    public void delete(News news);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Seite (News) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Seite (News) ist.
     */
    public Boolean isNewsUrl(String urlName);

    public News findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von News Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen News Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<News> - Liste von News Objekten
     */
    public List<News> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von News Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<News> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller News Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der News Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
