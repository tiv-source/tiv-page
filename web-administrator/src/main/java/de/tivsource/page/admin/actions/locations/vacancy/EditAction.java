package de.tivsource.page.admin.actions.locations.vacancy;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="vacancyEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/edit_form.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6472541996524122642L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Vacancy vacancy;

    private String lang;

    private List<Location> locationList;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
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
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "vacancyEditForm"),
        				@Result(name = "error",   type = "tiles", location = "vacancyEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(vacancy != null) {
    		LOGGER.info("UUID des Events: " + vacancy.getUuid());
    		Vacancy dbVacancy = vacancyDaoLocal.findByUuid(vacancy.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
            	vacancy.getContentMap().put(Language.DE, dbVacancy.getContentObject(Language.DE));
            	dbVacancy.getContentMap().get(Language.EN).setContent(vacancy.getContent(Language.EN));
            	dbVacancy.getContentMap().get(Language.EN).setModified(new Date());

                vacancy.getDescriptionMap().put(Language.DE, dbVacancy.getDescriptionObject(Language.DE));
                String noLineBreaks = vacancy.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbVacancy.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbVacancy.getDescriptionMap().get(Language.EN).setKeywords(vacancy.getKeywords(Language.EN));
                dbVacancy.getDescriptionMap().get(Language.EN).setName(vacancy.getName(Language.EN));
            } else {
            	dbVacancy.getContentMap().get(Language.DE).setContent(vacancy.getContent(Language.DE));
                dbVacancy.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = vacancy.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbVacancy.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbVacancy.getDescriptionMap().get(Language.DE).setKeywords(vacancy.getKeywords(Language.DE));;
                dbVacancy.getDescriptionMap().get(Language.DE).setName(vacancy.getName(Language.DE));
            }

            dbVacancy.setBeginning(vacancy.getBeginning());
            dbVacancy.setWorkingTime(vacancy.getWorkingTime());
            dbVacancy.setTechnical(vacancy.getTechnical());
            dbVacancy.setOrderNumber(vacancy.getOrderNumber());

    		dbVacancy.setModifiedAddress(remoteAddress);
    		dbVacancy.setLocation(locationDaoLocal.findByUuidWidthEvents(vacancy.getLocation().getUuid()));
    		dbVacancy.setModified(new Date());
    		dbVacancy.setModifiedBy(remoteUser);
    		dbVacancy.setVisible(vacancy.getVisible());
    		dbVacancy.setPictureOnPage(vacancy.getPictureOnPage());
    		dbVacancy.setCssGroup(vacancy.getCssGroup());
    		
    		vacancyDaoLocal.merge(dbVacancy);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<Location> getLocationList() {
        return locationList;
    }// Ende getLocationList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
