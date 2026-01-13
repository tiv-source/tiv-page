package de.tivsource.page.user.actions.lecturer;

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
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.lecturer.Lecturer;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="lecturerView", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",    value = "/WEB-INF/tiles/active/meta/lecturer_view.jsp"),
    @TilesPutAttribute(name = "css",     value = "/WEB-INF/tiles/active/css/lecturer.jsp"),
    @TilesPutAttribute(name = "twitter", value = "/WEB-INF/tiles/active/twitter/lecturer_view.jsp"),
    @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/lecturer/lecturer_view.jsp")
  })
})
public class LecturerAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4464777215754659674L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(LecturerAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="LecturerDao")
    private LecturerDaoLocal lecturerDaoLocal;

    /**
     * Location Uuid im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String lecturerTechnical;

    private Lecturer lecturer;
    
    public Lecturer getLecturer() {
        return lecturer;
    }

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "lecturerView"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.lecturer").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Lese UUID aus dem ServletRequest
            lecturerTechnical = ServletActionContext.getRequest().getServletPath();
            LOGGER.info("LecturerUuid: " + lecturerTechnical);
            // Lösche unbenötigte Teile aus dem Pfad
            lecturerTechnical = lecturerTechnical.replaceAll("/index.html", "");
            lecturerTechnical = lecturerTechnical.replaceAll("/lecturer/", "");
            LOGGER.info("LecturerUuid: " + lecturerTechnical);

            /*
             * Wenn die Location Uuid keine nicht erlaubten Zeichen enthält und es
             * die Location mit der Uuid gibt dann wird der Block ausgeführt.
             */
            if (isValid(lecturerTechnical) && lecturerDaoLocal.isLecturerUrl(lecturerTechnical)) {
                LOGGER.info("Gültiger String in lecturerTechnical.");
                lecturer = lecturerDaoLocal.findByTechnical(lecturerTechnical);
                return SUCCESS;
            }
            LOGGER.info("Manipulationsversuch in lecturerTechnical.");
            // Wenn es einen Manipulationsversuch gab.
            return ERROR;
        }
        LOGGER.info("Lecturer Modul nicht aktiviert.");
        // Wenn das Modul nicht aktiviert ist.
        return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return null;
    }

	private Boolean isValid(String input) {
        if (Pattern.matches("[a-zA-Z0-9-]*", input)) {
            LOGGER.trace("Gültiger String in lecturerTechnical: " + input);
            return true;
        } else {
            LOGGER.trace("Ungültiger String in lecturerTechnical: " + input);
            return false;
        }
    }

}// Ende class
