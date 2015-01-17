package de.tivsource.page.entity.contentitem;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

import de.tivsource.page.entity.enumeration.Language;

@Entity
public class Content {

    /**
     * Datenbank-ID des Item-Objektes.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "content_generator")
    @TableGenerator(name = "content_generator", initialValue = 0, allocationSize = 1)
    @Column(name = "content_id")
    private Long id;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ContentItem contentItem;

    /**
     * Erfassungsdatum, Datum/Zeit an dem das Objekt in die Datenbank
     * gespeichert wurde.
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
