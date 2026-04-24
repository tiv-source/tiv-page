/**
 * 
 */
package de.tivsource.page.entity.location;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.image.OSMImage;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.entity.vacancy.Vacancy;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "location", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "location_uuid")
    private OSMImage osmImage;

    @OneToOne(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private Address address;

    @OneToOne(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
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

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean showCourses = false;

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

    public OSMImage getOsmImage() {
        return osmImage;
    }

    public void setOsmImage(OSMImage osmImage) {
        this.osmImage = osmImage;
    }

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

    public Boolean getShowCourses() {
        return showCourses;
    }

    public void setShowCourses(Boolean showCourses) {
        this.showCourses = showCourses;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 21 *  hash * TABLE.hashCode() * inLocationList.hashCode();
        // Die Bildung des Hashcodes ging nicht Aufgrund eines Fehlers in der
        // Hibernate Version 6.1.7 den gabs es schon mal => HHH-3441 und HHH-4450
        // https://hibernate.atlassian.net/browse/HHH-4450
        //hash = 21 *  hash + (getName("de") == null ? 0 : getName("de").hashCode());
        //hash = 21 *  hash + (getDescription("de") == null ? 0 : getDescription("de").hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        // Selbst Test
        if (this == o) return true;
        // NULL Test
        if (o == null) return false;
        // type check and cast
        if (getClass() != o.getClass()) return false;
        PictureItem pictureItem = (PictureItem) o;
        // field comparison
        return Objects.equals(getUuid(), pictureItem.getUuid());
    }

}// Ende class
