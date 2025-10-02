/**
 * 
 */
package de.tivsource.page.entity.subsumption;

import java.util.SortedSet;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "Subsumption")
public class Subsumption extends ContentItem {

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean showTitles = true;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean showDescriptions = true;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean showDates = true;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean showPictures = true;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean orderDates = true;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, targetEntity = ContentItem.class)
    @SortNatural
    private SortedSet<ContentItem> contentItems;

    /**
     * @return the showTitles
     */
    public Boolean getShowTitles() {
        return showTitles;
    }

    /**
     * @param showTitles the showTitles to set
     */
    public void setShowTitles(Boolean showTitles) {
        this.showTitles = showTitles;
    }

    /**
     * @return the showDescriptions
     */
    public Boolean getShowDescriptions() {
        return showDescriptions;
    }

    /**
     * @param showDescriptions the showDescriptions to set
     */
    public void setShowDescriptions(Boolean showDescriptions) {
        this.showDescriptions = showDescriptions;
    }

    /**
     * @return the showDates
     */
    public Boolean getShowDates() {
        return showDates;
    }

    /**
     * @param showDates the showDates to set
     */
    public void setShowDates(Boolean showDates) {
        this.showDates = showDates;
    }

    /**
     * @return the showPictures
     */
    public Boolean getShowPictures() {
        return showPictures;
    }

    /**
     * @param showPictures the showPictures to set
     */
    public void setShowPictures(Boolean showPictures) {
        this.showPictures = showPictures;
    }

    /**
     * @return the orderDates
     */
    public Boolean getOrderDates() {
        return orderDates;
    }

    /**
     * @param orderDates the orderDates to set
     */
    public void setOrderDates(Boolean orderDates) {
        this.orderDates = orderDates;
    }

    /**
     * @return the contentItems
     */
    public SortedSet<ContentItem> getContentItems() {
        return contentItems;
    }

    /**
     * @param contentItems the contentItems to set
     */
    public void setContentItems(SortedSet<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("subsumption/");
        stringBuffer.append(this.getTechnical());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

    @Override
    public int compareTo(ContentItem o) {
        if (o.getCreated().after(this.getCreated())) {
            return 1;
        } else if (o.getCreated().before(this.getCreated())) {
            return -1;
        } else {
            return o.getUuid().compareTo(this.getUuid());
        }
    }

}// Ende class
