/**
 * 
 */
package de.tivsource.page.common.css;

import java.io.File;
import java.util.Date;
import java.util.SortedSet;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.common.file.FileActions;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "CSSFile")
public class CSSFile implements Comparable<CSSFile> {

    /**
     * Statischer Logger der Klasse CSSFile für die Ausgabe mit log4j.
     */
    private static final Logger logger = LogManager.getLogger(CSSFile.class);

    /**
     * UUID der Klasse CSSFile, diese ID ist einmalig über alle Objekte hinweg
     * und sollte der bevorzugte weg sein auf bestimmtes Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length = 42)
    private String uuid;

    private String name;

    private String description;

    private Integer priority;

    private String path;

    /**
     * Datei, die aus einem Formular hochgeladen wurde, die Datei ist temporär
     * vorhanden und wird nur zum erstellen benötigt.
     */
    @Transient
    private File uploadFile;

    /**
     * Gruppen zu dem die CSS Datei gehört.
     */
    @ManyToMany(targetEntity = CSSGroup.class, cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "CSSFile_CSSGroup", 
            joinColumns = @JoinColumn(name = "cssFile_uuid"), 
            inverseJoinColumns = @JoinColumn(name = "cssGroup_uuid")
            )
    @SortNatural
    private SortedSet<CSSGroup> groups;

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
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the uploadFile
     */
    public File getUploadFile() {
        return uploadFile;
    }

    /**
     * @param uploadFile
     *            the uploadFile to set
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * @return the groups
     */
    public SortedSet<CSSGroup> getGroups() {
        return groups;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(SortedSet<CSSGroup> groups) {
        this.groups = groups;
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

    public void generate() {
        String filePath = "/srv/tiv-page/cssfile/";
        // Generiere UUID für den Dateinamen
        String cssSaveName = UUID.randomUUID().toString();
        try {
            // Datei die erstellt werden soll
            File fileToCreate = new File(filePath, cssSaveName + ".css");
            logger.debug("Absoluter Pfad der neuen CSS-Datei : "
                    + fileToCreate.getAbsolutePath());

            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fileToCreate.exists()) {
                FileActions.savePictureFile(this.getUploadFile(), fileToCreate);
            }// Ende if
        } // Ende try
        catch (Exception e) {
            logger.error(e.getMessage());
        }// Ende Catch
        setPath(filePath + cssSaveName + ".css");
    }// Ende generate()

    @Override
    public int compareTo(CSSFile o) {
        if (o.priority < this.priority) {
            return 1;
        } else if (o.priority > this.priority) {
            return -1;
        } else {
            return o.uuid.compareTo(this.uuid);
        }
    }// Ende compareTo(CSSFile o)

}// Ende class
