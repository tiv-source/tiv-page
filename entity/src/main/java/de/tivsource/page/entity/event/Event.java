/**
 * 
 */
package de.tivsource.page.entity.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Event extends NamingItem {

    private BigDecimal price;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="event_date")
    private Date date;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean supper;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_uuid")
    private Location location;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean reservation;

    @ManyToMany(targetEntity = Reservation.class, cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Event_Reservation", 
            joinColumns = @JoinColumn(name = "event_uuid"), 
            inverseJoinColumns = @JoinColumn(name = "reservation_uuid")
            )
    private List<Reservation> reservations;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSupper() {
        return supper;
    }

    public void setSupper(Boolean supper) {
        this.supper = supper;
    }

    public Boolean getReservation() {
        return reservation;
    }

    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}// Ende class
