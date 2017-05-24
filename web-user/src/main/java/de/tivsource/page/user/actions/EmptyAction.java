package de.tivsource.page.user.actions;

import java.util.List;
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 */
public class EmptyAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware, SessionAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 1294373943839660227L;

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

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;
	
    @InjectEJB(name="PageDao")
	private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    private Event left;
    private Event right;

    private Page page;
    
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
		@Action(value = "index", results = { @Result(name = "success", type = "tiles", location = "index") })
		})
	public String execute() throws Exception {
		LOGGER.info("execute() aufgerufen.");

		// Hole Action Locale
		this.getLanguageFromActionContext();
		page = pageDaoLocal.findByTechnical("home");

		return SUCCESS;
	}

	public String getActionName() {
		return ActionContext.getContext().getName();
	}

	public List<Page> getTopNavigation() {
	    return pageDaoLocal.findAllTopNavigation();
	}

    public List<Page> getNavigation() {
        return pageDaoLocal.findAllNavigation();
    }

    public List<Page> getBottomNavigation() {
        return pageDaoLocal.findAllBottomNavigation();
    }

    public List<Page> getResponsiveNavigation() {
        return pageDaoLocal.findAllResponsiveNavigation();
    }

    public Page getPage() {
        return this.page;
    }

    public String getProperty(String key) {
        return propertyDaoLocal.findByKey(key).getValue();
    }

    public Event getLeftLocation() {
        if(left == null) {
            left = eventDaoLocal.findAll(propertyDaoLocal.findByKey("home.location.left").getValue(), 0, 1).get(0);
        }
        return left;
    }

    public Event getRightLocation() {
        if(right == null) {
            right = eventDaoLocal.findAll(propertyDaoLocal.findByKey("home.location.right").getValue(), 0, 1).get(0);
        }
        return right;
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
