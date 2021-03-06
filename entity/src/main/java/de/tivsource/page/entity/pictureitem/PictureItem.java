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

import org.hibernate.envers.Audited;

import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.picture.Picture;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PictureItem extends NamingItem {

    // TODO: muss entfernt werden sobald das andere Bild benutzt wird
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "picture_uuid")
    private Picture picture;

    @OneToOne(mappedBy = "pictureItem", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "image_uuid")
    private PictureItemImage image;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean pictureOnPage = true;

    @ManyToOne(targetEntity = CSSGroup.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cssGroup_uuid")
    private CSSGroup cssGroup;

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

    /**
     * @return the cssGroup
     */
    public CSSGroup getCssGroup() {
        return cssGroup;
    }

    /**
     * @param cssGroup the cssGroup to set
     */
    public void setCssGroup(CSSGroup cssGroup) {
        this.cssGroup = cssGroup;
    }

}// Ende class
