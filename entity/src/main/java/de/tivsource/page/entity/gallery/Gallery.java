/**
 * 
 */
package de.tivsource.page.entity.gallery;

import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.enumeration.GalleryType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

/**
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Indexed
@Entity
public class Gallery extends ContentItem {

	private GalleryType type = GalleryType.LANDSCAPE;

	@OneToMany(mappedBy = "gallery", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Picture> pictures;

    public GalleryType getType() {
        return type;
    }

    public void setType(GalleryType type) {
        this.type = type;
    }

    public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("gallery/");
        stringBuffer.append(this.getUuid());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

}// Ende class
