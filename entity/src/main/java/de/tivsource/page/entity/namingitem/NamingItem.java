/**
 * Paket mit der Hauptkasse und den direkt benötigten Klassen.
 */
package de.tivsource.page.entity.namingitem;

import java.util.Date;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import de.tivsource.page.entity.enumeration.Language;

/**
 * Die Hauptklasse des Projektes.
 *
 * @author Marc Michele
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class NamingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "UseIdOrGenerate")
    @GenericGenerator(name = "UseIdOrGenerate", strategy = "de.tivsource.page.entity.helper.UseIdOrGenerate", parameters = {
	    @Parameter(name = "initialValue", value = "0"),
	    @Parameter(name = "allocationSize", value = "1") })
    @Column(name = "item_id")
    private Long id;

    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "namingItem", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, Description> descriptionMap;

    @OneToMany(mappedBy = "namingItem", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, ShortUrl> bitlyMap;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean isShorten;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean visible;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Map<Language, Description> getDescriptionMap() {
	return descriptionMap;
    }

    public void setDescriptionMap(Map<Language, Description> descriptionMap) {
	this.descriptionMap = descriptionMap;
    }

    public Map<Language, ShortUrl> getBitlyMap() {
	return bitlyMap;
    }

    public void setBitlyMap(Map<Language, ShortUrl> bitlyMap) {
	this.bitlyMap = bitlyMap;
    }

    public Boolean getIsShorten() {
	return isShorten;
    }

    public void setIsShorten(Boolean isShorten) {
	this.isShorten = isShorten;
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

    public ShortUrl getShortUrl(String language) {
	ShortUrl result = bitlyMap.get(Language.DE);
	ShortUrl tmpResult = bitlyMap.get(Language.DE);

	try {
	    tmpResult = bitlyMap.get(Language.valueOf(language.toUpperCase()));
	} catch (IllegalArgumentException e) {
	    return result;
	} catch (NullPointerException e) {
	    return result;
	}
	return tmpResult;
    }

    public ShortUrl getShortUrl(Language language) {
	ShortUrl result = bitlyMap.get(Language.DE);
	ShortUrl tmpResult = bitlyMap.get(Language.DE);

	try {
	    tmpResult = bitlyMap.get(language);
	} catch (IllegalArgumentException e) {
	    return result;
	} catch (NullPointerException e) {
	    return result;
	}
	return tmpResult;
    }

}// Ende class
