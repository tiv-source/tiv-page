package de.tivsource.page.admin.actions.event;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.entity.event.Event;

/**
 * 
 * @author Marc Michele
 *
 */
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6472541996524122642L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "eventEditForm"),
        				@Result(name = "error",   type = "tiles", location = "eventEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	if(event != null) {
    		LOGGER.info("UUID des Events: " + event.getUuid());
    		Event dbEvent = eventDaoLocal.findByUuid(event.getUuid());
    		dbEvent.setBeginning(event.getBeginning());
    		eventDaoLocal.merge(dbEvent);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
