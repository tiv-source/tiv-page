package de.tivsource.page.admin.actions.vacancy;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
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
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6472541996524122642L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(EditAction.class);

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Vacancy vacancy;

    private String lang;

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
                vacancy.getDescriptionMap().put(Language.DE, dbVacancy.getDescriptionObject(Language.DE));
                dbVacancy.getDescriptionMap().get(Language.EN).setDescription(vacancy.getDescription(Language.EN));
                dbVacancy.getDescriptionMap().get(Language.EN).setKeywords(vacancy.getKeywords(Language.EN));
                dbVacancy.getDescriptionMap().get(Language.EN).setName(vacancy.getName(Language.EN));
            } else {
                dbVacancy.getDescriptionMap().get(Language.DE).setDescription(vacancy.getDescription(Language.DE));
                dbVacancy.getDescriptionMap().get(Language.DE).setKeywords(vacancy.getKeywords(Language.DE));;
                dbVacancy.getDescriptionMap().get(Language.DE).setName(vacancy.getName(Language.DE));
            }
    		
    		dbVacancy.setModifiedAddress(remoteAddress);
    		dbVacancy.setLocation(locationDaoLocal.findByUuidWidthEvents(vacancy.getLocation().getUuid()));
    		dbVacancy.setModified(new Date());
    		dbVacancy.setModifiedBy(remoteUser);
    		dbVacancy.setVisible(vacancy.getVisible());
    		
    		vacancyDaoLocal.merge(dbVacancy);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<Location> getLocationList() {
        return locationDaoLocal.findAllEventLocation();
    }

}// Ende class
