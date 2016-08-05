package de.tivsource.page.admin.actions.others.vacancy;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.picture.Picture;
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
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

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

    		dbVacancy.setModifiedAddress(remoteAddress);
    		dbVacancy.setLocation(locationDaoLocal.findByUuidWidthEvents(vacancy.getLocation().getUuid()));
    		dbVacancy.setModified(new Date());
    		dbVacancy.setModifiedBy(remoteUser);
    		dbVacancy.setVisible(vacancy.getVisible());
    		dbVacancy.setPicture(vacancy.getPicture());
    		
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

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("41c1471b-6511-4e98-a1ab-fe13e7a906ed");
	}

}// Ende class
