package de.tivsource.page.common.province;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.province.description.ProvinceDescription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

/**
 * Die Klasse Province.
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Entity
@Table(name = "Province")
public class Province implements Comparable<Province>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6824157146650582930L;

    /**
     * Statischer Logger der Klasse Country für die Ausgabe mit log4j. Es gibt
     * nur Ausgaben mit dem Log-Level "TRACE".
     */
    private static final Logger logger = LogManager.getLogger(Province.class);

    /**
     * Die UUID des Province Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "province", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "locale")
    private Map<String, ProvinceDescription> descriptionMap;

    /**
     * Der Alphanumerische (ISO 3166-2) Code der Province.
     */
    @Column(name = "code", unique = true, length=35)
    private String code;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

    public Province() {
        super();
    }

    public Province(String uuid) {
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
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the descriptionMap
     */
    public Map<String, ProvinceDescription> getDescriptionMap() {
        return descriptionMap;
    }

    /**
     * @param descriptionMap the descriptionMap to set
     */
    public void setDescriptionMap(Map<String, ProvinceDescription> descriptionMap) {
        this.descriptionMap = descriptionMap;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        ProvinceDescription localeName = descriptionMap.get(locale);
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
     * @return ProvinceDescription - Das Beschriebungsobjekt welches zu dem
     *         angegebenen ISO 639-1 Sprach Code passt
     */
    public ProvinceDescription getDescription(String locale) {
        ProvinceDescription defaultDescription = descriptionMap.get("de");
        ProvinceDescription localeDescription = descriptionMap.get(locale);
        if (localeDescription != null) {
            defaultDescription = localeDescription;
        }
        return defaultDescription;
    }

    // Dokumentation
    // TODO: Methode ausarbeiten
    public int compareTo(Province province) {
        logger.trace("compareTo(Province province) aufgerufen.");
        int returnValue = 0;
        if (returnValue == 0) {
            return (province.getUuid()).compareTo(this.uuid) * -1;
        } else {
            return returnValue;
        }
    }// Ende compareTo(Country country)

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
        if(other instanceof Province) {
            if (this == other) return true;
            if (this.uuid.equals(((Province)other).getUuid())) return true;
        }
        return false;
    }// Ende equals(Object other)
    
}// Ende class
