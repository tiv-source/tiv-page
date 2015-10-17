/**
 * 
 */
package de.tivsource.page.entity.location;

import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Location extends NamingItem {

    @Embedded
    private Address address;

    @Embedded
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "location", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @Sort(type=SortType.NATURAL)
    private SortedSet<OpeningHour> openingHours;

    /**
     * Longitude der Location.
     */
    private String longitude;

    /**
     * Latitude der Location.
     */
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
