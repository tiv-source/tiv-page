package de.tivsource.page.user.actions.feedback;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.feedback.FeedbackDaoLocal;
import de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.feedback.Feedback;
import de.tivsource.page.entity.feedback.FeedbackOption;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="feedbackForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/feedback_form.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/feedback_form.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/feedback/feedback_form.jsp")
  }),
  @TilesDefinition(name="page", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/content.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/page.jsp")
  })
})
public class SentAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -899397629172153047L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(SentAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="FeedbackDao")
    private FeedbackDaoLocal feedbackDaoLocal;

    @InjectEJB(name = "FeedbackOptionDao")
    private FeedbackOptionDaoLocal feedbackOptionDaoLocal;

	private Feedback feedback;

	private Page page;

	private List<FeedbackOption> options;
	
	public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Boolean getCashpoint() {
        if(feedback.getCashpoint() != null && feedback.getCashpoint().length() > 0) {
            return true;
        }
        return false;
    }

    public Boolean getVoucher() {
        if(feedback.getVoucher() != null && feedback.getVoucher().length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Actions({
        @Action(
        		value = "sent", 
        		results = { 
        				@Result(name = "success", type="tiles", location = "page"), 
        				@Result(name = "input", type="tiles", location = "feedbackForm")
        				}
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.feedback").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Speichere Message Objekt
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
            feedback.setUuid(UUID.randomUUID().toString());
            feedback.setCreated(new Date());
            feedback.setCreatedAddress(remoteAddress);
            feedbackDaoLocal.merge(feedback);

            LOGGER.info("Anzahl der Antworten: " + feedback.getAnswers().size());
            
            return SUCCESS;            
        } else {
            return ERROR;
        }
	}// Ende execute()

    @Override
    public void validate(){
        if(options == null) {
            options = feedbackOptionDaoLocal.findAllVisible(0, feedbackOptionDaoLocal.countAllVisible());
        }

        // feedback.getCashpoint();
        // feedback.getVoucher();
        // feedback.getComment();
        
        // Überprüfe Antworten
        Iterator<FeedbackOption> iteratorFeedbackOption = options.iterator();
        while(iteratorFeedbackOption.hasNext()) {
            FeedbackOption feedbackOption = iteratorFeedbackOption.next();
            LOGGER.info("Check for MapKey " + feedbackOption.getMapKey());
            Integer value = feedback.getAnswers().get(feedbackOption.getMapKey());
            // Wenn die Anwort nicht vorhanden ist
            if(value == null) {
                LOGGER.info("Anwort nicht Vorhanden");
                addFieldError("feedback.answers['" + feedbackOption.getMapKey() + "']",
                        getText("feedback.validation."+ feedbackOption.getMapKey() +".required"));
            }
            // Wenn die Anwort vorhanden ist und zwischen 0 und Maximum liegt            
            if((value != null) && !(value > 0) && !(value < (feedbackOption.getMaxStars() + 1))) {
                LOGGER.info("Anwort nicht gültig");
                addFieldError("feedback.answers['" + feedbackOption.getMapKey() + "']",
                        getText("feedback.validation."+ feedbackOption.getMapKey() +".notvalid"));
            }
        }
        
    }// validate()

    @Override
    public Page getPage() {
        if(page == null) {
            setUpPage();
        }
        return page;
    }// Ende getPage()

    public List<FeedbackOption> getOptions() {
        if(options == null) {
            options = feedbackOptionDaoLocal.findAllVisible(0, feedbackOptionDaoLocal.countAllVisible());
        }
        return options;
    }

    private void setUpPage() {
        LOGGER.info("Action Errors: " + this.getFieldErrors().size());
        if(this.getFieldErrors().size() > 0) {
            page = pageDaoLocal.findByTechnical("feedback");
        } else {
            page = pageDaoLocal.findByTechnical("feedbacksent");
        }
    }

}// Ende class
