package de.tivsource.page.user.actions;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.common.menuentry.MenuEntry;
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.menuentry.MenuEntryDaoLocal;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.news.News;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.slider.Slider;

/**
 * 
 * @author Marc Michele
 */
@TilesDefinitions({
    @TilesDefinition(name="index",  extend = "userTemplate")
})
public class EmptyAction extends ActionSupport implements Preparable, ServletRequestAware,
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

    @InjectEJB(name="MenuEntryDao")
    private MenuEntryDaoLocal menuEntryDaoLocal;

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="AppointmentDao")
    private AppointmentDaoLocal appointmentDaoLocal;

    @InjectEJB(name="SliderDao")
    private SliderDaoLocal sliderDaoLocal;

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    private Event left;
    private Event right;

    private Page page;
    
	/**
	 * Attribut das die ausgelesene Sprache enthält.
	 */
	private String language = null;

	private List<News> newsList;
	
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
    public void prepare() {
        // Lade die Startseite
        page = pageDaoLocal.findByTechnical("home");
        newsList = newsDaoLocal.findAllVisible(0, Integer.parseInt(propertyDaoLocal.findByKey("news.on.home.quantity").getValue()));
    }

	@Override
	@Actions({ 
		@Action(value = "index", results = { @Result(name = "success", type = "tiles", location = "index") })
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

	public List<MenuEntry> getTopNavigation() {
	    return menuEntryDaoLocal.findAllTopNavigation();
	}

    public List<MenuEntry> getNavigation() {
        return menuEntryDaoLocal.findAllNavigation();
    }

    public List<MenuEntry> getBottomNavigation() {
        return menuEntryDaoLocal.findAllBottomNavigation();
    }

    public List<MenuEntry> getResponsiveNavigation() {
        return menuEntryDaoLocal.findAllResponsiveNavigation();
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

    public List<Appointment> getSliderList() {
        return appointmentDaoLocal.findAllVisible(0, Integer.parseInt(propertyDaoLocal.findByKey("home.appointment.slider.max.items").getValue()));
    }

    public String getSliderWidth() {
        BigDecimal sliderWidth = new BigDecimal(100).divide(new BigDecimal(getSliderList().size()), 3, BigDecimal.ROUND_HALF_UP);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US); 
        return numberFormat.format(sliderWidth);
    }
    
    public List<Slider> getHomeSliderList() {
        return sliderDaoLocal.findAllVisible(0, 7, "home");
    }

    public String getHomeSliderWidth() {
        BigDecimal sliderWidth = new BigDecimal(100).divide(new BigDecimal(getHomeSliderList().size()), 2, BigDecimal.ROUND_HALF_UP);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US); 
        return numberFormat.format(sliderWidth);
    }

    /**
     * @return the newsList
     */
    public List<News> getNewsList() {
        return newsList;
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
