package de.tivsource.page.user.actions.news;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.news.News;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

public class NewsAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -4466409845775558651L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(NewsAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    private News news;

    private String newsUuid;
    
    private Page page;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "news"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.news").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled) {
            // Hole Action Locale
            this.getLanguageFromActionContext();

            // Lese UUID aus dem ServletRequest
            newsUuid = ServletActionContext.getRequest().getServletPath();
            LOGGER.info("NewsUuid: " + newsUuid);
            newsUuid = newsUuid.replaceAll("/index.html", "");
            newsUuid = newsUuid.replaceAll("/news/", "");
            LOGGER.info("NewsUuid: " + newsUuid);

            /*
             * Wenn die News Uuid keine nicht erlaubten Zeichen enthält, es die News
             * mit der Uuid gibt, diese sichtbar ist und das Veröffentlichungesdatum
             * erreicht wurde, dann wird der Block ausgeführt.
             */
            if (isValid(newsUuid) && newsDaoLocal.isPublicNewsUuid(newsUuid)) {
                LOGGER.info("gültige News Uuid.");

                news = newsDaoLocal.findByUuid(newsUuid);

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

    public News getNews() {
        return news;
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
        page.setDescriptionMap(news.getDescriptionMap());
        page.setPicture(news.getPicture());
    }

}// Ende class
