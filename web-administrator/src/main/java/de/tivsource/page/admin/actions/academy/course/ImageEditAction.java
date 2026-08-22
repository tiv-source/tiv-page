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
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
public class ImageEditAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 2694031080543783220L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ImageEditAction.class);

    private CourseService courseService;

    private Course course;

    @Autowired
    public ImageEditAction(
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
        		value = "image", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckCourse", "%{course.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
        if(course != null) {
            LOGGER.info("UUID des Kurs Objektes: " + course.getUuid());
            if(courseService.updateCourseImage(course)) {
                return SUCCESS;             
            }
        }
   		return ERROR;
    }// Ende execute()

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