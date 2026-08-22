package de.tivsource.page.admin.actions.locations.vacancy;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="vacancyAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/add_form.jsp")
  }),
  @TilesDefinition(name="vacancyEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/edit_form.jsp")
  }),
  @TilesDefinition(name="vacancyDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/delete_form.jsp")
  }),
  @TilesDefinition(name="imageForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/image_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -7248680606365373566L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @Autowired
    @Qualifier("cssDaoLocal")
    private CSSGroupDaoLocal cssGroupDaoLocal;

	@Autowired
    @Qualifier("vacancyDaoLocal")
    private VacancyDaoLocal vacancyDaoLocal;

    @Autowired
    @Qualifier("locationDaoLocal")
    private LocationDaoLocal locationDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

	private Vacancy vacancy;

	private String uncheckVacancy;

	private String lang = "DE";

    private List<Location> locationList;

    private List<CSSGroup> cssGroupList;

    @Autowired
    public FormAction(PropertyService propertyService) {
        super(propertyService);
    }

	public Vacancy getVacancy() {
        return vacancy;
    }

	@StrutsParameter
	public void setUncheckVacancy(String uncheckVacancy) {
        this.uncheckVacancy = uncheckVacancy;
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
        locationList = locationDaoLocal.findAll(0, locationDaoLocal.countAll());
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "vacancyEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "vacancyAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "vacancyDeleteForm") }
        ),
        @Action(
                value = "imageForm", 
                results = { @Result(name = "success", type="tiles", location = "imageForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<Location> getLocationList() {
        return locationList;
    }// Ende getLocationList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

	private void loadPageParameter() {

		if( uncheckVacancy != null && uncheckVacancy != "" && uncheckVacancy.length() > 0) {
			vacancy = vacancyDaoLocal.findByUuid(uncheckVacancy);
		} else {
			vacancy = new Vacancy();
		}

	}// Ende loadPageParameter()

}// Ende class
