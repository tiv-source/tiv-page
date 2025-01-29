package de.tivsource.page.admin.actions.others.vacancy;

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
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.entity.vacancy.Vacancy;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="vacancyAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/vacancy/add_form.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 9114559679951589800L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Vacancy vacancy;

    private String lang;

    private List<Location> locationList;

    private List<CSSGroup> cssGroupList;

	public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        locationList = locationDaoLocal.findAll(0, locationDaoLocal.countAll());
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "vacancyAddForm"),
        				@Result(name = "error", type="tiles", location = "vacancyAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(vacancy != null) {
    	    vacancy.setUuid(UUID.randomUUID().toString());
    	    vacancy.setModified(new Date());
    	    vacancy.setCreated(new Date());
    	    vacancy.setModifiedBy(remoteUser);
    	    vacancy.setModifiedAddress(remoteAddress);

    	    vacancy.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    vacancy.getDescriptionMap().get(Language.DE).setNamingItem(vacancy);
    	    vacancy.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = vacancy.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    vacancy.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    vacancy.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    vacancy.getContentMap().get(Language.DE).setContentItem(vacancy);
    	    vacancy.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    vacancy.getContentMap().get(Language.DE).setCreated(new Date());
    	    vacancy.getContentMap().get(Language.DE).setModified(new Date());

    	    vacancy.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    vacancy.getDescriptionMap().get(Language.EN).setNamingItem(vacancy);
    	    vacancy.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    vacancy.getDescriptionMap().get(Language.EN).setDescription(vacancy.getDescriptionMap().get(Language.DE).getDescription());
    	    vacancy.getDescriptionMap().get(Language.EN).setName(vacancy.getDescriptionMap().get(Language.DE).getName());
    	    vacancy.getDescriptionMap().get(Language.EN).setKeywords(vacancy.getDescriptionMap().get(Language.DE).getKeywords());

    	    vacancy.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    vacancy.getContentMap().get(Language.EN).setContentItem(vacancy);
    	    vacancy.getContentMap().get(Language.EN).setLanguage(Language.EN);
    	    vacancy.getContentMap().get(Language.EN).setCreated(new Date());
    	    vacancy.getContentMap().get(Language.EN).setModified(new Date());
    	    vacancy.getContentMap().get(Language.EN).setContent(vacancy.getContentMap().get(Language.DE).getContent());

    	    vacancy.setTechnical("VACANCY_" + vacancy.getUuid());

    	    vacancy.getImage().setUuid(UUID.randomUUID().toString());
    	    vacancy.getImage().generate();
    	    vacancy.getImage().setCreated(new Date());
    	    vacancy.getImage().setModified(new Date());
    	    vacancy.getImage().setModifiedAddress(remoteAddress);
    	    vacancy.getImage().setModifiedBy(remoteUser);

    	    vacancyDaoLocal.merge(vacancy);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    public List<Location> getLocationList() {
        return locationList;
    }// Ende getLocationList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.vacancy = new Vacancy();
            this.vacancy.setImage(new PictureItemImage());
            this.vacancy.getImage().setPictureItem(this.vacancy);
            this.vacancy.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
