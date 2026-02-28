package de.tivsource.page.common.province.description;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.province.Province;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Die Klasse CategoryDescription dient dazu die Beschreibung und die
 * Überschrift in den verschiedenen Sprachen zu verwalten.
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Entity
@Table(name = "Province_Description")
public class ProvinceDescription implements Comparable<ProvinceDescription>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6725404093141192332L;

    /**
     * Statischer Logger der Klasse CategoryDescription für die Ausgabe mit
     * log4j. Es gibt nur Ausgaben mit dem Log-Level "TRACE".
     */
    private static final Logger logger = LogManager.getLogger(ProvinceDescription.class);

    /**
     * UUID der Länderbeschreibung.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    /**
     * Sprache für die die Kategorie-Beschreibung gültig ist (2 Letter Code nach
     * ISO 639-1)
     */
    private String locale;

    /**
     * Name des Kategorie.
     */
    private String name;

    /**
     * Kategorie zu der die Beschreibung gehört.
     */
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    public ProvinceDescription() {
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

    /**
     * @return the province
     */
    public Province getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public int compareTo(ProvinceDescription countryDescription) {
        logger.trace("compareTo(CategoryDescription categoryDescription) aufgerufen.");
        int returnValue = countryDescription.getName().toLowerCase().compareTo(this.name.toLowerCase()) * -1;
        if (returnValue == 0) {
            return (countryDescription.getUuid()).compareTo(this.uuid) * -1;
        } else {
            return returnValue;
        }
    }// Ende compareTo(CategoryDescription categoryDescription)
}// Ende Class
