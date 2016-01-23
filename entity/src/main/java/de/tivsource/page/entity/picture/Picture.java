/**
 * 
 */
package de.tivsource.page.entity.picture;

import java.util.Date;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.enumeration.UrlType;

/**
 * 
 * Name des Bildes<br/>
 * 
 * 
 * @author Marc Michele
 * 
 */
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
    @Column(name="uuid", unique=true)
    private String uuid;

    /**
     * Die Map mit dem Beschreibung des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "picture", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, PictureDescription> descriptionMap;

    @OneToMany(mappedBy = "picture", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "urlType")
    private Map<UrlType, PictureUrl> pictureUrls;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean visible;

    @ManyToOne(targetEntity = Gallery.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "gallery_uuid")
    private Gallery gallery;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
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

	public Map<UrlType, PictureUrl> getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(Map<UrlType, PictureUrl> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
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

}// Ende class
