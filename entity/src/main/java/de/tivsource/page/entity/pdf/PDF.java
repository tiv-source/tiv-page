/**
 * 
 */
package de.tivsource.page.entity.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.util.FileUtils;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.common.file.FileActions;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.enumeration.PDFType;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class PDF implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 433552151887902170L;

    /**
     * Statischer Logger der Klasse Image für die Ausgabe mit log4j. Es gibt nur
     * Ausgaben mit dem Log-Level "TRACE".
     */
    private static final Logger logger = LogManager.getLogger(PDF.class);

    /**
     * Lokaler Dateisystem Pfad zu den hochgeladenen Bildern.
     */
    private static final String uploadPath = "/srv/tiv-page/pdf/";

    /**
     * UUID des Objektes der Klasse PDF, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "pdf", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, PDFDescription> descriptionMap;

    private PDFType pdfType;

    private String edition;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateOfPublication;

    private Integer downloadCounter = 0;

    /**
     * Lokaler Pfad der PDF-Datei.
     */
    private String file;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean visible;

    private Integer orderNumber = 1;

    @OneToOne(mappedBy = "pdf", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "image_uuid")
    private PDFImage image;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

    /**
     * Datei, die aus einem Formular hochgeladen wurde. Diese Datei existiert
     * nur nach der Erstellung oder nach eine Änderung.
     */
    @Transient
    private File uploadFile;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<Language, PDFDescription> getDescriptionMap() {
        return descriptionMap;
    }

    public void setDescriptionMap(Map<Language, PDFDescription> descriptionMap) {
        this.descriptionMap = descriptionMap;
    }

    public PDFType getPdfType() {
        return pdfType;
    }

    public void setPdfType(PDFType pdfType) {
        this.pdfType = pdfType;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Integer getDownloadCounter() {
        return downloadCounter;
    }

    public void setDownloadCounter(Integer downloadCounter) {
        this.downloadCounter = downloadCounter;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PDFImage getImage() {
        return image;
    }

    public void setImage(PDFImage image) {
        this.image = image;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedAddress() {
        return modifiedAddress;
    }

    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void generate() {

        // Generiere UUID für den Dateinamen
        String pdfSaveName = UUID.randomUUID().toString();

        try {
            // Datei die erstellt werden soll
            File fileToCreate = new File(uploadPath, pdfSaveName + ".pdf");
            logger.debug("Absoluter Pfad der neuen PDF-Datei : "
                    + fileToCreate.getAbsolutePath());

            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fileToCreate.exists()) {
                FileActions.savePictureFile(this.getUploadFile(), fileToCreate);
            }// Ende if
        } // Ende try
        catch (Exception e) {
            logger.error(e.getMessage());
        }// Ende Catch

        file = uploadPath + pdfSaveName + ".pdf";

    }// Ende generate()

    public void delete() {
        File pdfFile = new File(file);
        if (pdfFile.exists()) {
            FileUtils.delete(pdfFile);
        }// Ende if
    }// Ende delete()

    public FileInputStream getFileInputStream() {
        try {
            return new FileInputStream(new File(this.file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }// Ende getFileInputStream()

    /**
     * Methode die den Namen des aktuellen Objektes zurück liefert, es muss dazu
     * die gewünschte Sprache als String übergeben werden (e.g. de/en/fr(etc.).
     * 
     * @param language - String der den 2 Buchstaben-Code der Sprache enthält.
     * @return String - Der Name des aktuellen Objektes als String.
     */
    public String getName(String language) {
        String result = descriptionMap.get(Language.DE).getName();
        String tmpResult = descriptionMap.get(Language.DE).getName();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getName();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getName(Language language) {
        String result = descriptionMap.get(Language.DE).getName();
        String tmpResult = descriptionMap.get(Language.DE).getName();

        try {
            tmpResult = descriptionMap.get(language).getName();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }
    
    public String getDescription(String language) {
        String result = descriptionMap.get(Language.DE).getDescription();
        String tmpResult = descriptionMap.get(Language.DE).getDescription();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getDescription();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getDescription(Language language) {
        String result = descriptionMap.get(Language.DE).getDescription();
        String tmpResult = descriptionMap.get(Language.DE).getDescription();
        try {
            tmpResult = descriptionMap.get(language).getDescription();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public PDFDescription getDescriptionObject(Language language) {
        PDFDescription result = descriptionMap.get(Language.DE);
        PDFDescription tmpResult = descriptionMap.get(Language.DE);
        try {
            tmpResult = descriptionMap.get(language);
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getKeywords(String language) {
        String result = descriptionMap.get(Language.DE).getKeywords();
        String tmpResult = descriptionMap.get(Language.DE).getKeywords();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getKeywords();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getKeywords(Language language) {
        String result = descriptionMap.get(Language.DE).getKeywords();
        String tmpResult = descriptionMap.get(Language.DE).getKeywords();

        try {
            tmpResult = descriptionMap.get(language).getKeywords();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

}// Ende class
