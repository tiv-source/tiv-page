/**
 * 
 */
package de.tivsource.page.entity.pictureitem;

import java.io.Serializable;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.image.ImageUntouched;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * @author Marc Michele
 *
 */
@Audited
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
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pictureitem_uuid")
    private PictureItem pictureItem;

    public PictureItemImage() {
        super();
        uploadPath = "/srv/tiv-page/pictureItem/";
    }

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
