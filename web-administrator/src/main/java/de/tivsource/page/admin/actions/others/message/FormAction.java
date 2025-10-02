package de.tivsource.page.admin.actions.others.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.entity.message.Message;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="messageDeleteForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/message/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 4505856166481245798L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="MessageDao")
    private MessageDaoLocal messageDaoLocal;

	private Message message;

	private String uncheckMessage;

	public Message getMessage() {
        return message;
    }

	@StrutsParameter
	public void setUncheckMessage(String uncheckMessage) {
        this.uncheckMessage = uncheckMessage;
    }

    @Override
    @Actions({
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "messageDeleteForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	LOGGER.info("Message UUID: " + uncheckMessage);
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {

		if( uncheckMessage != null && uncheckMessage != "" && uncheckMessage.length() > 0) {
		    message = messageDaoLocal.findByUuid(uncheckMessage);
		} 

	}// Ende loadPageParameter()
	
}// Ende class
