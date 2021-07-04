/**
 * 
 */
package de.tivsource.page.entity.request;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.namingitem.NamingItem;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Reason extends NamingItem {

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
