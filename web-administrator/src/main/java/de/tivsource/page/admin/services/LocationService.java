package de.tivsource.page.admin.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * @author Marc Michele
 *
 */
@Service
public class LocationService {

    private static final Logger LOGGER = LogManager.getLogger(LocationService.class);

    @Autowired
    private LocationDaoLocal locationDaoLocal;

    public List<Location> getAllLocations() {
        LOGGER.debug("getAllLocations() aufgerufen.");
        return locationDaoLocal.findAll(0, locationDaoLocal.countAll());
    }

}// Ende class
