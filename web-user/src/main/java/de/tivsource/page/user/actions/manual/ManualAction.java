package de.tivsource.page.user.actions.manual;

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
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="manual", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",    value = "/WEB-INF/tiles/active/meta/manual.jsp"),
    @TilesPutAttribute(name = "twitter", value = "/WEB-INF/tiles/active/twitter/manual.jsp"),
    @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/manual/manual.jsp")
  })
})
public class ManualAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 6773842640719364262L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ManualAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    private Manual manual;

    private String manualTechnical;
    
    private Page page;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "manual"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.manual").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Lese UUID aus dem ServletRequest
            manualTechnical = ServletActionContext.getRequest().getServletPath();
            LOGGER.info("ManualTechnical: " + manualTechnical);
            manualTechnical = manualTechnical.replaceAll("/index.html", "");
            manualTechnical = manualTechnical.replaceAll("/manual/", "");
            LOGGER.info("ManualTechnical: " + manualTechnical);

            /*
             * Wenn die Manual Uuid keine nicht erlaubten Zeichen enthält und es
             * das Manual mit der Uuid gibt dann wird der Block ausgeführt.
             */
            if (isValidTechnical(manualTechnical) && manualDaoLocal.isManualUrl(manualTechnical)) {
                LOGGER.info("gültige Manual Uuid.");

                manual = manualDaoLocal.findByTechnical(manualTechnical);

                // Setze Daten in ein Page Objekt
                setUpPage();

                return SUCCESS;
            }

            // Wenn es einen Manipulationsversuch gab.
            return ERROR;
        } else {
            // Wenn das Module nicht aktiviert ist.
            return ERROR;
        }

    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    public Manual getManual() {
        return manual;
    }

    private Boolean isValidTechnical(String input) {
        if (Pattern.matches("[a-z0-9]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpPage() {
        page = new Page();
        page.setTechnical(manual.getTechnical());
        page.setDescriptionMap(manual.getDescriptionMap());
        page.setPicture(manual.getPicture());
        page.setPictureOnPage(manual.getPictureOnPage());
        page.setCssGroup(manual.getCssGroup());
    }

}// Ende class
