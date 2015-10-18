package de.tivsource.page.admin.actions.location;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;

/**
 * 
 * @author Marc Michele
 *
 */
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -1781888752368914342L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Location location;

    private String lang;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "locationEditForm"),
        				@Result(name = "error",   type = "tiles", location = "locationEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(location != null) {
    		LOGGER.info(location.getUuid());
    		Location dbLocation = locationDaoLocal.findByUuid(location.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                location.getDescriptionMap().put(Language.DE, dbLocation.getDescriptionObject(Language.DE));
                dbLocation.getDescriptionMap().get(Language.EN).setDescription(location.getDescription(Language.EN));
                dbLocation.getDescriptionMap().get(Language.EN).setKeywords(location.getKeywords(Language.EN));
                dbLocation.getDescriptionMap().get(Language.EN).setName(location.getName(Language.EN));
            } else {
                dbLocation.getDescriptionMap().get(Language.DE).setDescription(location.getDescription(Language.DE));
                dbLocation.getDescriptionMap().get(Language.DE).setKeywords(location.getKeywords(Language.DE));;
                dbLocation.getDescriptionMap().get(Language.DE).setName(location.getName(Language.DE));
            }

    		dbLocation.setModified(new Date());
    		dbLocation.setVisible(location.getVisible());
    		dbLocation.setModifiedBy(remoteUser);
    		dbLocation.setIp(remoteAddress);

    		dbLocation.getAddress().setCity(location.getAddress().getCity());
    		dbLocation.getAddress().setCountry(location.getAddress().getCountry());
    		dbLocation.getAddress().setStreet(location.getAddress().getStreet());
    		dbLocation.getAddress().setZip(location.getAddress().getZip());

    		dbLocation.getContactDetails().setEmail(location.getContactDetails().getEmail());
    		dbLocation.getContactDetails().setFax(location.getContactDetails().getFax());
    		dbLocation.getContactDetails().setHomepage(location.getContactDetails().getHomepage());
    		dbLocation.getContactDetails().setMobile(location.getContactDetails().getMobile());
    		dbLocation.getContactDetails().setTelephone(location.getContactDetails().getTelephone());

    		dbLocation.setLatitude(location.getLatitude());
    		dbLocation.setLongitude(location.getLongitude());

    		locationDaoLocal.merge(dbLocation);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()


}// Ende class
