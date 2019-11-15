/**
 * 
 */
package de.tivsource.page.entity.manual;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Manual extends ContentItem {

    private Integer orderNumber;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

}// Ende class
