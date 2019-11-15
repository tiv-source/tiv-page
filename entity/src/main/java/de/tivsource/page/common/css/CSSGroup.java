/**
 * 
 */
package de.tivsource.page.common.css;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.enumeration.Language;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "CSSGroup")
public class CSSGroup implements Comparable<CSSGroup> {

    /**
     * UUID der Klasse CSSGroup, diese ID ist einmalig über alle Objekte hinweg
     * und sollte der bevorzugte weg sein auf bestimmtes Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length = 42)
    private String uuid;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Language language;

    /**
     * Set mit den CSS-Dateien die zu der Gruppe gehören.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, mappedBy = "groups", targetEntity = CSSFile.class)
    @SortNatural
    private SortedSet<CSSFile> files;

    @OneToMany(mappedBy = "cssGroup", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<ContentItem> contentItems;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

    /**
     * Versionsnummer für die Locking Strategie: optimistic-locking.
     */
    @Version
    private Integer version;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     *            the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * @return the files
     */
    public SortedSet<CSSFile> getFiles() {
        return files;
    }

    /**
     * @param files
     *            the files to set
     */
    public void setFiles(SortedSet<CSSFile> files) {
        this.files = files;
    }

    /**
     * @return the contentItems
     */
    public List<ContentItem> getContentItems() {
        return contentItems;
    }

    /**
     * @param contentItems
     *            the contentItems to set
     */
    public void setContentItems(List<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified
     *            the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy
     *            the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedAddress
     */
    public String getModifiedAddress() {
        return modifiedAddress;
    }

    /**
     * @param modifiedAddress
     *            the modifiedAddress to set
     */
    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(CSSGroup o) {
        return o.name.compareTo(this.name);
    }

}// Ende class
