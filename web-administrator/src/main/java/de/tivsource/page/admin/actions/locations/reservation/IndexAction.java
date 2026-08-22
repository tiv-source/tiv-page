package de.tivsource.page.admin.actions.locations.reservation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.entity.event.Event;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="reservationGrid", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/default_jquery.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/reservation/list.jsp")
  })
})
public class IndexAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2200164248790963358L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @Autowired
    @Qualifier("eventDaoLocal")
    private EventDaoLocal eventDaoLocal;

    private Event event;

    private String uncheckEvent;

    @Autowired
    public IndexAction(PropertyService propertyService) {
        super(propertyService);
    }

    public Event getEvent() {
        return event;
    }

    @StrutsParameter
    public void setUncheckEvent(String uncheckEvent) {
        this.uncheckEvent = uncheckEvent;
    }

	@Override
    @Actions({
        @Action(
        		value = "index", 
        		results = { @Result(name = "success", type="tiles", location = "reservationGrid") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.trace("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    private void loadPageParameter() {
        LOGGER.trace("loadPageParameter() aufgerufen.");
        if( uncheckEvent != null && uncheckEvent != "" && uncheckEvent.length() > 0) {
            event = eventDaoLocal.findByUuid(uncheckEvent);
        }
    }// Ende loadPageParameter()
	
}// Ende class
