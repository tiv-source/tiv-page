/**
 * 
 */
package de.tivsource.page.dao.feedback;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.feedback.Feedback;

/**
 * @author Marc Michele
 *
 */
@Local
public interface FeedbackDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse Feedback.
     * @param feedback - Feedback Objekt das verändert werden soll
     */
    public void merge(Feedback feedback);

    /**
     * Methode zum löschen eines Objektes der Klasse Feedback.
     * @param feedback - zu löschendes Feedback Objekt
     */
    public void delete(Feedback feedback);

    public Feedback findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Feedback Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Feedback Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Feedback> - Liste von Feedback Objekten
     */
    public List<Feedback> findAll(Integer start, Integer max);

    public List<Feedback> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Feedback Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Feedback Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
