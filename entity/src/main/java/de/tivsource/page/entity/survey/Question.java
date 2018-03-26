/**
 * 
 */
package de.tivsource.page.entity.survey;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;

/**
 * @author Marc Michele
 *
 */
public class Question {

    /**
     * UUID des Objektes der Klasse Question, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

    @Lob
    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Short order;

    private List<QuestionOption> options;

    private Survey survey;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

}// Ende class
