/**
 * 
 */
package de.tivsource.page.dao.contentitem;

import java.util.List;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ContentItemDaoLocal {

	public ContentItem findByUuid(String uuid);

    /**
     * Methode die eine Liste mit ContentItem Objekte liefert außer dem Objekt
     * mit der angegeben UUID, es muss ein Startwert und die Anzahl der zu
     * ladenenden Objekte angegeben werden.
     * 
     * @param start
     * @param max
     * @return
     */
    public List<ContentItem> findAllVisible(String uuid, Integer start, Integer max);

    /**
     * Methode zum laden einer Liste von ContentItem Objekten, die noch nicht
     * einem ContentEntry Objekt zugeordnet sind, es muss dabei ein Startwert
     * angegeben werden und die Anzahl der zu ladenen ContentItem Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<ContentItem> - Liste von ContentItem Objekten
     */
    public List<ContentItem> findAllUnassigned(Integer start, Integer max);

    public Integer countAllVisible(String uuid);

    /**
     * Methode die die Anzahl aller ContentItem Objekte die noch nicht
     * einem ContentEntry Objekt zugeordnet sind und sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der ContentItem Objekte die sich in der Datenbank befinden.
     */
    public Integer countAllUnassigned();

}// Ende interface
