/**
 * 
 */
package de.tivsource.page.entity.event;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.entity.reservation.Reservation;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Event extends PictureItem {

    private BigDecimal price;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date beginning;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date ending;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date deadline;

    /**
     * True wenn die Zeit auswählbar seien soll.
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean timeSelection = true;

    /**
     * Location Objekt zu dem das Event Objekt gehört.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_uuid")
    private Location location;

    /**
     * True wenn für diese Event Objekt noch eine Reservierung möglich ist.
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean reservation;

    @OneToMany(mappedBy = "event", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Reservation> reservations;

    /**
     * Maximale Anzahl an Reservierungen die für diese Event Objekt möglich sind.
     */
    private Integer maxReservations;

    /**
     * Maximal Anzahl an Personen für die Reserviert werden kann.
     */
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

    public Boolean getTimeSelection() {
        return timeSelection;
    }

    public void setTimeSelection(Boolean timeSelection) {
        this.timeSelection = timeSelection;
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

	public String getFormatedDate() {
	    DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
	    return dateFormat.format(beginning);
	}

}// Ende class
