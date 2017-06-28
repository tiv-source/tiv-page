/**
 * 
 */
package de.tivsource.page.entity.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Event extends PictureItem {

    private BigDecimal price;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date beginning;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ending;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_uuid")
    private Location location;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean reservation;
    
    @OneToMany(mappedBy = "event", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Reservation> reservations;

    private Integer maxReservations;

    private Integer maxPersons;
    
    private Integer piwikGoal;
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) {
        this.ending = ending;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

	public Integer getPiwikGoal() {
		return piwikGoal;
	}

	public void setPiwikGoal(Integer piwikGoal) {
		this.piwikGoal = piwikGoal;
	}

	public Integer getMaxReservations() {
		return maxReservations;
	}

	public void setMaxReservations(Integer maxReservations) {
		this.maxReservations = maxReservations;
	}

	public Integer getMaxPersons() {
		return maxPersons;
	}

	public void setMaxPersons(Integer maxPersons) {
		this.maxPersons = maxPersons;
	}

}// Ende class
