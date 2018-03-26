package de.tivsource.page.user.actions.feedback;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.Preparable;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.feedback.Feedback;
import de.tivsource.page.entity.feedback.FeedbackOption;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

public class IndexAction extends EmptyAction implements Preparable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3520284340434098206L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name = "FeedbackOptionDao")
    private FeedbackOptionDaoLocal feedbackOptionDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Feedback feedback;

    private Integer uncheckCashpoint;

    private Integer uncheckVoucher;
    
    private Page page;

    private List<FeedbackOption> options;
    
    public Feedback getFeedback() {
        return feedback;
    }

    public Boolean getCashpoint() {
        if(feedback.getCashpoint() != null) {
            return true;
        }
        return false;
    }

    public void setCashpoint(Integer uncheckCashpoint) {
        this.uncheckCashpoint = uncheckCashpoint;
    }

    public Boolean getVoucher() {
        if(feedback.getVoucher() != null) {
            return true;
        }
        return false;
    }

    public void setVoucher(Integer uncheckVoucher) {
        this.uncheckVoucher = uncheckVoucher;
    }

    @Override
    @Actions({
        @Action(value = "index", results = {
            @Result(name = "success", type = "tiles", location = "feedbackForm"),
            @Result(name = "input", type = "tiles", location = "feedbackForm"),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Initialisiere Feedback Objekt
        initFeedbackObject();

        return SUCCESS;

    }// Ende execute()

    @Override
    public void prepare() throws Exception {
        LOGGER.info("prepare() aufgerufen.");
        // Hole Action Locale
        this.getLanguageFromActionContext();

        options = feedbackOptionDaoLocal.findAllVisible(0, feedbackOptionDaoLocal.countAllVisible());

        // Setze Daten in ein Page Objekt
        setUpPage();
    }

    @Override
    public Page getPage() {
        return page;
    }

    public List<FeedbackOption> getOptions() {
        return options;
    }

    private void setUpPage() {
    	page = pageDaoLocal.findByTechnical("feedback");
    }

    private void initFeedbackObject() {
        feedback = new Feedback();
        if(isValidCashpoint()) {
            feedback.setCashpoint(uncheckCashpoint.toString());
        }
        if(isValidVoucher()) {
            feedback.setVoucher(uncheckVoucher.toString());
        }
    }

    private Boolean isValidCashpoint() {
        // TODO: Überprüfung ob es eine gültige Kasse ist
        if(uncheckCashpoint != null && uncheckCashpoint > 0) {
            return true;
        }
        return false;
    }

    private Boolean isValidVoucher() {
        // TODO: Überprüfung ob es eine gültige Bonnummer ist
        if(uncheckVoucher != null && uncheckVoucher > 0) {
            return true;
        }
        return false;
    }



}// Ende class
