/**
 * 
 */
package de.tivsource.page.common.css;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.pictureitem.PictureItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "CSSGroup")
public class CSSGroup implements Comparable<CSSGroup> {

    /**
     * Statischer Logger der Klasse CSSGroup für die Ausgabe mit log4j.
     */
    private static final Logger logger = LogManager.getLogger(CSSFile.class);

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
    private Set<PictureItem> pictureItems;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
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
     * @return the pictureItems
     */
    public Set<PictureItem> getPictureItems() {
        return pictureItems;
    }

    /**
     * @param pictureItems the pictureItems to set
     */
    public void setPictureItems(Set<PictureItem> pictureItems) {
        this.pictureItems = pictureItems;
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
        logger.trace("compareTo(CSSGroup o) aufgerufen.");
        return o.name.compareTo(this.name);
    }

    @Override
    public int hashCode() {
        logger.trace("hashCode() aufgerufen.");
        int hash = 7;
        hash = 21 *  hash + uuid.hashCode();
        hash = 21 *  hash + (name == null ? 0 : name.hashCode());
        hash = 21 *  hash + (description == null ? 0 : description.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        // Selbst Test
        if (this == o) return true;
        // NULL Test
        if (o == null) return false;
        // type check and cast
        if (getClass() != o.getClass()) return false;
        CSSGroup cssGroup = (CSSGroup) o;
        // field comparison
        return Objects.equals(uuid, cssGroup.getUuid());
    }

}// Ende class
