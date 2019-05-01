/**
 * 
 */
package de.tivsource.page.entity.pictureitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.common.image.ImageUntouched;

/**
 * @author Marc Michele
 *
 */
@Entity
public class PictureItemImage extends ImageUntouched implements Comparable<ImageUntouched>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 5892598301708511856L;

    /**
     * UUID des PictureItemImage Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pictureitem_uuid")
    private PictureItem pictureItem;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the pictureItem
     */
    public PictureItem getPictureItem() {
        return pictureItem;
    }

    /**
     * @param pictureItem the pictureItem to set
     */
    public void setPictureItem(PictureItem pictureItem) {
        this.pictureItem = pictureItem;
    }

    public int compareTo(PictureItemImage image) {
        return image.getUuid().compareTo(this.uuid) * -1;
    }

}// Ende class
