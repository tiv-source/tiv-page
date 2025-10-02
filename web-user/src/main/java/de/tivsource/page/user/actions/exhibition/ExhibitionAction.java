package de.tivsource.page.user.actions.exhibition;

import java.util.Date;
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
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.exhibition.Exhibition;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="exhibition", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/exhibition.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/exhibition.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/exhibition.jsp")
  })
})
public class ExhibitionAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 7971933775814365850L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ExhibitionAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    private Exhibition exhibition;

    private String exhibitionTechnical;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "exhibition"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        exhibitionTechnical = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Exhibition Technical: " + exhibitionTechnical);

        exhibitionTechnical = exhibitionTechnical.replaceAll("/index.html", "");
        exhibitionTechnical = exhibitionTechnical.replaceAll("/exhibition/", "");

        LOGGER.info("Exhibition Technical: " + exhibitionTechnical);

        /*
         * Wenn die Angabe des Exhibition Technical keine nicht erlaubten Zeichen enthält und es
         * das Exhibition Objekt mit dem Technical gibt dann wird der Block ausgeführt.
         */
        if (isValid(exhibitionTechnical) && exhibitionDaoLocal.isExhibitionTechnical(exhibitionTechnical)) {
            LOGGER.info("gültiges Exhibition Technical.");
            exhibition = exhibitionDaoLocal.findByTechnical(exhibitionTechnical);
            
            /*
             * Wenn das Exhibition Objekt sichbar sein soll und auch schon das
             * Anzeigedtaum erreicht hat wird dieser Block ausgefüht.
             */
            if(exhibition.getVisible() && new Date().after(exhibition.getVisibleFrom())) {
                // Setze Daten in ein Page Objekt
                return SUCCESS;
            }
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    public Exhibition getExhibition() {
        return exhibition;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[a-z0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class
