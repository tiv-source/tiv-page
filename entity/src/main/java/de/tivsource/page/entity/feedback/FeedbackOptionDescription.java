package de.tivsource.page.entity.feedback;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 * 
 */
@Entity
public class FeedbackOptionDescription {

    /**
     * UUID des Objektes der Klasse FeedbackOptionDescription, diese ID ist
     * einmalig über alle Objekte hinweg und sollte der bevorzugte weg sein auf
     * bestimmte Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    private String name;

    @Lob
    private String description;

    private String keywords;

    private String hints;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_uuid")
    private FeedbackOption feedbackOption;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public FeedbackOption getFeedbackOption() {
        return feedbackOption;
    }

    public void setFeedbackOption(FeedbackOption feedbackOption) {
        this.feedbackOption = feedbackOption;
    }

}// Ende class
