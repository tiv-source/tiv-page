package de.tivsource.page.user.actions.gallery;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.interfaces.Pagination;

public class PictureAction extends EmptyAction implements Pagination {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -5967314888490364407L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PictureAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;
    
    private Gallery gallery;

    private Picture picture;
    
    /**
	 * Wird durch den Benutzer beeinflusst, es sollte vor der Verwendung auf
	 * Sonderzeichen getestet werden.
	 */
    private String pathUuid;

    /**
     * Kann durch den Benutzer manipuliert werden, der Wert sollte vor der
     * Verwendung überprüft werden.
     */ 
    private Integer requestedPicture;

    /**
     * Kann durch den Benutzer manipuliert werden, der Wert sollte vor der
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
        @Action(value = "*/*/*/index", results = {
            @Result(name = "success", type = "tiles", location = "picture"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        pathUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("UUID from Path: " + pathUuid);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        pathUuid = pathUuid.replaceAll("/index.html", "");
        pathUuid = pathUuid.replaceAll("/gallery/", "");
        
        LOGGER.info("UUID from Path: " + pathUuid);
        
        String[] parts = pathUuid.split("/");
        
        LOGGER.info("Erster Teil: " + parts[0]);

        /*
         * Überprüfung ob der Inhalt der dritten Position im Array nur Zahlen
         * enthält.
         */
        LOGGER.info("Zweiter Teil: " + parts[1]);
        if(parts.length == 3 && isNumber(parts[1])) {
            requestedPage = Integer.parseInt(parts[1]);
        } else {
            return ERROR;
        }

        /*
         * Überprüfung das Array aus 3 Postionen besteht und ob der Inhalt des
         * String Objektes an dritten Position im Array nur Zahlen besteht
         * enthält. Anschließend wird die Zahl als Integer geparst.
         */
        LOGGER.info("Dritter Teil: " + parts[2]);
        if(parts.length == 3 && isNumber(parts[2])) {
            requestedPicture = Integer.parseInt(parts[2]);
        } else {
            return ERROR;
        }

        /*
         * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
         * die Location mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(parts[0]) && galleryDaoLocal.isGallery(parts[0])) {
            LOGGER.info("gültige Gallery UUID.");

            gallery = galleryDaoLocal.findByUuid(parts[0]);

            pictureCount = pictureDaoLocal.countAllInGallery(gallery.getUuid());
            
            calculate();

            /*
             * Ermittle Wert des Attributes maxElements aus Datenbank, versuche
             * die Eigenschaft gallery.page.max.pictures zu laden.
             */
            if(propertyDaoLocal.findByKey("gallery.page.max.pictures") != null) {
                maxElements = Integer.parseInt(propertyDaoLocal.findByKey("gallery.page.max.pictures").getValue());
            }
            
            
            // TODO: Neue Methode für die Abfrage
            Integer pictureStart = requestedPage == 1 ? requestedPicture - 1 : (requestedPage - 1) * maxElements + requestedPicture - 1;
            LOGGER.info("Attribute pictureStart: " + pictureStart);
            picture = pictureDaoLocal.findCurrentPicture(pictureStart, gallery.getUuid());

            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

	public Gallery getGallery() {
		return gallery;
	}

	public Picture getPicture() {
		return picture;
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
        return requestedPicture;
    }

    @Override
    public void setPage(Integer page) {
        // Nicht nötig.
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isNumber(String input) {
        if (Pattern.matches("[0-9]*", input)) {
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
