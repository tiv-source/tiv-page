package de.tivsource.page.entity.administration;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;

/**
 * 
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class User implements Principal {

    /**
     * UUID des Objektes der Klasse User, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Benutzername der Kasse User, dieses Attribut ist auch eindeutigt das
     * heißt es kann nicht zwei gleiche Benutzernamen geben. Dieses Attribut
     * wird für die Login Funktionalität benutzt.
     */
    @Column(name="username", unique=true)
    private String username;

    /**
     * E-Mail Adresse der Klasse User, dieses Attribut ist auch eindeutigt das
     * heißt es kann nicht zwei verschiedene Benutzer mit der gleichen E-Mail
     * Adresse geben. Diese Adresse wird zur Passwort Wiederherstellung benutzt.
     */
    @Column(name="email", unique=true)
    private String email;

    /**
     * Vorname der Klasse User.
     */
    private String firstname;

    /**
     * Nachname der Klasse User.
     */
    private String lastname;

    /**
     * Passwort der Klasse User.
     */
    private String password;

    /**
     * Rollen die zu dem Benutzerobjekt gehören.
     */
    @ManyToMany(targetEntity = Role.class, cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Role", 
            joinColumns = @JoinColumn(name = "user_uuid"), 
            inverseJoinColumns = @JoinColumn(name = "role_uuid")
            )
    private List<Role> roles;

    /**
     * Erfassungsdatum, Datum/Zeit an dem das Objekt in die Datenbank
     * gespeichert wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Datum an dem das Objekt das letzte mal geändert wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getModifiedAddress() {
        return modifiedAddress;
    }

    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Methode die von der JAAS Implementation genutzt wird, es wird der
     * Benutzername des Objektes zugeliefert.
     */
    @Override
    public String getName() {
        return this.username;
    }

}// Ende class
