/**
 * 
 */
package de.tivsource.page.entity.picture;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.enumeration.UrlType;


/**
 * @author marc
 *
 */
@Entity
public class PictureUrl {

    /**
     * UUID des Objektes der Klasse PictureUrl, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

	@Enumerated(EnumType.STRING)
	private UrlType urlType;

	private String url;

	@ManyToOne(targetEntity=Picture.class, fetch=FetchType.LAZY)
    @JoinColumn(name="picture_uuid")
	private Picture picture;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public UrlType getUrlType() {
		return urlType;
	}

	public void setUrlType(UrlType urlType) {
		this.urlType = urlType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}// Ende class
