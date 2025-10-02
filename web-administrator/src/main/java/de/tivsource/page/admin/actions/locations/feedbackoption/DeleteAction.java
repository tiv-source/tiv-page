package de.tivsource.page.admin.actions.locations.feedbackoption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal;
import de.tivsource.page.entity.feedback.FeedbackOption;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 904888186996610235L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="FeedbackOptionDao")
    private FeedbackOptionDaoLocal feedbackOptionDaoLocal;

    private FeedbackOption feedbackOption;

    /**
     * @return the feedbackOption
     */
    @StrutsParameter(depth=3)
    public FeedbackOption getFeedbackOption() {
        return feedbackOption;
    }

    /**
     * @param feedbackOption the feedbackOption to set
     */
    public void setFeedbackOption(FeedbackOption feedbackOption) {
        this.feedbackOption = feedbackOption;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "feedbackOptionDeleteForm"),
        				@Result(name = "error", type="tiles", location = "feedbackOptionDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

    	if(feedbackOption != null) {
    	    FeedbackOption dbFeedbackOption = feedbackOptionDaoLocal.findByUuid(feedbackOption.getUuid());
    	    feedbackOptionDaoLocal.delete(dbFeedbackOption);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
