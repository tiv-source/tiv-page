/**
 * 
 */
package de.tivsource.page.dao.contentitem;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 *
 */
@Local
public interface ContentItemDaoLocal {

	public ContentItem findByUuid(String uuid);
	
    /**
     * Methode zum laden einer Liste von ContentItem Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen ContentItem Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<ContentItem> - Liste von ContentItem Objekten
     */
    public List<ContentItem> findAll(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller ContentItem Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der ContentItem Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
