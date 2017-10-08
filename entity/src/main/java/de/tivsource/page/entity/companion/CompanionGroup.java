/**
 * 
 */
package de.tivsource.page.entity.companion;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import de.tivsource.page.entity.pictureitem.PictureItem;

/**
 * @author Marc Michele
 *
 */
@Entity
public class CompanionGroup extends PictureItem {

    @Column(unique = true)
    private String technical;

    private Integer orderNumber;

    @OneToMany(mappedBy = "group", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Companion> companions;

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Companion> getCompanions() {
        return companions;
    }

    public void setCompanions(List<Companion> companions) {
        this.companions = companions;
    }

}// Ende class
