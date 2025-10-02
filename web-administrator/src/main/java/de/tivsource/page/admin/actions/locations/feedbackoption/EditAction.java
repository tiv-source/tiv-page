package de.tivsource.page.admin.actions.locations.feedbackoption;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.feedback.FeedbackOption;

/**
 * 
 * @author Marc Michele
 *
 */
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -9176491578050650632L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="FeedbackOptionDao")
    private FeedbackOptionDaoLocal feedbackOptionDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private FeedbackOption feedbackOption;

    private String lang = "DE";

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

    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "feedbackOptionEditForm"),
        				@Result(name = "error",   type = "tiles", location = "feedbackOptionEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(feedbackOption != null) {
    		LOGGER.info("UUID der FeedbackOption: " + feedbackOption.getUuid());
    		FeedbackOption dbFeedbackOption = feedbackOptionDaoLocal.findByUuid(feedbackOption.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                feedbackOption.getDescriptionMap().put(Language.DE, dbFeedbackOption.getDescriptionObject(Language.DE));
                String noLineBreaks = feedbackOption.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbFeedbackOption.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbFeedbackOption.getDescriptionMap().get(Language.EN).setKeywords(feedbackOption.getDescriptionObject(Language.EN).getKeywords());
                dbFeedbackOption.getDescriptionMap().get(Language.EN).setName(feedbackOption.getDescriptionObject(Language.EN).getName());
                dbFeedbackOption.getDescriptionMap().get(Language.EN).setHints(feedbackOption.getDescriptionObject(Language.EN).getHints());
            } else {
        	    String noLineBreaks = feedbackOption.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
        	    dbFeedbackOption.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbFeedbackOption.getDescriptionMap().get(Language.DE).setKeywords(feedbackOption.getDescriptionObject(Language.DE).getKeywords());;
                dbFeedbackOption.getDescriptionMap().get(Language.DE).setName(feedbackOption.getDescriptionObject(Language.DE).getName());
                dbFeedbackOption.getDescriptionMap().get(Language.DE).setHints(feedbackOption.getDescriptionObject(Language.DE).getHints());
            }

            dbFeedbackOption.setMapKey(feedbackOption.getMapKey());
            dbFeedbackOption.setMaxStars(feedbackOption.getMaxStars());
            dbFeedbackOption.setOrderNumber(feedbackOption.getOrderNumber());
            dbFeedbackOption.setModifiedAddress(remoteAddress);
    		dbFeedbackOption.setModified(new Date());
    		dbFeedbackOption.setModifiedBy(remoteUser);
    		dbFeedbackOption.setVisible(feedbackOption.getVisible());

    		feedbackOptionDaoLocal.merge(dbFeedbackOption);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

}// Ende class
