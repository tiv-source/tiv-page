/**
 * 
 */
package de.tivsource.page.entity.survey;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.search.annotations.DocumentId;

/**
 * @author Marc Michele
 *
 */
public class QuestionOption {

    /**
     * UUID des Objektes der Klasse QuestionOption, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

    private Short order;

    private String text;

    private String value;

    private Question question;

}// Ende class
