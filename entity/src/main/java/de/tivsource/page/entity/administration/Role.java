package de.tivsource.page.entity.administration;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.DocumentId;

/**
 * 
 * @author Marc Michele
 *
 */
@Entity
public class Role {

    /**
     * UUID der Klasse Role, diese ID ist einmalig über alle Objekte hinweg und
     * sollte der bevorzugte weg sein auf bestimmte Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

    /**
     * Technischer Name der Rolle.
     */
    private String technical;

    /**
     * Liste der Benutzer die die Rolle inne haben.
     */
    @ManyToMany(
            cascade = { CascadeType.PERSIST }, 
            fetch = FetchType.LAZY, 
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

}// Ende class
