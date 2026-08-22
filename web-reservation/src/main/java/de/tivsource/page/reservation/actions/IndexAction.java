package de.tivsource.page.reservation.actions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3953411070248396460L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

    @Autowired
    @Qualifier("locationDaoLocal")
    private LocationDaoLocal locationDaoLocal;

    private Page page;

    @Override
    @Actions({
        @Action(
        		value = "index", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "index"),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	// Hole Action Locale
    	this.getLanguageFromActionContext();

    	page = pageDaoLocal.findByTechnical("reservation");

    	boolean contactPageEnabled = propertyDaoLocal.findByKey("reservation.page.enabled").getValue().equals("true") ? true : false;

    	if(contactPageEnabled) {
            return SUCCESS;
    	} else {
    	    return ERROR;
    	}

    }// Ende execute()

    public Page getPage() {
    	return page;
    }// Ende getPage()

    public List<Location> getList() {
        return locationDaoLocal.findAllEventLocation();
    }

}// Ende class