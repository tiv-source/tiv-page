/**
 * Paket mit der Hauptkasse und den direkt benötigten Klassen.
 */
package de.tivsource.page.entity.namingitem;

import java.util.Date;
import java.util.Map;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.entity.enumeration.Language;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;

/**
 * Die Hauptklasse des Projektes.
 *
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = NamingItem.DISCRIMINATOR_COLUMN, discriminatorType = DiscriminatorType.STRING, length = 255)
public class NamingItem {

    public static final String DISCRIMINATOR_COLUMN = "classname";
    
    /**
     * UUID des Objektes der Klasse NamingItem, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    @Column(updatable = false, insertable = false)
    protected String classname;
    
    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "namingItem", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, Description> descriptionMap;

    @Column(unique = true)
    private String technical;

    /**
     * Versionsnummer für die Locking Strategie: optimistic-locking.
     */
    @Version
    private int version;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean visible;

    private Integer orderNumber = 1;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<Language, Description> getDescriptionMap() {
        return descriptionMap;
    }

    public void setDescriptionMap(Map<Language, Description> descriptionMap) {
        this.descriptionMap = descriptionMap;
    }

    /**
     * @return the technical
     */
    public String getTechnical() {
        return technical;
    }

    /**
     * @param technical the technical to set
     */
    public void setTechnical(String technical) {
        this.technical = technical;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedAddress() {
        return modifiedAddress;
    }

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
    public String getName(String language) {
        String result = descriptionMap.get(Language.DE).getName();
        String tmpResult = descriptionMap.get(Language.DE).getName();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getName();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getShortName(String language) {
        String result = descriptionMap.get(Language.DE).getName();
        String tmpResult = descriptionMap.get(Language.DE).getName();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getName();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }

        if (tmpResult.length() > 35) {
            return tmpResult.substring(0, 35) + "...";
        } else {
            return tmpResult;
        }
    }

    public String getName(Language language) {
        String result = descriptionMap.get(Language.DE).getName();
        String tmpResult = descriptionMap.get(Language.DE).getName();

        try {
            tmpResult = descriptionMap.get(language).getName();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getDescription(String language) {
        String result = descriptionMap.get(Language.DE).getDescription();
        String tmpResult = descriptionMap.get(Language.DE).getDescription();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getDescription();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public Description getDescriptionObject(Language language) {
        Description result = descriptionMap.get(Language.DE);
        Description tmpResult = descriptionMap.get(Language.DE);
        try {
            tmpResult = descriptionMap.get(language);
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getShortDescription(String language) {
        String result = descriptionMap.get(Language.DE).getDescription();
        String tmpResult = descriptionMap.get(Language.DE).getDescription();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getDescription();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }

        if (tmpResult.length() > 266) {
            return tmpResult.substring(0, 266) + "...";
        } else {
            return tmpResult;
        }
    }

    public String getOverviewDescription(String language) {
        String result = descriptionMap.get(Language.DE).getDescription();
        String tmpResult = descriptionMap.get(Language.DE).getDescription();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getDescription();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }

        if (tmpResult.length() > 588) {
            return tmpResult.substring(0, 588) + "...";
        } else {
            return tmpResult;
        }
    }

    public String getDescription(Language language) {
        String result = descriptionMap.get(Language.DE).getDescription();
        String tmpResult = descriptionMap.get(Language.DE).getDescription();

        try {
            tmpResult = descriptionMap.get(language).getDescription();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getKeywords(String language) {
        String result = descriptionMap.get(Language.DE).getKeywords();
        String tmpResult = descriptionMap.get(Language.DE).getKeywords();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getKeywords();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getKeywords(Language language) {
        String result = descriptionMap.get(Language.DE).getKeywords();
        String tmpResult = descriptionMap.get(Language.DE).getKeywords();

        try {
            tmpResult = descriptionMap.get(language).getKeywords();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    @Override
    public boolean equals(Object o) {
        // Selbsttest
        if (this == o) {
            return true;
        }
        // Auf NULL prüfen
        if (o == null) {
            return false;
        }
        // Überprüfen ob das Objekt der Klasse angehört
        if (getClass() != o.getClass()) {
            return false;
        }
        NamingItem namingItem = (NamingItem) o;
        if(this.uuid.equals(namingItem.uuid)) {
            return true;
        }
        return false;
    }// Ende equals(Object o)

}// Ende class
