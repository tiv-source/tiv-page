package de.tivsource.page.admin.actions.academy.course;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.CSSGroupService;
import de.tivsource.page.admin.services.CourseService;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;

/**
 * 
 * @author Marc Michele
 *
 */
public class UnitDeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 8940055707364225508L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(UnitDeleteAction.class);

    private CourseService courseService;

    private Course course;

    private CourseUnit courseUnit;

    @Autowired
    public UnitDeleteAction(
            PropertyService propertyService,
            CourseService courseService
            ) {
        super(propertyService);
        this.courseService = courseService;
    }

    @StrutsParameter(depth=3)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @StrutsParameter(depth=3)
    public CourseUnit getCourseUnit() {
        return courseUnit;
    }

    public void setCourseUnit(CourseUnit courseUnit) {
        this.courseUnit = courseUnit;
    }

    @Override
    @Actions({
        @Action(
        		value = "unitDelete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "courseOverview.html", params = {"uncheckCourse", "%{course.uuid}"}),
        				@Result(name = "input", type="tiles", location = "courseUnitDeleteError"),
        				@Result(name = "error", type="tiles", location = "courseUnitDeleteError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
        if(course != null && courseUnit != null) {
            LOGGER.info("UUID des Course: " + course.getUuid());
            courseService.deleteCourseUnit(course.getUuid(), courseUnit.getUuid());
            return SUCCESS;
    	}// Ende if()
        // Wenn kein Kurs-Objekt vorhanden ist
        return ERROR;
    }// Ende execute()

}// Ende class
