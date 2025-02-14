package de.tivsource.page.admin.actions;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionContext;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.Preparable;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.ServletResponseAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author Marc Michele
 */
@TilesDefinitions({
    @TilesDefinition(name="index",  extend = "adminTemplate"),
    @TilesDefinition(name="logout", template = "/WEB-INF/tiles/active/view/logout.jsp")
})
public class EmptyAction extends ActionSupport implements Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -7504444800754220704L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(EmptyAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

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
    public void prepare() {
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
		return ActionContext.getContext().getActionName();
	}

    public String getProperty(String key) {
        return propertyDaoLocal.findByKey(key).getValue();
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
