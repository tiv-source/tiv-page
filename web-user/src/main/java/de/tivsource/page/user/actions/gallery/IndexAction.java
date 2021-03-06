package de.tivsource.page.user.actions.gallery;

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
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.interfaces.Pagination;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="galleryList", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/gallery_list.jsp")
  })
})
public class IndexAction extends EmptyAction implements Pagination {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 3876303997272325669L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private List<Gallery> gallery;

    private Page page;

    /**
     * Attribut das die maximale Anzahl von Objekten enthält, die in der Liste
     * enthalten seien sollen. Dieser Wert ist mit 7 vorbelegt und sollte aus
     * der Datenbank geholt werden.
     */
    private Integer to;

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
    public void prepare() {
        // Lade die Galerie Seite aus der Datenbank
        page = pageDaoLocal.findByTechnical("gallery");
    }

    @Override
    @Actions({
        @Action(value = "index", results = {
            @Result(name = "success", type = "tiles", location = "galleryList"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        /*
         * Ermittle ob die Galeriefunktion der Webseite angeschaltet wurde.
         */
        boolean galleryPageEnabled = getProperty("gallery.page.enabled").equals("true") ? true : false;
        if(!galleryPageEnabled) {
            return ERROR;
        }

        // Hole attribute to aus Datenbank 
        to = Integer.parseInt(getProperty("gallery.list.quantity"));

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
        gallery = galleryDaoLocal.findAllVisible(from, to);
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

    public List<Gallery> getGallery() {
		return gallery;
	}

    private void getDBCount() {
        LOGGER.debug("getDBCount() aufgerufen.");
        dbQuantity = galleryDaoLocal.countAllVisible();
        LOGGER.debug("DbQuantity: " + dbQuantity);
        // Berechne die Maximal mögliche Seitenzahl
        maxPages = (dbQuantity % to == 0) ? (dbQuantity / to) : (dbQuantity / to) + 1;
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
            from = (pagination - 1) * to;
            current = pagination;
        }
    }// Ende calculate()

    
}// Ende class
