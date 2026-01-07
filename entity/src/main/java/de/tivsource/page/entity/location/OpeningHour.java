package de.tivsource.page.entity.location;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.enumeration.Weekday;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class OpeningHour implements Comparable<OpeningHour> {

    /**
     * UUID des Objektes der Klasse OpeningHour, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

	@Enumerated(EnumType.STRING)
	private Weekday weekday;

	@Column(length=2)
	private Integer openHour;

	@Column(length=2)
	private Integer openMinute;

	@Column(length=2)
	private Integer closeHour;

	@Column(length=2)
	private Integer closeMinute;

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

	public Integer getOpenHour() {
        return openHour;
    }

    public void setOpenHour(Integer openHour) {
        this.openHour = openHour;
    }

    public Integer getOpenMinute() {
        return openMinute;
    }

    public void setOpenMinute(Integer openMinute) {
        this.openMinute = openMinute;
    }

    public Integer getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(Integer closeHour) {
        this.closeHour = closeHour;
    }

    public Integer getCloseMinute() {
        return closeMinute;
    }

    public void setCloseMinute(Integer closeMinute) {
        this.closeMinute = closeMinute;
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
		if(openHour.compareTo(o.getOpenHour()) < 0) {
			return -1;
		}
		// Wenn die Öffnungzeiten nach den des Objektes liegen.
		if(openHour.compareTo(o.getOpenHour()) > 0) {
			return 1;
		}
		// Wenn die Schließzeiten vor den des Objektes liegen.
		if(closeHour.compareTo(o.getCloseHour()) < 0) {
			return -1;
		}
		// Wenn die Schließzeiten nach den des Objektes liegen.
		if(closeHour.compareTo(o.getCloseHour()) > 0) {
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
		// Wenn die UUID Identisch ist dann ist das Objekt gleich.
		if (this.uuid.equals(other.uuid)){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 7 * (weekday == null ? 0 : weekday.hashCode());
		hash = hash * 7 * (openHour == null ? 0 : openHour.hashCode());
		hash = hash * 7 * (closeHour == null ? 0 : closeHour.hashCode());
		return hash;
	}

	public String openHourToString() {
        String hourString = openHour < 10 ? "0" + openHour : openHour.toString();
        String minuteString = openMinute < 10 ? "0" + openMinute : openMinute.toString();
        return hourString + ":" + minuteString;
	}

    public String closeHourToString() {
        String hourString = closeHour < 10 ? "0" + closeHour : closeHour.toString();
        String minuteString = closeMinute < 10 ? "0" + closeMinute : closeMinute.toString();
        return hourString + ":" + minuteString;
	}

}// End class
