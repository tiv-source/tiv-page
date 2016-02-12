package de.tivsource.page.admin.actions.location;


import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 1231962694824098776L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(FormAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

	private Location location;

	private String uncheckLocation;

	private String lang;

    public Location getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.uncheckLocation = location;
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
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "locationEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "locationAddForm") }
        ),
        @Action(
                value = "deleteForm", 
                results = { @Result(name = "success", type="tiles", location = "locationDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("d8a2d89f-cda4-4c64-9e51-18592e88bbc6");
	}

	private void loadPageParameter() {

		if( uncheckLocation != null && uncheckLocation != "" && uncheckLocation.length() > 0) {
			location = locationDaoLocal.findByUuid(uncheckLocation);
		} else {
			location = new Location();
		}

	}// Ende loadPageParameter()
	
}// Ende class
