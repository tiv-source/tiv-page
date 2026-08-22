package de.tivsource.page.admin.actions.academy.course;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.CSSGroupService;
import de.tivsource.page.admin.services.CourseService;
import de.tivsource.page.admin.services.LecturerService;
import de.tivsource.page.admin.services.LocationService;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.entity.course.Course;
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

    private CourseService courseService;

    private CSSGroupService cssGroupService;

    private LecturerService lecturerService;

    private LocationService locationService;

    private Course course;

    @Autowired
    public AddAction(
            PropertyService propertyService, 
            CourseService courseService, 
            CSSGroupService cssGroupService,
            LecturerService lecturerService,
            LocationService locationService
            ) {
        super(propertyService);
        this.courseService = courseService;
        this.cssGroupService = cssGroupService;
        this.lecturerService = lecturerService;
        this.locationService = locationService;
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
    	if(course != null) {
    	    if(courseService.createCourse(course)) {
                return SUCCESS;    	        
    	    }
    	}
    	// Wenn es einen Fehler gab zum Beispiel das
    	// Objekt nicht gespeichert werden konnte
  		return ERROR;
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.debug("getCssGroupList() aufgerufen.");
        List<CSSGroup> cssGroupList = cssGroupService.getAllCSSGroups();
        LOGGER.debug("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    public List<Lecturer> getLecturerList() {
        return lecturerService.getAllLecturer();
    }// Ende getLecturerList()

    public List<Location> getLocationList() {
        return locationService.getAllLocations();
    }// Ende getLocationList()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.debug("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.debug("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.course = new Course();
            this.course.setImage(new PictureItemImage());
            this.course.getImage().setPictureItem(this.course);
            this.course.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
