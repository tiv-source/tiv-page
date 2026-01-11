/**
 * 
 */
package de.tivsource.page.entity.mail;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class MailAddressLockRequest {

    /**
     * UUID des Objektes der Klasse MailAddressLock, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Hash der Mail-Adresse die gesperrt werden soll.
     */
    @Column(unique=true)
    private String hash;

    /**
     * Wahrheitswert der angibt ob der Antrag schon verarbeitet wurde.
     */
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean used = false;

    /**
     * Datum an dem der Sperrantrag gestellt wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    /**
     * IP-Adresse von der aus der Sperrantrag gestellt wurde.
     */
    private String createdAddress;

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
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the used
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     * @param used the used to set
     */
    public void setUsed(Boolean used) {
        this.used = used;
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

}// Ende class
