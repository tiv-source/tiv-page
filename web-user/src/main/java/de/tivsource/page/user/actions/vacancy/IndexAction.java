package de.tivsource.page.user.actions.vacancy;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.vacancy.Vacancy;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -3783192653104896023L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(IndexAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    private Page page;

    @Override
    @Actions({
        @Action(
        		value = "index", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "vacancy"),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	// Hole Action Locale
    	this.getLanguageFromActionContext();

    	page = pageDaoLocal.findByTechnical("vacancy");

    	boolean contactPageEnabled = propertyDaoLocal.findByKey("vacancy.page.enabled").getValue().equals("true") ? true : false;

    	if(contactPageEnabled) {
            return SUCCESS;
    	} 
    	
    	return ERROR;
    }// Ende execute()

    public Page getPage() {
    	return page;
    }// Ende getPage()

    public List<Vacancy> getList() {
        return vacancyDaoLocal.findAll(0, vacancyDaoLocal.countAll());
    }

}// Ende class