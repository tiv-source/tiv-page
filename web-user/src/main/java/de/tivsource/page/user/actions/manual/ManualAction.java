package de.tivsource.page.user.actions.manual;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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

    private String manualUuid;
    
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

        // Hole Action Locale
        this.getLanguageFromActionContext();

        manualUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("ManualUuid: " + manualUuid);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        manualUuid = manualUuid.replaceAll("/index.html", "");
        manualUuid = manualUuid.replaceAll("/manual/", "");
            
        LOGGER.info("ManualUuid: " + manualUuid);

        /*
         * Wenn die Manual Uuid keine nicht erlaubten Zeichen enthält und es
         * das Manual mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(manualUuid) && manualDaoLocal.isManualUuid(manualUuid)) {
            LOGGER.info("gültige Manual Uuid.");

            manual = manualDaoLocal.findByUuid(manualUuid);

            // Setze Daten in ein Page Objekt
            setUpPage();

            return SUCCESS;
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    public Manual getManual() {
        return manual;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpPage() {
        page = new Page();
        page.setDescriptionMap(manual.getDescriptionMap());
        page.setPicture(manual.getPicture());
    }

}// Ende class
