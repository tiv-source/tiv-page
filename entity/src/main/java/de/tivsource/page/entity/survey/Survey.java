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
public class Survey {

    /**
     * UUID des Objektes der Klasse Survey, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private SurveyType type;

    private List<Question> questions;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

}// Ende class
