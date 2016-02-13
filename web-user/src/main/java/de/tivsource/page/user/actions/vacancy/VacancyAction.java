package de.tivsource.page.user.actions.vacancy;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.vacancy.Vacancy;
import de.tivsource.page.user.actions.EmptyAction;

public class VacancyAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 4241333027632203234L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(VacancyAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    private Page page;

    /**
     * Location Uuid im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String vacancyUuid;

    private Vacancy vacancy;

    public Vacancy getVacancy() {
		return vacancy;
	}

	@Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "vacancyDetail"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        }),
        @Action(value = "*/form", results = {
            @Result(name = "success", type = "tiles", location = "vacancyForm"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        vacancyUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("VacancyUuid: " + vacancyUuid);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        vacancyUuid = vacancyUuid.replaceAll("/form.html", "");
        vacancyUuid = vacancyUuid.replaceAll("/index.html", "");
        vacancyUuid = vacancyUuid.replaceAll("/vacancy/", "");
            
        LOGGER.info("VacancyUuid: " + vacancyUuid);
        
    	boolean contactPageEnabled = propertyDaoLocal.findByKey("vacancy.page.enabled").getValue().equals("true") ? true : false;

        if(contactPageEnabled) {
            /*
             * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
             * die Location mit der Uuid gibt dann wird der Block ausgeführt.
             */
            if (isValid(vacancyUuid) && vacancyDaoLocal.isVacancy(vacancyUuid)) {
                LOGGER.info("gültige Vacancy Uuid.");
                // Setze Daten in ein Page Objekt
                setUpPage();
                return SUCCESS;
            }
        }
       

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpPage() {
    	vacancy = vacancyDaoLocal.findByUuid(vacancyUuid);
        page = new Page();
        page.setTechnical(vacancy.getName(Language.DE));
        page.setDescriptionMap(vacancy.getDescriptionMap());
        page.setPicture(vacancy.getPicture());
    }

}// Ende class
