/**
 * 
 */
package de.tivsource.page.entity.survey;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

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
