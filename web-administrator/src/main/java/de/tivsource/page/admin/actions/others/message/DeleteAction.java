package de.tivsource.page.admin.actions.others.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.entity.message.Message;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4352071715982352252L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @Autowired
    @Qualifier("messageDaoLocal")
    private MessageDaoLocal messageDaoLocal;

    private Message message;

    @Autowired
    public DeleteAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=1)
	public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "messageDeleteForm"),
        				@Result(name = "error", type="tiles", location = "messageDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	if(message != null) {
    	    Message dbMessage = messageDaoLocal.findByUuid(message.getUuid());
    		messageDaoLocal.delete(dbMessage);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
