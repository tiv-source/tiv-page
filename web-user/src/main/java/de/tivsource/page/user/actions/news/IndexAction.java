package de.tivsource.page.user.actions.news;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.news.News;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.interfaces.Pagination;

public class IndexAction extends EmptyAction implements Pagination {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 3876303997272325669L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    /**
     * Attribut das die maximal Anzahl der Liste enthält. 
     */
    private static final Integer TO = 7;

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    private List<News> news;

    private Page page;

    private Integer next;
    private Integer previous;
    private Integer current;

    /**
     * Attribut das den Startpunkt der Liste enthält.
     */
    private Integer from;

    /**
     * Angefordete Seitenzahl (Achtung kann durch den benutzer manipuliert werden). 
     */
    private Integer pagination;

    /**
     * Attribut das die Anzahl der Objekte in der Datenbank enthält.
     */
    private Integer dbQuantity;

    /**
     * Attribute das die maximal mögliche Anzahl an Seiten enthält.
     */
    private Integer maxPages;

    @Override
    @Actions({
        @Action(value = "index", results = {
            @Result(name = "success", type = "tiles", location = "newsList"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.news").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Setze Daten in ein Page Objekt
            setUpPage();

            // Hole die Anzahl aus der Datenbank
            this.getDBCount();

            // Wenn page nicht gesetzt wurde
            if(pagination == null) {
                pagination = 1;
            }

            //  Wenn page größer als maxPages ist.
            if(pagination > maxPages) {
                pagination = 1;
            }

            // Kalkuliere die Seiten
            this.calculate();

            news = newsDaoLocal.findAllVisible(from, TO);
            return SUCCESS;            
        } else {
            // Wenn das Module nicht aktiviert ist.
            return ERROR;
        }

        
    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public Integer getNext() {
        return next;
    }

    @Override
    public Integer getPrevious() {
        return previous;
    }

    @Override
    public Integer getCurrent() {
        return current;
    }

    @Override
    public void setPage(Integer extpage) {
        pagination = extpage;
    }

    public List<News> getNews() {
		return news;
	}

	private void setUpPage() {
    	page = pageDaoLocal.findByTechnical("news");
    }

    private void getDBCount() {
        LOGGER.debug("getDBCount() aufgerufen.");
        dbQuantity = newsDaoLocal.countAllVisible();
        LOGGER.debug("DbQuantity: " + dbQuantity);
        // Berechne die Maximal mögliche Seitenzahl
        maxPages = (dbQuantity % TO == 0) ? (dbQuantity / TO) : (dbQuantity / TO) + 1;
        LOGGER.debug("MaxPages: " + maxPages);
    }// Ende getDBCount()

    /**
     * Methode die Start und Enpunkt der Liste und die vorherige beziehungweise
     * die nächste Seitenzahl berechnet.
     */
    private void calculate() {
        if(pagination == 1) {
            previous = null;
            next = (2 <= maxPages) ? 2 : null;
            from = 0;
            current = pagination;
        } else {
            previous = pagination -1;
            next = (pagination + 1 <= maxPages) ? pagination + 1 : null;
            from = (pagination - 1) * TO;
            current = pagination;
        }
    }// Ende calculate()

    
}// Ende class
