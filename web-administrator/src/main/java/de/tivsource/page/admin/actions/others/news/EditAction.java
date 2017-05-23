package de.tivsource.page.admin.actions.others.news;

import java.util.Date;
import java.util.List;

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
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2928952499252570880L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private News news;

    private String lang;

    public News getNews() {
        return news;
    }

	public void setNews(News news) {
        this.news = news;
    }
	
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "newsEditForm"),
        				@Result(name = "error",   type = "tiles", location = "newsEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(news != null) {
    		LOGGER.info("News UUID: " + news.getUuid());
    		News dbNews = newsDaoLocal.findByUuid(news.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                news.getContentMap().put(Language.DE, dbNews.getContentObject(Language.DE));
                dbNews.getContentMap().get(Language.EN).setContent(news.getContent(Language.EN));
                dbNews.getContentMap().get(Language.EN).setModified(new Date());

                news.getDescriptionMap().put(Language.DE, dbNews.getDescriptionObject(Language.DE));
                String noLineBreaks = news.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbNews.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbNews.getDescriptionMap().get(Language.EN).setKeywords(news.getKeywords(Language.EN));
                dbNews.getDescriptionMap().get(Language.EN).setName(news.getName(Language.EN));
            } else {
                dbNews.getContentMap().get(Language.DE).setContent(news.getContent(Language.DE));
                dbNews.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = news.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbNews.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbNews.getDescriptionMap().get(Language.DE).setKeywords(news.getKeywords(Language.DE));;
                dbNews.getDescriptionMap().get(Language.DE).setName(news.getName(Language.DE));
            }


    		dbNews.setModified(new Date());
    		dbNews.setVisible(news.getVisible());
    		dbNews.setModifiedBy(remoteUser);
    		dbNews.setModifiedAddress(remoteAddress);
    		dbNews.setPicture(news.getPicture());
    		dbNews.setReleaseDate(news.getReleaseDate());
    		dbNews.setPictureOnPage(news.getPictureOnPage());

    		newsDaoLocal.merge(dbNews);
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
