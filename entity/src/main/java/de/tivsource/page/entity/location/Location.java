/**
 * 
 */
package de.tivsource.page.entity.location;

import java.util.List;
import java.util.SortedSet;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.embeddable.Address;
import de.tivsource.page.entity.embeddable.ContactDetails;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.entity.vacancy.Vacancy;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = Location.TABLE)
public class Location extends PictureItem {

    public static final String TABLE = "Location";

    @Embedded
    @AttributeOverrides( {
        @AttributeOverride(name="street", column = @Column(table = TABLE, name="street") ),
        @AttributeOverride(name="zip", column = @Column(table = TABLE, name="zip") ),
        @AttributeOverride(name="city", column = @Column(table = TABLE, name="city") ),
        @AttributeOverride(name="country", column = @Column(table = TABLE, name="country") )
    } )
    private Address address;

    @Embedded
    @AttributeOverrides( {
        @AttributeOverride(name="mobile", column = @Column(table = TABLE, name="mobile") ),
        @AttributeOverride(name="telephone", column = @Column(table = TABLE, name="telephone") ),
        @AttributeOverride(name="fax", column = @Column(table = TABLE, name="fax") ),
        @AttributeOverride(name="email", column = @Column(table = TABLE, name="email") ),
        @AttributeOverride(name="homepage", column = @Column(table = TABLE, name="homepage") )
    } )
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    @SortNatural
    private SortedSet<OpeningHour> openingHours;

    /**
     * True wenn die Location auch in der Liste angezeigt werden soll.
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean inLocationList = true;

    /**
     * Wenn in der Filiale Veranstaltungen stattfinden können, dann true wenn
     * nicht dann false (Achtung die Location taucht nur im Eventformular auf
     * wenn true).
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean event;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Event> events;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Vacancy> vacancies;

    /**
     * Longitude der Location.
     */
    @Column(table = TABLE)
    private String longitude;

    /**
     * Latitude der Location.
     */
    @Column(table = TABLE)
    private String latitude;

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

}// Ende class
