package de.tivsource.page.admin.actions.locations.location;


import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.helper.osm.CreateOpenStreetMapCache;

/**
 * 
 * @author Marc Michele
 *
 */
public class OSMCacheAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 575079215033958866L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(OSMCacheAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @Override
    @Actions({
        @Action(
        		value = "cache", 
        		results = { @Result(name = "success", type="tiles", location = "index") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	Iterator<Location> locationIterator = locationDaoLocal.findAll(0, locationDaoLocal.countAll()).iterator();
    	
    	while(locationIterator.hasNext()) {
    		Location next = locationIterator.next();
    		CreateOpenStreetMapCache cacheToCreate = new CreateOpenStreetMapCache(next.getUuid(), next.getLatitude(), next.getLongitude());
    		cacheToCreate.generate();
    	}
    	
    	
    	return SUCCESS;
    }// Ende execute()
	
}// Ende class
