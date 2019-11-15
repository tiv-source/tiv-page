package de.tivsource.page.entity.administration;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

/**
 * Die Klasse Role dient dazu das Berechtigungskonzept der Anwendung abzubilden.
 *
 * @author Marc Michele
 * @since 0.1
 */
@Audited
@Entity
public class Role implements Principal {

    /**
     * UUID der Klasse Role, diese ID ist einmalig über alle Objekte hinweg und
     * sollte der bevorzugte weg sein auf bestimmtes Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Technischer Name des Objektes der Kasse Role.
     */
    private String technical;

    /**
     * Liste der Benutzer die die Rolle inne haben.
     */
    @ManyToMany(
            cascade = { CascadeType.PERSIST }, 
            fetch = FetchType.EAGER, 
            mappedBy = "roles", 
            targetEntity = User.class
            )
    private List<User> users;

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

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    @Override
    public String getName() {
        return this.technical;
    }

}// Ende class
