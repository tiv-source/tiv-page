/**
 * 
 */
package de.tivsource.page.entity.request;

import java.util.List;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.namingitem.NamingItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = Reason.TABLE)
@Table(appliesTo = Reason.TABLE, fetch = FetchMode.SELECT)
public class Reason extends NamingItem {

    public static final String TABLE = "Reason";

    @OneToMany(mappedBy = "reason", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Request> requests;

    /**
     * @return the requests
     */
    public List<Request> getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

}// Ende class
