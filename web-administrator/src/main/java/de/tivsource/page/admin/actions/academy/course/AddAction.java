package de.tivsource.page.admin.actions.academy.course;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.lecturer.Lecturer;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="courseAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/add_form.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7370115357881013870L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="LecturerDao")
    private LecturerDaoLocal lecturerDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Course course;

    private List<CSSGroup> cssGroupList;

    private List<Lecturer> lecturerList;

    private List<Location> locationList;

    @Autowired
    public AddAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=3)
	public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
        lecturerList = lecturerDaoLocal.findAll(0, lecturerDaoLocal.countAll());
        locationList = locationDaoLocal.findAll(0, locationDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "courseAddForm"),
        				@Result(name = "error", type="tiles", location = "courseAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(course != null) {
    	    course.setUuid(UUID.randomUUID().toString());
    	    course.setModified(new Date());
    	    course.setCreated(new Date());
    	    course.setModifiedBy(remoteUser);
    	    course.setModifiedAddress(remoteAddress);

    	    course.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    course.getDescriptionMap().get(Language.DE).setNamingItem(course);
    	    course.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = course.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    course.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    course.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    course.getContentMap().get(Language.DE).setContentItem(course);
    	    course.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    course.getContentMap().get(Language.DE).setCreated(new Date());
    	    course.getContentMap().get(Language.DE).setModified(new Date());

    	    course.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    course.getDescriptionMap().get(Language.EN).setNamingItem(course);
    	    course.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    course.getDescriptionMap().get(Language.EN).setDescription(course.getDescriptionMap().get(Language.DE).getDescription());
    	    course.getDescriptionMap().get(Language.EN).setName(course.getDescriptionMap().get(Language.DE).getName());
    	    course.getDescriptionMap().get(Language.EN).setKeywords(course.getDescriptionMap().get(Language.DE).getKeywords());

    	    course.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    course.getContentMap().get(Language.EN).setContentItem(course);
    	    course.getContentMap().get(Language.EN).setLanguage(Language.EN);
    	    course.getContentMap().get(Language.EN).setCreated(new Date());
    	    course.getContentMap().get(Language.EN).setModified(new Date());
    	    course.getContentMap().get(Language.EN).setContent(course.getContentMap().get(Language.DE).getContent());

    	    course.getImage().setUuid(UUID.randomUUID().toString());
    	    course.getImage().generate();
    	    course.getImage().setCreated(new Date());
    	    course.getImage().setModified(new Date());
    	    course.getImage().setModifiedAddress(remoteAddress);
    	    course.getImage().setModifiedBy(remoteUser);

    	    course.setTechnical("Course_" + course.getUuid());

    	    SortedSet<Lecturer> lecturers = course.getLecturers();
    	    course.setLecturers(null);
    	    courseDaoLocal.merge(course);

            // Update das LecturerSet
            LOGGER.info("Course LecturerSet Anzahl: " + lecturers.size());
            courseDaoLocal.updateLecturerSet(course, lecturers);
            LOGGER.info("Course: " + course.getName("de") + " LecturerSet erfolgreich aktualisiert.");

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

    public List<Lecturer> getLecturerList() {
        return lecturerList;
    }// Ende getLecturerList()

    public List<Location> getLocationList() {
        return locationList;
    }// Ende getLocationList()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.course = new Course();
            this.course.setImage(new PictureItemImage());
            this.course.getImage().setPictureItem(this.course);
            this.course.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
