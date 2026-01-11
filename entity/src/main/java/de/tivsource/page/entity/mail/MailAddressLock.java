/**
 * 
 */
package de.tivsource.page.entity.mail;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class MailAddressLock {

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
     * Hash der Mail-Adresse die gesperrt ist. Dieser Hash dient zur Prüfung ob
     * die Mail-Adresse in den Verteiler aufgenommen werden darf.
     */
    @Column(unique=true)
    private String hash;

    /**
     * Datum an dem der Hash erstellt wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Benutzername des Operators der die Mail-Adresse gesprerrt hat, sollte das
     * durch einen Sperr-Antrag geschehen sein dann steht dort "Internet-Seite".
     */
    private String createdBy = "Internet-Seite";

    /**
     * IP-Adresse von der aus der Hash erstellt wurde.
     */
    private String createdAddress;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAddress() {
        return createdAddress;
    }

    public void setCreatedAddress(String createdAddress) {
        this.createdAddress = createdAddress;
    }

}// Ende class
