/**
 * 
 */
package de.tivsource.page.common.salutation;

import java.util.Date;
import java.util.Map;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import de.tivsource.page.common.salutation.description.SalutationDescription;
import de.tivsource.page.entity.enumeration.Language;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;

/**
 *
 * @author Marc Michele
 * @since 0.1
 */
@Audited
@Entity
@Table(name = "Salutation")
public class Salutation {

    /**
     * UUID des Objektes der Klasse Salutation, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @IndexedEmbedded
    @OneToMany(mappedBy = "salutation", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "locale")
    private Map<Language, SalutationDescription> descriptionMap;

    /**
     * Versionsnummer für die Locking Strategie: optimistic-locking.
     */
    @Version
    private int version;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean visible;

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

	public Map<Language, SalutationDescription> getDescriptionMap() {
		return descriptionMap;
	}

	public void setDescriptionMap(Map<Language, SalutationDescription> descriptionMap) {
		this.descriptionMap = descriptionMap;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
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

    public SalutationDescription getDescriptionObject(Language language) {
    	SalutationDescription result = descriptionMap.get(Language.DE);
    	SalutationDescription tmpResult = descriptionMap.get(Language.DE);
        try {
            tmpResult = descriptionMap.get(language);
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

    public String getLongSalutation(String language) {
        String result = descriptionMap.get(Language.DE).getLongSalutation();
        String tmpResult = descriptionMap.get(Language.DE).getLongSalutation();
        try {
            tmpResult = descriptionMap.get(
                    Language.valueOf(language.toUpperCase())).getLongSalutation();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getLongSalutation(Language language) {
        String result = descriptionMap.get(Language.DE).getLongSalutation();
        String tmpResult = descriptionMap.get(Language.DE).getLongSalutation();

        try {
            tmpResult = descriptionMap.get(language).getLongSalutation();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

}// Ende class
