package de.tivsource.page.admin.actions.vacancy;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * 
 * @author Marc Michele
 *
 */
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -7248680606365373566L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(FormAction.class);

	@InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

	private Vacancy vacancy;

	private String uncheckVacancy;

	private String lang;

	public Vacancy getVacancy() {
        return vacancy;
    }

	public void setVacancy(String uncheckVacancy) {
        this.uncheckVacancy = uncheckVacancy;
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
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<Location> getLocationList() {
        // TODO: Anzahl der Objkte anpassen
        return locationDaoLocal.findAll(0, locationDaoLocal.countAll());
    }// Ende getLocationList()

	private void loadPageParameter() {

		if( uncheckVacancy != null && uncheckVacancy != "" && uncheckVacancy.length() > 0) {
			vacancy = vacancyDaoLocal.findByUuid(uncheckVacancy);
		} else {
			vacancy = new Vacancy();
		}

	}// Ende loadPageParameter()

}// Ende class
