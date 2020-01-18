/**
 * 
 */
package de.tivsource.page.entity.appointment;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Appointment extends ContentItem {

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date pointInTime;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean booking;
    
	private String bookingUrl;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean hasVenue;

	private String venue;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date visibleFrom;
	
    public Date getPointInTime() {
        return pointInTime;
    }

    public void setPointInTime(Date pointInTime) {
        this.pointInTime = pointInTime;
    }

    public Boolean getBooking() {
        return booking;
    }

    public void setBooking(Boolean booking) {
        this.booking = booking;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }

    public Boolean getHasVenue() {
        return hasVenue;
    }

    public void setHasVenue(Boolean hasVenue) {
        this.hasVenue = hasVenue;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getVisibleFrom() {
        return visibleFrom;
    }

    public void setVisibleFrom(Date visibleFrom) {
        this.visibleFrom = visibleFrom;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("appointment/");
        stringBuffer.append(this.getUuid());
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
