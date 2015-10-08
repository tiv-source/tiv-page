package de.tivsource.page.user.actions;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.page.Page;

public class PageAction extends EmptyAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6236431708460575442L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger("INFO");

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    /**
     * Seiten-Name im Pfad (Achtung kann duch den Benutzer manipuliert werden).
     */
    private String pageName;

    private Page page;

    @Override
    @Actions({
        @Action(value = "*/*", results = {
            @Result(name = "success", type = "tiles", location = "page"),
            @Result(name = "error", type = "redirectAction", location = "index.html") })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        pageName = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("PageName: " + pageName);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        pageName = pageName.replaceAll("/index.html", "");
        pageName = pageName.replaceAll("/", "");
            
        LOGGER.info("PageName: " + pageName);

        /*
         * Wenn der Seiten-Name keine nicht erlaubten Zeichen enthält und es
         * die Seite mit dem Namen gibt dann wird der Block ausgeführt.
         */
        if (isValid(pageName) && pageDaoLocal.isPageUrl(pageName)) {
            page = pageDaoLocal.findByTechnical(pageName);
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

    private Boolean isValid(String input) {
        if (Pattern.matches("[a-z]*", input)) {
            return true;
        } else {
            return false;
        }
    }

}// Ende class
