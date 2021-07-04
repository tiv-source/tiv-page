/**
 * 
 */
package de.tivsource.page.entity.companion;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.pictureitem.PictureItem;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class CompanionGroup extends PictureItem {

    @OneToMany(mappedBy = "group", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Companion> companions;

    public List<Companion> getCompanions() {
        return companions;
    }

    public void setCompanions(List<Companion> companions) {
        this.companions = companions;
    }

}// Ende class
