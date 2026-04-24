/**
 * 
 */
package de.tivsource.page.entity.location.image;

import java.io.Serializable;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.image.ImageUntouched;
import de.tivsource.page.entity.location.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = OSMImage.TABLE)
public class OSMImage extends ImageUntouched implements Comparable<ImageUntouched>, Serializable {

    /**
     * Name der SQL-Tabelle.
     */
    public static final String TABLE = "Location_OSMImage";

	/**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 446949859824886392L;

    /**
     * UUID des OSMImage Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_uuid")
    private Location location;

    public OSMImage() {
        super();
        uploadPath = "/srv/tiv-page/location-osmimage/";
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
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public int compareTo(OSMImage image) {
        return image.getUuid().compareTo(this.uuid) * -1;
    }

}// Ende class
