package de.tivsource.page.admin.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Marc Michele
 */
@TilesDefinitions({
    @TilesDefinition(name="index",  extend = "adminTemplate"),
    @TilesDefinition(name="logout", extend = "adminTemplate", putAttributes = {
        @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/logout.jsp")
    })
})
public class EmptyAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware, SessionAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -7504444800754220704L;

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

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.servletRequest = httpServletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	@Actions({ 
		@Action(value = "index", results = { @Result(name = "success", type = "tiles", location = "index") }),
		@Action(value = "logout", results = { @Result(name = "success", type = "tiles", location = "logout") })
		})
	public String execute() throws Exception {
		LOGGER.info("execute() aufgerufen.");

		// Hole Action Locale
		this.getLanguageFromActionContext();

		return SUCCESS;
	}

	public String getActionName() {
		return ActionContext.getContext().getName();
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

}// Ende class
