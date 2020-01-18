/**
 * 
 */
package de.tivsource.page.entity.subsumption;

import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "Subsumption")
public class Subsumption extends ContentItem {

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean showTitles = true;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean showDescriptions = true;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean showDates = true;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean showPictures = true;

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
