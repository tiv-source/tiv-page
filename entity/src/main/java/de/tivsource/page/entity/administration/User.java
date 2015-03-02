package de.tivsource.page.entity.administration;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;

/**
 * 
 * @author Marc Michele
 *
 */
@Entity
public class User implements Principal {

    /**
     * UUID des Objektes der Klasse User, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
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
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date added;

    /**
     * Datum an dem das Objekt das letzte mal geändert wurde.
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    /**
     * Ip-Adresse von der der Benutzer das letzte mal angemeldet war.
     */
    private String ip;

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

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
