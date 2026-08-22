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
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.CourseService;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
public class UnitImageEditAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 1638556336051986903L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(UnitImageEditAction.class);

    private CourseService courseService;

    private Course course;

    private CourseUnit courseUnit;

    private UploadedFile uploadedFile;

    @Autowired
    public UnitImageEditAction(
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
        		value = "unitImage", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "courseOverview.html", params = {"uncheckCourse", "%{course.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
        if(course != null && courseUnit != null) {
            LOGGER.info("UUID des Course: " + course.getUuid());
            if(courseService.updateCourseUnitImage(
                    course.getUuid(), 
                    courseUnit.getUuid(), 
                    UploadedFileToUploadFile.convert(uploadedFile)
            )) {
                return SUCCESS;
            }// Ende if()
        }// Ende if()
        // Wenn kein Kurs-Objekt vorhanden ist
        return ERROR;
    }// Ende execute()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            uploadedFile = uploadedFiles.get(0);
         }
    }

}// Ende class