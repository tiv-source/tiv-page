/**
 * 
 */
package de.tivsource.page.entity.picture;

import java.util.Date;
import java.util.Map;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.enumeration.UrlType;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;

/**
 * 
 * Name des Bildes<br/>
 * 
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Indexed
@Entity
public class Picture {

    /**
     * UUID des Objektes der Klasse Picture, diese ID ist einmalig über alle
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
    @OneToMany(mappedBy = "picture", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @MapKey(name = "language")
    private Map<Language, PictureDescription> descriptionMap;

    @OneToMany(mappedBy = "picture", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @MapKey(name = "urlType")
    @Deprecated
    private Map<UrlType, PictureUrl> pictureUrls;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean visible;

    private Integer orderNumber;

    @OneToOne(mappedBy = "picture", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "image_uuid")
    private PictureImage image;

    @ManyToOne(targetEntity = Gallery.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "gallery_uuid")
    private Gallery gallery;

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

	public Map<Language, PictureDescription> getDescriptionMap() {
		return descriptionMap;
	}

	public void setDescriptionMap(Map<Language, PictureDescription> descriptionMap) {
		this.descriptionMap = descriptionMap;
	}

	@Deprecated
	public Map<UrlType, PictureUrl> getPictureUrls() {
		return pictureUrls;
	}

	@Deprecated
	public void setPictureUrls(Map<UrlType, PictureUrl> pictureUrls) {
		this.pictureUrls = pictureUrls;
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

	/**
     * @return the image
     */
    public PictureImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(PictureImage image) {
        this.image = image;
    }

    public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
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

	public String getPictureUrl(String urlType) {
        StringBuilder sb = new StringBuilder("/");
        sb.append("pictures");
        sb.append("/");
        
        if (urlType.equals("THUMBNAIL")) {
            sb.append("THUMBNAIL/");
            sb.append(pictureUrls.get(UrlType.THUMBNAIL).getUrl());
            return sb.toString();
        } else if (urlType.equals("NORMAL")) {
            sb.append("NORMAL/");
            sb.append(pictureUrls.get(UrlType.NORMAL).getUrl());
            return sb.toString();
        } else if (urlType.equals("LARGE")) {
            sb.append("LARGE/");
            sb.append(pictureUrls.get(UrlType.LARGE).getUrl());
            return sb.toString();
        } else {
            sb.append("FULL/");
            sb.append(pictureUrls.get(UrlType.FULL).getUrl());
            return sb.toString();
        }
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

}// Ende class
