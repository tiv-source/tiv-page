package de.tivsource.page.admin.actions.vacancy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 9114559679951589800L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

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
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "vacancyAddForm"),
        				@Result(name = "error", type="tiles", location = "vacancyAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(vacancy != null) {
    	    vacancy.setUuid(UUID.randomUUID().toString());
    	    vacancy.setModified(new Date());
    	    vacancy.setCreated(new Date());
    	    vacancy.setModifiedBy(remoteUser);
    	    vacancy.setModifiedAddress(remoteAddress);


    	    vacancy.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    vacancy.getDescriptionMap().get(Language.DE).setNamingItem(vacancy);
    	    vacancy.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = vacancy.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    vacancy.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    vacancy.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    vacancy.getContentMap().get(Language.DE).setContentItem(vacancy);
    	    vacancy.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    vacancy.getContentMap().get(Language.DE).setCreated(new Date());
    	    vacancy.getContentMap().get(Language.DE).setModified(new Date());


    	    vacancy.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    vacancy.getDescriptionMap().get(Language.EN).setNamingItem(vacancy);
    	    vacancy.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    vacancy.getDescriptionMap().get(Language.EN).setDescription(vacancy.getDescriptionMap().get(Language.DE).getDescription());
    	    vacancy.getDescriptionMap().get(Language.EN).setName(vacancy.getDescriptionMap().get(Language.DE).getName());
    	    vacancy.getDescriptionMap().get(Language.EN).setKeywords(vacancy.getDescriptionMap().get(Language.DE).getKeywords());

    	    vacancy.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    vacancy.getContentMap().get(Language.EN).setContentItem(vacancy);
    	    vacancy.getContentMap().get(Language.EN).setLanguage(Language.EN);
    	    vacancy.getContentMap().get(Language.EN).setCreated(new Date());
    	    vacancy.getContentMap().get(Language.EN).setModified(new Date());
    	    vacancy.getContentMap().get(Language.EN).setContent(vacancy.getContentMap().get(Language.DE).getContent());
    	    
    	    
    	    vacancyDaoLocal.merge(vacancy);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    public List<Location> getLocationList() {
        // TODO: Anzahl der Objkte anpassen
        return locationDaoLocal.findAll(0, locationDaoLocal.countAll());
    }// Ende getLocationList()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("41c1471b-6511-4e98-a1ab-fe13e7a906ed");
	}

}// Ende class
