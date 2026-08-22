package de.tivsource.page.admin.actions.academy.lecturer;

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
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.lecturer.Lecturer;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="lecturerAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/add_form.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2747750838356145329L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @Autowired
    @Qualifier("cssGroupDaoLocal")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @Autowired
    @Qualifier("lecturerDaoLocal")
    private LecturerDaoLocal lecturerDaoLocal;

    private Lecturer lecturer;

    private List<CSSGroup> cssGroupList;

    @Autowired
    public AddAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=3)
	public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
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
        				@Result(name = "input", type="tiles", location = "lecturerAddForm"),
        				@Result(name = "error", type="tiles", location = "lecturerAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(lecturer != null) {
    	    lecturer.setUuid(UUID.randomUUID().toString());
    	    lecturer.setModified(new Date());
    	    lecturer.setCreated(new Date());
    	    lecturer.setModifiedBy(remoteUser);
    	    lecturer.setModifiedAddress(remoteAddress);

    	    lecturer.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    lecturer.getDescriptionMap().get(Language.DE).setNamingItem(lecturer);
    	    lecturer.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = lecturer.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    lecturer.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    lecturer.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    lecturer.getContentMap().get(Language.DE).setContentItem(lecturer);
    	    lecturer.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    lecturer.getContentMap().get(Language.DE).setCreated(new Date());
    	    lecturer.getContentMap().get(Language.DE).setModified(new Date());

    	    lecturer.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    lecturer.getDescriptionMap().get(Language.EN).setNamingItem(lecturer);
    	    lecturer.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    lecturer.getDescriptionMap().get(Language.EN).setDescription(lecturer.getDescriptionMap().get(Language.DE).getDescription());
    	    lecturer.getDescriptionMap().get(Language.EN).setName(lecturer.getDescriptionMap().get(Language.DE).getName());
    	    lecturer.getDescriptionMap().get(Language.EN).setKeywords(lecturer.getDescriptionMap().get(Language.DE).getKeywords());

    	    lecturer.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    lecturer.getContentMap().get(Language.EN).setContentItem(lecturer);
    	    lecturer.getContentMap().get(Language.EN).setLanguage(Language.EN);
    	    lecturer.getContentMap().get(Language.EN).setCreated(new Date());
    	    lecturer.getContentMap().get(Language.EN).setModified(new Date());
    	    lecturer.getContentMap().get(Language.EN).setContent(lecturer.getContentMap().get(Language.DE).getContent());

    	    lecturer.getImage().setUuid(UUID.randomUUID().toString());
    	    lecturer.getImage().generate();
    	    lecturer.getImage().setCreated(new Date());
    	    lecturer.getImage().setModified(new Date());
    	    lecturer.getImage().setModifiedAddress(remoteAddress);
    	    lecturer.getImage().setModifiedBy(remoteUser);

    	    lecturerDaoLocal.merge(lecturer);

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
    }// Ende getCssGroupList()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.lecturer = new Lecturer();
            this.lecturer.setImage(new PictureItemImage());
            this.lecturer.getImage().setPictureItem(this.lecturer);
            this.lecturer.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
