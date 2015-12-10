/**
 * 
 */
package de.tivsource.page.entity.reservation;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.entity.event.Event;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Reservation {

    /**
     * UUID des Objektes der Klasse Reservation, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

    /**
     * Attribute dass das Geschlecht der Person enthält, true wenn weiblich und false wenn männlich.
     */
    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean gender;

    private String firstname;

    private String lastname;

    private String email;

    private String telephone;

    private Integer quantity;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="reservation_time")
    private Date time;

    @Lob
    private String wishes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_uuid")
    private Event event;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean confirmed;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

}// Ende class
