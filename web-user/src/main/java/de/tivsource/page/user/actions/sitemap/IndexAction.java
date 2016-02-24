package de.tivsource.page.user.actions.sitemap;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 7064780265619899524L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(IndexAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @Override
    @Actions({
        @Action(value = "index", results = {
            @Result(name = "success", type = "tiles", location = "sitemapTemplate")
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        return SUCCESS;
    }// Ende execute()

    public List<Location> getLocation() {
        return locationDaoLocal.findAllEventLocation();
    }

}// Ende class
