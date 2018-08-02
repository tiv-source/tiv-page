package de.tivsource.page.user.actions.gallery;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.interfaces.Pagination;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="gallery", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/gallery.jsp")
  })
})
public class GalleryAction extends EmptyAction implements Pagination {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -4466409845775558651L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(GalleryAction.class);

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Page page;

    private Gallery gallery;

    private List<Picture> pictures;
    
    /**
	 * Wird durch den Benutzer beeinflusst, es sollte vor der Verwendung auf
	 * Sonderzeichen getestet werden.
	 */
    private String pathUuid;

    /**
     * Kann durch den Benutzer manipuliert werden, der wert sollte vor der
     * Verwendung überprüft werden.
     */ 
    private Integer requestedPage;

    /**
     * Anzahl der Bilder aus der Datenbank ermittlet.
     */
    private Integer pictureCount;

    /**
     * Attribut das die maximal Anzahl der Liste enthält. 
     */
    private Integer maxElements = 5;

    private Integer next;
    private Integer previous;
    private Integer current;

    /**
     * Attribut das den Startpunkt der Liste enthält.
     */
    private Integer from;

    /**
     * Attribute das die maximal mögliche Anzahl an Seiten enthält.
     */
    private Integer maxPages;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "gallery"),
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

        /*
         * Ermittle Wert des Attributes maxElements aus Datenbank, versuche
         * die Eigenschaft gallery.page.max.pictures zu laden.
         */
        if(getProperty("gallery.overview.list.quantity") != null) {
            maxElements = Integer.parseInt(getProperty("gallery.overview.list.quantity"));
        }


        pathUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("UUID from Path: " + pathUuid);

        // /gallery/index.html?page=1&request_locale=de
        pathUuid = pathUuid.replaceAll("/index.html", "");
        pathUuid = pathUuid.replaceAll("/gallery/", "");
            
        LOGGER.info("UUID from Path: " + pathUuid);

        /*
         * Wenn das Attribute pathUuid keine nicht erlaubten Zeichen enthält und
         * es eine Galerie mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(pathUuid) && galleryDaoLocal.isGallery(pathUuid)) {
            LOGGER.info("gültige Galerie UUID.");

            // Lade das Galerie Objekt aus der Datenbank
            gallery = galleryDaoLocal.findByUuid(pathUuid);

            // Ermittle die Anzahl der Bilder in der Galerie
            pictureCount = pictureDaoLocal.countAllInGallery(gallery.getUuid());
            LOGGER.info("Inhalt Attribute pictureCount:" + pictureCount);

            // Kalkuliere Zähler Werte
            calculate();

            LOGGER.info("Inhalt Attribute from:" + from);

            // TODO: Neue Methode für die Abfrage
            pictures = pictureDaoLocal.findAll(from, maxElements, gallery.getUuid(), "p.orderNumber, p.uuid", "desc");

            page = new Page();
            page.setTechnical("gallery_01");
            page.setDescriptionMap(gallery.getDescriptionMap());

            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

	public Page getPage() {
        return page;
    }

    public Gallery getGallery() {
		return gallery;
	}

	public List<Picture> getPictures() {
		return pictures;
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
    public void setPage(Integer page) {
        this.requestedPage = page;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Methode die Start und Enpunkt der Liste und die vorherige beziehungweise
     * die nächste Seitenzahl berechnet.
     */
    private void calculate() {
        maxPages = (pictureCount % maxElements == 0) ? (pictureCount / maxElements)
                : (pictureCount / maxElements) + 1;

        // Wenn page nicht gesetzt wurde
        if(requestedPage == null) {
            requestedPage = 1;
        }

        //  Wenn page größer als maxPages ist.
        if(requestedPage > maxPages) {
            requestedPage = 1;
        }
        
        if(requestedPage == 1) {
            previous = null;
            next = (2 <= maxPages) ? 2 : null;
            from = 0;
            current = 1;
        } else {
            previous = requestedPage -1;
            next = (requestedPage + 1 <= maxPages) ? requestedPage + 1 : null;
            from = (requestedPage - 1) * maxElements;
            current = requestedPage;
        }
    }// Ende calculate()

}// Ende class
