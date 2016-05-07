package de.tivsource.page.admin.actions.news;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.news.News;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6033997203518511979L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

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
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "newsAddForm"),
        				@Result(name = "error", type="tiles", location = "newsAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(news != null) {
    	    news.setUuid(UUID.randomUUID().toString());
    	    news.setModified(new Date());
    	    news.setCreated(new Date());
    	    news.setModifiedBy(remoteUser);
    	    news.setModifiedAddress(remoteAddress);


    	    news.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    news.getDescriptionMap().get(Language.DE).setNamingItem(news);
    	    news.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);

    	    news.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    news.getContentMap().get(Language.DE).setContentItem(news);
    	    news.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    news.getContentMap().get(Language.DE).setCreated(new Date());
    	    news.getContentMap().get(Language.DE).setModified(new Date());


            news.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            news.getDescriptionMap().get(Language.EN).setNamingItem(news);
            news.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            news.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            news.getContentMap().get(Language.EN).setContentItem(news);
            news.getContentMap().get(Language.EN).setLanguage(Language.EN);
            news.getContentMap().get(Language.EN).setCreated(new Date());
            news.getContentMap().get(Language.EN).setModified(new Date());
    	    
    		newsDaoLocal.merge(news);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("f8fed35d-6df2-4d74-835d-fcf64faf2b5a");
	}

}// Ende class
