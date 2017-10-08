package de.tivsource.page.user.actions.companion;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.companion.Companion;
import de.tivsource.page.entity.companion.CompanionGroup;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

public class GroupAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6773446792537275637L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(GroupAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="CompanionDao")
    private CompanionDaoLocal companionDaoLocal;

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    /**
     * Seiten-Name im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String pageName;

    private CompanionGroup companionGroup;

    private Page page;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "companionGroup"),
            @Result(name = "error", type = "redirectAction", location = "index.html") })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        page = pageDaoLocal.findByTechnical("companion");
        
        pageName = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("PageName: " + pageName);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        pageName = pageName.replaceAll("/index.html", "");
        pageName = pageName.replaceAll("/companion/", "");
            
        LOGGER.info("PageName: " + pageName);

        /*
         * Wenn der Seiten-Name keine nicht erlaubten Zeichen enthält und es
         * die Seite mit dem Namen gibt dann wird der Block ausgeführt.
         */
        if (isValid(pageName) && companionGroupDaoLocal.isCompanionGroupTechnical(pageName)) {
            companionGroup = companionGroupDaoLocal.findByTechnical(pageName);
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
    }// Ende getPage()
    
    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

    public List<CompanionGroup> getGroupList() {
        return companionGroupDaoLocal.findAllVisible(0, companionGroupDaoLocal.countAllVisible());
    }
    
    public List<Companion> getList() {
        return companionDaoLocal.findAllVisible(0, companionDaoLocal.countAllVisible(), companionGroup);
    }
    
    private Boolean isValid(String input) {
        if (Pattern.matches("[a-z]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class
