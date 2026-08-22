package de.tivsource.page.admin.actions.academy.course;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
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

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="courseEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/edit_form.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 3508446383120113438L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    private CourseService courseService;

    private CSSGroupService cssGroupService;

    private LecturerService lecturerService;

    private LocationService locationService;

    private Course course;

    private String lang;

    @Autowired
    public EditAction(
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
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "courseEditForm"),
        				@Result(name = "error",   type = "tiles", location = "courseEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	if(course != null) {
            if(courseService.updateCourse(course, lang)) {
                return SUCCESS;             
            }
    	}
    	// Wenn kein Kurs-Objekt vorhanden ist
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

}// Ende class
