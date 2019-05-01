/**
 * 
 */
package de.tivsource.page.entity.pictureitem;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.picture.Picture;

/**
 * @author Marc Michele
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PictureItem extends NamingItem {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "picture_uuid")
    private Picture picture;

    @OneToOne(mappedBy = "pictureItem", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "image_uuid")
    private PictureItemImage image;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean pictureOnPage = true;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    /**
     * @return the image
     */
    public PictureItemImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(PictureItemImage image) {
        this.image = image;
    }

    public Boolean getPictureOnPage() {
        return pictureOnPage;
    }

    public void setPictureOnPage(Boolean pictureOnPage) {
        this.pictureOnPage = pictureOnPage;
    }

}// Ende class
