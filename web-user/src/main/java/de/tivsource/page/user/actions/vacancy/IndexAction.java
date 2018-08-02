package de.tivsource.page.user.actions.vacancy;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

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
@TilesDefinitions({
  @TilesDefinition(name="vacancy", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/vacancy.jsp")
  })
})
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -3783192653104896023L;

	/**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

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

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.vacancy").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Hole Seite aus der Datenbank
            page = pageDaoLocal.findByTechnical("vacancy");
            return SUCCESS;
        } else {
            // Wenn das Module nicht aktiviert ist.
            return ERROR;
        }
    }// Ende execute()

    public Page getPage() {
    	return page;
    }// Ende getPage()

    public Date getNow() {
        return new Date();
    }

    public List<Vacancy> getList() {
        return vacancyDaoLocal.findAllVisible(0, vacancyDaoLocal.countAllVisible());
    }

}// Ende class