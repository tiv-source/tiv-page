/**
 * 
 */
package de.tivsource.page.entity.survey;

import java.util.Date;
import java.util.List;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;

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

    @Column(columnDefinition="LONGTEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Short order;

    private List<QuestionOption> options;

    private Survey survey;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

}// Ende class
