/**
 * 
 */
package de.tivsource.page.entity.slider;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Slider {

    /**
     * UUID des Objektes der Klasse Slider, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein um auf bestimmte
     * Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean clickable;

    private String url;

    private String name;
    
    private String page;

    private Integer orderNumber;

    @OneToOne(mappedBy = "slider", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "image_uuid")
    private SliderImage image;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean visible;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getClickable() {
        return clickable;
    }

    public void setClickable(Boolean clickable) {
        this.clickable = clickable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public SliderImage getImage() {
        return image;
    }

    public void setImage(SliderImage image) {
        this.image = image;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedAddress() {
        return modifiedAddress;
    }

    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

}// Ende class
