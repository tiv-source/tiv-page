package de.tivsource.page.user.actions.manual;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.interfaces.Pagination;

public class IndexAction extends EmptyAction implements Pagination {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 3719136247417859740L;

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

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    private List<Manual> manuals;

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
            @Result(name = "success", type = "tiles", location = "manualList"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

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
        
        // TODO: neue Methode die das Datum und das Attribute visible berücksichtigt 
        manuals = manualDaoLocal.findAll(from, TO);
        return SUCCESS;
        
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

    public List<Manual> getManuals() {
		return manuals;
	}

	private void setUpPage() {
    	page = pageDaoLocal.findByTechnical("manual");
    }

    private void getDBCount() {
        LOGGER.debug("getDBCount() aufgerufen.");
        dbQuantity = manualDaoLocal.countAll();
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
