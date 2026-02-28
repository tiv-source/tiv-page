package de.tivsource.page.common.country;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.country.description.CountryDescription;
import de.tivsource.page.common.province.Province;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

/**
 * Die Klasse Country dient dazu Länder zu verwalten, ein Land kann dabei eine
 * ganze Reihe von verschiedenen Eigenschaften haben (zum Beispiel den
 * zweistelligen Sprachcode mach ISO 639).
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Entity
@Table(name = "Country")
public class Country implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6012247317456066129L;

    /**
     * Die UUID des aktuelle Land-Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    /**
     * Der ISO 639-1 (2 Buchstaben Code) der Sprache des Landes. Es kann meherer
     * Länder mit dem gleichen Sprach Code geben siehe zum Beispiel Deutschland,
     * Östereich und Schweiz.
     */
    @Column(name = "language", length=2)
    private String language;

    /**
     * Der 2 Buchstaben (ISO 3166 Alpha 2) Code des Landes.
     */
    @Column(name = "alpha2", unique = true, length=2)
    private String alpha2;

    /**
     * Der 3 Buchstaben (ISO 3166 Alpha 3) Code des Landes.
     */
    @Column(name = "alpha3", unique = true, length=3)
    private String alpha3;

    /**
     * Der Numerische ISO 3166 Code des Landes.
     */
    @Column(name = "isoNumeric", unique = true, length=3)
    private String numeric;

    /**
     * Vorwahl des Landes im Internationalen Format (zum Beispiel für
     * Deutschland +49)
     */
    @Column(name = "areaCode", unique = true, length=7)
    private String areaCode;

    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "country", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "locale")
    private Map<String, CountryDescription> descriptionMap;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @SortNatural
    private SortedSet<Province> provinceSet = new TreeSet<Province>();

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;
    
    public Country() {
        super();
    }

    public Country(String uuid) {
        super();
        this.uuid = uuid;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     *            the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getNumeric() {
        return numeric;
    }

    public void setNumeric(String numeric) {
        this.numeric = numeric;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * @return the descriptionMap
     */
    public Map<String, CountryDescription> getDescriptionMap() {
        return descriptionMap;
    }

    /**
     * @param descriptionMap
     *            the descriptionMap to set
     */
    public void setDescriptionMap(Map<String, CountryDescription> descriptionMap) {
        this.descriptionMap = descriptionMap;
    }

    public SortedSet<Province> getProvinceSet() {
        return provinceSet;
    }

    public void setProvinceSet(SortedSet<Province> provinceSet) {
        this.provinceSet = provinceSet;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedAddress
     */
    public String getModifiedAddress() {
        return modifiedAddress;
    }

    /**
     * @param modifiedAddress the modifiedAddress to set
     */
    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

    /**
     * Methode die den Namen des aktuellen Objektes zurück liefert, es muss dazu
     * die gewünschte Sprache als String übergeben werden (e.g. de/en/fr(etc.).
     * 
     * @param language
     *            - String der den 2 Buchstaben-Code der Sprache enthält.
     * @return String - Der Name des aktuellen Objektes als String.
     */
    public String getName(String locale) {
        String defaultName = descriptionMap.get("de").getName();
        CountryDescription localeName = descriptionMap.get(locale);
        if (localeName != null) {
            defaultName = localeName.getName();
        }
        return defaultName;
    }
    
    /**
     * Methode die das Beschreibungsobjekt das zu dem angegebenen ISO 639-1
     * Sprach Code (2 Buchstaben Code) passt zurückliefert.
     *
     * @param locale - Der ISO 639-1 Sprach Code für das gewünschte
     *            Beschreibungobjekt
     * @return CountryDescription - Das Beschriebungsobjekt welches zu dem
     *         angegebenen ISO 639-1 Sprach Code passt
     */
    public CountryDescription getDescription(String locale) {
        CountryDescription defaultDescription = descriptionMap.get("de");
        CountryDescription localeDescription = descriptionMap.get(locale);
        if (localeDescription != null) {
            defaultDescription = localeDescription;
        }
        return defaultDescription;
    }

    /**
     * Die Methode equals liefert true, wenn es sich um das selbe Objekt im
     * Speicher handelt und wenn das Attribute uuid gleich sind, in allen
     * anderen Fällen liefter diese Methode false.
     *
     * @param other - Das Objekt mit dem verglichen werden soll.
     * @return boolean - True wenn es sich um das gleiche Objekt handelt,
     *         ansonsten false.
     */
    public boolean equals(Object other) {
        if(other instanceof Country) {
            if (this == other) return true;
            if (this.uuid.equals(((Country)other).getUuid())) return true;
        }
        return false;
    }// Ende equals(Object other)

    public int hashCode() {
        return this.uuid.hashCode();
    }

}// Ende class
