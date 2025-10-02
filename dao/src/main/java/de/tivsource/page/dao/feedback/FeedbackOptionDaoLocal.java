/**
 * 
 */
package de.tivsource.page.dao.feedback;

import java.util.List;

import de.tivsource.page.entity.feedback.FeedbackOption;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface FeedbackOptionDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse FeedbackOption.
     * @param feedbackOption - FeedbackOption Objekt das verändert werden soll
     */
    public void merge(FeedbackOption feedbackOption);

    /**
     * Methode zum löschen eines Objektes der Klasse FeedbackOption.
     * @param feedbackOption - zu löschendes FeedbackOption Objekt
     */
    public void delete(FeedbackOption feedbackOption);

    public Boolean isFeedbackOption(String uuid);
    
    public Boolean hasReferences(String uuid);

    public FeedbackOption findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von FeedbackOption Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen FeedbackOption Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<FeedbackOption> - Liste von FeedbackOption Objekten
     */
    public List<FeedbackOption> findAll(Integer start, Integer max);

    public List<FeedbackOption> findAll(Integer start, Integer max, String field, String order);

    public List<FeedbackOption> findAllVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller FeedbackOption Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der FeedbackOption Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
