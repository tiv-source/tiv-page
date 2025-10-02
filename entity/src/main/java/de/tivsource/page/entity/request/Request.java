/**
 * 
 */
package de.tivsource.page.entity.request;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.file.FileActions;
import de.tivsource.page.entity.location.Location;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.Transient;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Request {

    private static final Logger logger = LogManager.getLogger(Request.class);

    /**
     * Datei-Pfad unter dem das Bild abgelegt wird.
     */
    protected static String uploadPath = "/srv/tiv-page/request/";

    /**
     * UUID des Objektes der Klasse Request, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Attribut das angibt wie jemand angesprochen werden möchte, es kann aus
     * den folgenden Geschlechtern ausgewählt werden: M = Male (Männlich), F =
     * Female (Weiblich), O = Other (Anderes).
     * 
     * Dabei gilt das die Anrede bei M = Herr x yz, F = Frau x yz und O = x yz
     * benutzt wird.
     */
    private Character gender;

    private String firstname;

    private String lastname;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date birthday;

    private String mail;

    @ManyToOne(targetEntity = Reason.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "reason_uuid")
    private Reason reason;

    private String proofOfAuthority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_uuid")
    private Location location;

    private String comment;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean privacy;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean confirmed;

    private String confirmedAddress;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date confirmedDate;

    private String confirmedBy;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    private String createdAddress;

    /**
     * Datei, die aus einem Formular hochgeladen wird.
     */
    @Transient
    private File uploadFile;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the gender
     */
    public Character getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the reason
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    /**
     * @return the proofOfAuthority
     */
    public String getProofOfAuthority() {
        return proofOfAuthority;
    }

    /**
     * @param proofOfAuthority the proofOfAuthority to set
     */
    public void setProofOfAuthority(String proofOfAuthority) {
        this.proofOfAuthority = proofOfAuthority;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the privacy
     */
    public Boolean getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy the privacy to set
     */
    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    /**
     * @return the confirmed
     */
    public Boolean getConfirmed() {
        return confirmed;
    }

    /**
     * @param confirmed the confirmed to set
     */
    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * @return the confirmedAddress
     */
    public String getConfirmedAddress() {
        return confirmedAddress;
    }

    /**
     * @param confirmedAddress the confirmedAddress to set
     */
    public void setConfirmedAddress(String confirmedAddress) {
        this.confirmedAddress = confirmedAddress;
    }

    /**
     * @return the confirmedDate
     */
    public Date getConfirmedDate() {
        return confirmedDate;
    }

    /**
     * @param confirmedDate the confirmedDate to set
     */
    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    /**
     * @return the confirmedBy
     */
    public String getConfirmedBy() {
        return confirmedBy;
    }

    /**
     * @param confirmedBy the confirmedBy to set
     */
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the createdAddress
     */
    public String getCreatedAddress() {
        return createdAddress;
    }

    /**
     * @param createdAddress the createdAddress to set
     */
    public void setCreatedAddress(String createdAddress) {
        this.createdAddress = createdAddress;
    }

    /**
     * @return the uploadFile
     */
    public File getUploadFile() {
        return uploadFile;
    }

    /**
     * @param uploadFile the uploadFile to set
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public FileInputStream getProofOfAuthorityFileInputStream() {
        try {
            return new FileInputStream(new File(this.proofOfAuthority));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void generate() {

        // Generiere UUID für den Dateinamen
        String pictureSaveName = UUID.randomUUID().toString();

        try {
            // Erstelle Content Analyse Toolkit
            Tika tika = new Tika();
            String mimeType = tika.detect(uploadFile);
            String type = mimeType.split("/")[1];
            // Datei die erstellt werden soll
            File fileToCreate = new File(uploadPath, pictureSaveName + ".org." + type);
            this.proofOfAuthority = uploadPath + pictureSaveName + ".org." + type;
            logger.debug("Absoluter Pfad der neuen Picture-Datei : "
                    + fileToCreate.getAbsolutePath());

            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fileToCreate.exists()) {
                FileActions.savePictureFile(this.getUploadFile(), fileToCreate);
            }// Ende if
        } // Ende try
        catch (Exception e) {
            logger.error(e.getMessage());
        }// Ende Catch
    }        
}// Ende class
