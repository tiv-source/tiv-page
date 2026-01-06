package de.tivsource.page.reservation.actions;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionContext;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.ServletResponseAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * 
 * @author Marc Michele
 */
public class EmptyAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware, SessionAware {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 4881897528400501135L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(EmptyAction.class);

	/**
	 * Servlet-Request der die Post und Get Daten der Session enthält.
	 */
	private HttpServletRequest servletRequest;

	private HttpServletResponse servletResponse;

	private Map<String, Object> session;

	/**
	 * Attribut das die ausgelesene Sprache enthält.
	 */
	private String language = null;

	public EmptyAction() {
		super();
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	@Actions({ 
		@Action(value = "logout", results = { @Result(name = "success", type = "tiles", location = "logout") })
		})
	public String execute() throws Exception {
		LOGGER.info("execute() aufgerufen.");

		// Hole Action Locale
		this.getLanguageFromActionContext();

		return SUCCESS;
	}

	public String getActionName() {
		return ActionContext.getContext().getActionName();
	}

	/**
	 * Methode die die aktuelle Sprache aus dem Context holt.
	 */
	protected void getLanguageFromActionContext() {
		LOGGER.info("getLanguageFromSession() aufgerufen.");
		ActionContext actionContext = ActionContext.getContext();
		language = actionContext.getLocale().getLanguage();
		LOGGER.info("Action Locale: " + language);
	}

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void withServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void withServletRequest(HttpServletRequest httpServletRequest) {
        this.servletRequest = httpServletRequest;
    }

}// Ende class
