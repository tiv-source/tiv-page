package de.tivsource.page.entity.location;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.enumeration.Weekday;

/**
 * 
 * @author Marc Michele
 *
 */
@Entity
public class OpeningHour implements Comparable<OpeningHour> {

    /**
     * UUID des Objektes der Klasse OpeningHour, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

	@Enumerated(EnumType.STRING)
	private Weekday weekday;

	@Embedded
	@AttributeOverrides({
	  @AttributeOverride(name="hour", column=@Column(name="open_hour")),
	  @AttributeOverride(name="minute", column=@Column(name="open_minute"))
	})
	private HourMinute open;

	@Embedded
    @AttributeOverrides({
        @AttributeOverride(name="hour", column=@Column(name="close_hour")),
        @AttributeOverride(name="minute", column=@Column(name="close_minute"))
      })
	private HourMinute close;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_uuid")
	private Location location;

	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Weekday getWeekday() {
		return weekday;
	}

	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	public HourMinute getOpen() {
		return open;
	}

	public void setOpen(HourMinute open) {
		this.open = open;
	}

	public HourMinute getClose() {
		return close;
	}

	public void setClose(HourMinute close) {
		this.close = close;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public int compareTo(OpeningHour o) {
		// Wenn die beiden Datenbank-IDs gleich sind und nicht null !
		if(o.uuid != null && uuid != null && o.uuid == uuid) {
			return 0;
		}
		// Wenn der übergebene Wochtag vor dem des Objektes liegt.
		if(o.weekday.ordinal() < weekday.ordinal()) {
			return 1;
		}
		// Wenn der übergebene Wochtag nach dem des Objektes liegt.
		if(o.weekday.ordinal() > weekday.ordinal()) {
			return -1;
		}
		// Wenn die Öffnungzeiten vor den des Objektes liegen.
		if(open.compareTo(open) < 0) {
			return -1;
		}
		// Wenn die Öffnungzeiten nach den des Objektes liegen.
		if(open.compareTo(open) > 0) {
			return 1;
		}
		// Wenn die Schließzeiten vor den des Objektes liegen.
		if(close.compareTo(close) < 0) {
			return -1;
		}
		// Wenn die Schließzeiten nach den des Objektes liegen.
		if(close.compareTo(close) > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// Wenn die Objekte gleich sind.
		if(this == obj) {
			return true;
		}
		// Wenn das Objekt null ist.
		if(obj == null) {
			return false;
		}
		// Wenn es sich nicht um die gleich Klasse handelt.
		if (!(obj instanceof OpeningHour)) {
			return false;
		}
		// Objekt Casting
		OpeningHour other = (OpeningHour) obj;
		// Wenn der Inhalt Identisch ist dann ist das Objekt gleich.
		if (this.weekday.equals(other.weekday) &&
				this.open.equals(other.open) &&
				this.close.equals(other.close)){
			return true;
		}
		if((this.weekday == null && other.weekday != null) ||
				(this.open == null && other.open != null) ||
				(this.close == null && other.close != null)){
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 7 * (weekday == null ? 0 : weekday.hashCode());
		hash = hash * 7 * (open == null ? 0 : open.hashCode());
		hash = hash * 7 * (close == null ? 0 : close.hashCode());
		return hash;
	}

}// End class
