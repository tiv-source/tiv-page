/**
 * 
 */
package de.tivsource.page.entity.gallery;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Indexed;

import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 * 
 */
@Indexed
@Entity
public class Gallery extends NamingItem {

	@Column(unique = true)
	private String technical;

	private Integer orderNumber;

	@OneToMany(mappedBy = "gallery", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Picture> pictures;

	public String getTechnical() {
		return technical;
	}

	public void setTechnical(String technical) {
		this.technical = technical;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

}// Ende class
