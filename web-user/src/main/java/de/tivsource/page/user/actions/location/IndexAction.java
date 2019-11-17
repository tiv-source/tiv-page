package de.tivsource.page.user.actions.location;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="location", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/location.jsp")
  })
})
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3431822966540867695L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Page page;

    @Override
    public void prepare() {
        // Lade die Filialübersichtsseite aus der Datenbank
        page = pageDaoLocal.findByTechnical("location");
    }

    @Override
    @Actions({
        @Action(
        		value = "index", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "location"),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.location").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();
            return SUCCESS;
        } else {
            // Wennn das Modul nicht aktiviert ist.
            return ERROR;
        }
    }// Ende execute()

    public Page getPage() {
    	return page;
    }// Ende getPage()

    public List<Location> getList() {
        return locationDaoLocal.findAllVisible(0, locationDaoLocal.countAllVisible());
    }

}// Ende class