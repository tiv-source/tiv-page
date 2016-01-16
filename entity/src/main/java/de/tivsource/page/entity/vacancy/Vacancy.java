/**
 * 
 */
package de.tivsource.page.entity.vacancy;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.tivsource.page.entity.application.Application;
import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.location.Location;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Vacancy extends ContentItem {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_uuid")
    private Location location;

    @OneToMany(mappedBy = "vacancy", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Application> applications;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

}// Ende class
