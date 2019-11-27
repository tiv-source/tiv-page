/**
 * 
 */
package de.tivsource.page.common.captcha;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.common.captcha.image.CaptchaImage;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Captcha {

    /**
     * UUID des Objektes der Klasse Captcha, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    @OneToOne(mappedBy = "captcha", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private CaptchaImage image;

    private String content;

    /**
     * Erfassungsdatum, Datum/Zeit an dem das Objekt in die Datenbank
     * gespeichert wurde.
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Datum an dem das Objekt das letzte mal geändert wurde.
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    /**
     * Feld in dem gespeichert ist welche Benutzer (Username) das Objekt als
     * letztes verändert hat.
     */
    private String modifiedBy;

    /**
     * Ip-Adresse von der das Objekt das letzte mal geändert wurde.
     */
    private String modifiedAddress;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the image
     */
    public CaptchaImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(CaptchaImage image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

}// Ende class
