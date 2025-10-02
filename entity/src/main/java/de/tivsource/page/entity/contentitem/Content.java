package de.tivsource.page.entity.contentitem;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.entity.enumeration.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;

/**
 * 
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Content {

    /**
     * UUID des Objektes der Klasse Content, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    @Column(columnDefinition="LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_uuid")
    private ContentItem contentItem;

    /**
     * Erfassungsdatum, Datum/Zeit an dem das Objekt in die Datenbank
     * gespeichert wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ContentItem getContentItem() {
        return contentItem;
    }

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

}// Ende class
