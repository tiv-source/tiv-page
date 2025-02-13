package de.tivsource.page.entity.location;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 
 * @author Marc Michele
 *
 */
@Embeddable
public class HourMinute implements Comparable<HourMinute> {

	@Column(length=2)
	private Integer hour;

	@Column(length=2)
	private Integer minute;

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

    @Override
    public int compareTo(HourMinute o) {
        // Wenn die Stunde des übergebenen Objektes kleiner ist
        if(o.hour < hour) {
            return 1;
        }
        // Wenn die Stunde des übergebenen Objektes gößer ist
        if(o.hour > hour) {
            return -1;
        }
        // Wenn die Minuten des übergebenen Objektes kleiner sind
        if(o.minute < minute) {
            return 1;
        }
        // Wenn die Minuten des übergebenen Objektes größer sind
        if(o.minute > minute) {
            return -1;
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
        if (!(obj instanceof HourMinute)) {
            return false;
        }
        // Objekt Casting
        HourMinute other = (HourMinute) obj;
        // Wenn der Inhalt Identisch ist dann ist das Objekt gleich.
        if(this.hour.equals(other.hour) &&
           this.minute.equals(other.minute)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 7 * (hour == null ? 0 : hour.hashCode());
        hash = hash * 7 * (minute == null ? 0 : minute.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        String hourString = hour < 10 ? "0" + hour : hour.toString();
        String minuteString = minute < 10 ? "0" + minute : minute.toString();
        return hourString + ":" + minuteString;
    }
    
}// End class
