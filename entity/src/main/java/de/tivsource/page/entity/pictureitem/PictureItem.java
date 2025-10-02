/**
 * 
 */
package de.tivsource.page.entity.pictureitem;

import java.util.Objects;

import org.hibernate.envers.Audited;

import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.entity.namingitem.NamingItem;
import de.tivsource.page.entity.picture.Picture;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

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
    @Deprecated
    private Picture picture;

    @OneToOne(mappedBy = "pictureItem", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "image_uuid")
    private PictureItemImage image;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean pictureOnPage = true;

    @ManyToOne(targetEntity = CSSGroup.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cssGroup_uuid")
    private CSSGroup cssGroup;

    @Deprecated
    public Picture getPicture() {
        return picture;
    }

    @Deprecated
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 21 *  hash + getUuid().hashCode();
        // Die Bildung des Hashcodes ging nicht Aufgrund eines Fehlers in der
        // Hibernate Version 6.1.7 den gabs es schon mal => HHH-3441 und HHH-4450
        // https://hibernate.atlassian.net/browse/HHH-4450
        //hash = 21 *  hash + (getName("de") == null ? 0 : getName("de").hashCode());
        //hash = 21 *  hash + (getDescription("de") == null ? 0 : getDescription("de").hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        // Selbst Test
        if (this == o) return true;
        // NULL Test
        if (o == null) return false;
        // type check and cast
        if (getClass() != o.getClass()) return false;
        PictureItem pictureItem = (PictureItem) o;
        // field comparison
        return Objects.equals(getUuid(), pictureItem.getUuid());
    }

}// Ende class
