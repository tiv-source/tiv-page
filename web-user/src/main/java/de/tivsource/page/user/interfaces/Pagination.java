/**
 * 
 */
package de.tivsource.page.user.interfaces;

/**
 * @author Marc Michele
 *
 */
public interface Pagination {

    /**
     * Liefert die Nummer der nächsten Seite zurück. 
     * @return Integer - nächste Seite
     */
    public Integer getNext();

    /**
     * Liefert die Nummer der vorherigen Seite zurück.
     * @return Integer - vorherige Seite
     */
    public Integer getPrevious();

    /**
     * Liefert die Nummer der aktuellen Seite zurück.
     * @return Integer - aktuelle Seite
     */
    public Integer getCurrent();

    /**
     * Methode zum setzen der gewünschten Seite.
     * @param page - gewünschte Seite
     */
    public void setPage(Integer page);

}// Ende interface
