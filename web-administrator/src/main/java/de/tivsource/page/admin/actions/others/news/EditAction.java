package de.tivsource.page.admin.actions.others.news;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.news.News;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="newsEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/news/edit_form.jsp")
  }),
  @TilesDefinition(name="newsEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/news/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2928952499252570880L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private News news;

    private String lang;
    
    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public News getNews() {
        return news;
    }

	public void setNews(News news) {
        this.news = news;
    }
	
    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
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
    		dbNews.setReleaseDate(news.getReleaseDate());
    		dbNews.setPictureOnPage(news.getPictureOnPage());
    		dbNews.setCssGroup(news.getCssGroup());

    		newsDaoLocal.merge(dbNews);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }

}// Ende class
