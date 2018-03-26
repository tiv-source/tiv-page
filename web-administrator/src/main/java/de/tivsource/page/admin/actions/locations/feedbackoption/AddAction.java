package de.tivsource.page.admin.actions.locations.feedbackoption;

import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.feedback.FeedbackOption;
import de.tivsource.page.entity.feedback.FeedbackOptionDescription;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -8084324674723975703L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="FeedbackOptionDao")
    private FeedbackOptionDaoLocal feedbackOptionDaoLocal;

    private FeedbackOption feedbackOption;

    public FeedbackOption getFeedbackOption() {
        return feedbackOption;
    }

    public void setFeedbackOption(FeedbackOption feedbackOption) {
        this.feedbackOption = feedbackOption;
    }

	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "manualAddForm"),
        				@Result(name = "error", type="tiles", location = "manualAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(feedbackOption != null) {
    	    feedbackOption.setUuid(UUID.randomUUID().toString());
    	    feedbackOption.setModified(new Date());
    	    feedbackOption.setCreated(new Date());
    	    feedbackOption.setModifiedBy(remoteUser);
    	    feedbackOption.setModifiedAddress(remoteAddress);

    	    feedbackOption.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    feedbackOption.getDescriptionMap().get(Language.DE).setFeedbackOption(feedbackOption);
    	    feedbackOption.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = feedbackOption.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    feedbackOption.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    // Erstelle englische Beschreibung.
    	    FeedbackOptionDescription feedbackOptionDescription = new FeedbackOptionDescription();
    	    feedbackOptionDescription.setUuid(UUID.randomUUID().toString());
    	    feedbackOptionDescription.setFeedbackOption(feedbackOption);
    	    feedbackOptionDescription.setLanguage(Language.EN);
    	    feedbackOptionDescription.setHints(feedbackOption.getDescriptionMap().get(Language.DE).getHints());
    	    feedbackOptionDescription.setDescription(feedbackOption.getDescriptionMap().get(Language.DE).getDescription());
    	    feedbackOptionDescription.setKeywords(feedbackOption.getDescriptionMap().get(Language.DE).getKeywords());
    	    feedbackOptionDescription.setName(feedbackOption.getDescriptionMap().get(Language.DE).getName());
            feedbackOption.getDescriptionMap().put(Language.EN, feedbackOptionDescription);

    		feedbackOptionDaoLocal.merge(feedbackOption);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

}// Ende class
