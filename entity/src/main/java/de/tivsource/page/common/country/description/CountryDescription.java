package de.tivsource.page.common.country.description;

import java.io.Serializable;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.country.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Die Klasse CountryDescription dient dazu die Beschreibung und die
 * Überschrift in den verschiedenen Sprachen zu verwalten.
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Entity
@Table(name = "Country_Description")
public class CountryDescription implements Comparable<CountryDescription>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 5694270989747464671L;

    /**
     * UUID der Länderbeschreibung.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    /**
     * Sprache für die die Landesbeschreibung gültig ist (2 Letter Code nach
     * ISO 639-1)
     */
    private String locale;

    /**
     * Name des Landes.
     */
    private String name;

    /**
     * Land zu der die Beschreibung gehört.
     */
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public CountryDescription() {
        super();
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int compareTo(CountryDescription countryDescription) {
        int returnValue = countryDescription.getName().toLowerCase().compareTo(this.name.toLowerCase()) * -1;
        if (returnValue == 0) {
            return (countryDescription.getUuid()).compareTo(this.uuid) * -1;
        } else {
            return returnValue;
        }
    }// Ende compareTo(CategoryDescription categoryDescription)
}// Ende Class
