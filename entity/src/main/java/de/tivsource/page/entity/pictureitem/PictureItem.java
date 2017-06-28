/**
 * 
 */
package de.tivsource.page.entity.pictureitem;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean pictureOnPage = true;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Boolean getPictureOnPage() {
        return pictureOnPage;
    }

    public void setPictureOnPage(Boolean pictureOnPage) {
        this.pictureOnPage = pictureOnPage;
    }

}// Ende class
