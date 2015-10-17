/**
 * 
 */
package de.tivsource.page.entity.event;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;

import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Event extends NamingItem {

    private BigDecimal price;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="event_date")
    private Date date;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean supper;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSupper() {
        return supper;
    }

    public void setSupper(Boolean supper) {
        this.supper = supper;
    }

}// Ende class
