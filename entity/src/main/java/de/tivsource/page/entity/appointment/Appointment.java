/**
 * 
 */
package de.tivsource.page.entity.appointment;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Appointment extends ContentItem {

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date pointInTime;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean booking;
    
	private String bookingUrl;

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

}// Ende class
