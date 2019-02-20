/**
 * 
 */
package de.tivsource.page.entity.gallery;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Indexed;

import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.enumeration.GalleryType;

/**
 * 
 * @author Marc Michele
 * 
 */
@Indexed
@Entity
public class Gallery extends PictureItem {

	private Integer orderNumber;

	private GalleryType type = GalleryType.LANDSCAPE;

	@OneToMany(mappedBy = "gallery", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Picture> pictures;

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

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

}// Ende class
