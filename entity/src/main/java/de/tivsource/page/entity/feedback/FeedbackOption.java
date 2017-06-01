/**
 * 
 */
package de.tivsource.page.entity.feedback;

import java.util.Date;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.entity.enumeration.Language;

/**
 * @author Marc Michele
 *
 */
@Entity
public class FeedbackOption {

    /**
     * UUID des Objektes der Klasse FeedbackOption, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true)
    private String uuid;

    /**
     * Die Map mit den Beschreibungen des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "feedbackOption", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, FeedbackOptionDescription> descriptionMap;

    private Integer orderNumber;

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

    public Map<Language, FeedbackOptionDescription> getDescriptionMap() {
        return descriptionMap;
    }

    public void setDescriptionMap(
            Map<Language, FeedbackOptionDescription> descriptionMap) {
        this.descriptionMap = descriptionMap;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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
