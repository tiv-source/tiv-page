package de.tivsource.page.entity.exhibition;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.enumeration.CityType;
import de.tivsource.page.entity.enumeration.CountryType;
import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * Austellungen
 * 
 * Atribute: id, name, beschreibung, beginn, ende, ort, zeitpunkt, 
 * 
 * 
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Exhibition extends NamingItem {

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date moment;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date start;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date end;

	@Enumerated(EnumType.STRING)
	private CityType place;
	
	@Enumerated(EnumType.STRING)
	private CountryType country;

	private String thumbnail;
	
	public Exhibition() {
		super();
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public CityType getPlace() {
		return place;
	}

	public void setPlace(CityType place) {
		this.place = place;
	}

	public CountryType getCountry() {
		return country;
	}

	public void setCountry(CountryType country) {
		this.country = country;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}// Ende class
