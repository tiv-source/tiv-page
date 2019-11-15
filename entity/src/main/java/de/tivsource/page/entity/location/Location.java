/**
 * 
 */
package de.tivsource.page.entity.location;

import java.util.List;
import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.embeddable.Address;
import de.tivsource.page.entity.embeddable.ContactDetails;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Location extends PictureItem {

    @Embedded
    private Address address;

    @Embedded
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval=true)
    @Sort(type=SortType.NATURAL)
    private SortedSet<OpeningHour> openingHours;

    /**
     * True wenn die Location auch in der Liste angezeigt werden soll.
     */
    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean inLocationList = true;

    /**
     * Wenn in der Filiale Veranstaltungen stattfinden können, dann true wenn
     * nicht dann false (Achtung die Location taucht nur im Eventformular auf
     * wenn true).
     */
    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean event;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Event> events;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Vacancy> vacancies;

    /**
     * Longitude der Location.
     */
    private String longitude;

    /**
     * Latitude der Location.
     */
    private String latitude;

    @Column(name="orderNumber")
    private Integer order = 1;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public SortedSet<OpeningHour> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(SortedSet<OpeningHour> openingHours) {
        this.openingHours = openingHours;
    }

    public Boolean getInLocationList() {
        return inLocationList;
    }

    public void setInLocationList(Boolean inLocationList) {
        this.inLocationList = inLocationList;
    }

    public Boolean getEvent() {
        return event;
    }

    public void setEvent(Boolean event) {
        this.event = event;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}// Ende class
