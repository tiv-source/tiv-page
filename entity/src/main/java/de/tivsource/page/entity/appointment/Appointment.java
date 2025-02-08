/**
 * 
 */
package de.tivsource.page.entity.appointment;

import java.util.Date;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Basic;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Appointment extends ContentItem {

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date pointInTime;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean booking;
    
	private String bookingUrl;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean hasVenue;

	private String venue;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
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
