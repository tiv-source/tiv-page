package de.tivsource.page.entity.administration;

import java.security.Principal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.DocumentId;

/**
 * Die Klasse Role dient dazu das Berechtigungskonzept der Anwendung abzubilden.
 *
 * @author Marc Michele
 * @since 0.1
 */
@Entity
public class Role implements Principal {

    /**
     * UUID der Klasse Role, diese ID ist einmalig über alle Objekte hinweg und
     * sollte der bevorzugte weg sein auf bestimmtes Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
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

    @Override
    public String getName() {
        return this.technical;
    }

}// Ende class
