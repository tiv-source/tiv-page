package de.tivsource.page.user.actions.subsumption;

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
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.subsumption.Subsumption;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="subsumption", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",    value = "/WEB-INF/tiles/active/meta/subsumption.jsp"),
    @TilesPutAttribute(name = "twitter", value = "/WEB-INF/tiles/active/twitter/subsumption.jsp"),
    @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/subsumption/subsumption.jsp")
  })
})
public class SubsumptionAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1181728157641483645L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SubsumptionAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="SubsumptionDao")
    private SubsumptionDaoLocal subsumptionDaoLocal;

    private Subsumption subsumption;

    private String subsumptionTechnical;
    
    private Page page;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "subsumption"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        // Lese UUID aus dem ServletRequest
        subsumptionTechnical = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("SubsumptionTechnical: " + subsumptionTechnical);
        subsumptionTechnical = subsumptionTechnical.replaceAll("/index.html", "");
        subsumptionTechnical = subsumptionTechnical.replaceAll("/subsumption/", "");
        LOGGER.info("SubsumptionTechnical: " + subsumptionTechnical);

        /*
         * Wenn die SubsumptionTechnical keine nicht erlaubten Zeichen enthält und es
         * das Subsumption Objekt mit dem Technical Name gibt dann wird der Block ausgeführt.
         */
        if (isValid(subsumptionTechnical) && subsumptionDaoLocal.isSubsumptionUrl(subsumptionTechnical)) {
            LOGGER.info("gültige Manual Uuid.");
            subsumption = subsumptionDaoLocal.findByTechnical(subsumptionTechnical);
            if(subsumption.getVisible()) {
                // Setze Daten in ein Page Objekt
                page = new Page();
                page.setTechnical(subsumption.getTechnical());
                page.setDescriptionMap(subsumption.getDescriptionMap());
                page.setCssGroup(subsumption.getCssGroup());
                return SUCCESS;
            }
        }
        // Wenn es einen Manipulationsversuch gab oder das Objekt nicht sichbar ist.
        return ERROR;

    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    public Subsumption getSubsumption() {
        return subsumption;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[a-z]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class
