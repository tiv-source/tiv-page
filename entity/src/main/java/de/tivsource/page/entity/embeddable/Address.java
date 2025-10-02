package de.tivsource.page.entity.embeddable;

import jakarta.persistence.Embeddable;

/**
 * 
 * @author Marc Michele
 *
 */
@Embeddable
public class Address {

    /**
     * Strasse
     */
    private String street;

    /**
     * Postleitzahl
     */
    private String zip;

    /**
     * Stadt
     */
    private String city;

    /**
     * Land
     */
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}// Ende class
