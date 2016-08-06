package de.tivsource.page.admin.actions.others.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.entity.message.Message;

/**
 * 
 * @author Marc Michele
 *
 */
public class ViewAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2502111716063075247L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ViewAction.class);

    @InjectEJB(name="MessageDao")
    private MessageDaoLocal messageDaoLocal;

    private Message message;

    private String uncheckMessage;

    public Message getMessage() {
        return message;
    }

	public void setMessage(String uncheckMessage) {
        this.uncheckMessage = uncheckMessage;
    }

	@Override
    @Actions({
        @Action(
        		value = "view", 
        		results = { @Result(name = "success", type="tiles", location = "messageView") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {
		if( uncheckMessage != null && uncheckMessage != "" && uncheckMessage.length() > 0) {
		    message = messageDaoLocal.findByUuid(uncheckMessage);
		    message.setContent((message.getContent().replace("\n", "<br/>")));
		    
		}
	}// Ende loadPageParameter()

}// Ende class
