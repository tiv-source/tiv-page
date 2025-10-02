package de.tivsource.page.admin.actions.others.news;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.news.News;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6033997203518511979L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private News news;
    
    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
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
    	    String noLineBreaks = news.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    news.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
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

            news.setTechnical("NEWS_" + news.getUuid());

            news.getImage().setUuid(UUID.randomUUID().toString());
            news.getImage().generate();
            news.getImage().setCreated(new Date());
            news.getImage().setModified(new Date());
            news.getImage().setModifiedAddress(remoteAddress);
            news.getImage().setModifiedBy(remoteUser);

            
    		newsDaoLocal.merge(news);

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

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.news = new News();
            this.news.setImage(new PictureItemImage());
            this.news.getImage().setPictureItem(this.news);
            this.news.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
