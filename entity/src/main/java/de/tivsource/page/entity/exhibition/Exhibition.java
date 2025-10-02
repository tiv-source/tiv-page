package de.tivsource.page.entity.exhibition;

import java.util.Date;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.enumeration.CityType;
import de.tivsource.page.entity.enumeration.CountryType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;

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
public class Exhibition extends ContentItem {

	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date moment;

	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date start;

	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date end;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date visibleFrom;

	@Enumerated(EnumType.STRING)
	private CityType place;
	
	@Enumerated(EnumType.STRING)
	private CountryType country;

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

	/**
     * @return the visibleFrom
     */
    public Date getVisibleFrom() {
        return visibleFrom;
    }

    /**
     * @param visibleFrom the visibleFrom to set
     */
    public void setVisibleFrom(Date visibleFrom) {
        this.visibleFrom = visibleFrom;
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

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("exhibition/");
        stringBuffer.append(this.getTechnical());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

}// Ende class
