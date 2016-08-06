package de.tivsource.page.admin.actions.others.news;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.entity.news.News;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 173705414287709097L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    private News news;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "newsDeleteForm"),
        				@Result(name = "error", type="tiles", location = "newsDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(news != null) {
    		News dbNews = newsDaoLocal.findByUuid(news.getUuid());
    		dbNews.setModified(new Date());
    		dbNews.setModifiedBy(remoteUser);
    		dbNews.setModifiedAddress(remoteAddress);
    		newsDaoLocal.merge(dbNews);
    		newsDaoLocal.delete(dbNews);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()
	
}// Ende class
