package de.tivsource.page.admin.actions.academy.course;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.CourseService;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.entity.course.Course;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 2771909836675147763L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    private CourseService courseService;

    private Course course;

    @Autowired
    public DeleteAction(
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

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "courseDeleteError"),
        				@Result(name = "error", type="tiles", location = "courseDeleteError")
        				}
        )
    })
    public String execute() {
    	LOGGER.info("execute() aufgerufen.");
    	if(course != null) {
    	    if(courseService.deleteCourse(course)) {
    	        return SUCCESS;
    	    }
    	}
    	return ERROR;
    }// Ende execute()
	
}// Ende class
