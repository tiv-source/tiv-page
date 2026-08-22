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
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;
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
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/unit_add_form.jsp")
  })
})
public class UnitAddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2526274080442355458L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(UnitAddAction.class);

    private CourseService courseService;

    private CSSGroupService cssGroupService;

    private Course course;

    private CourseUnit courseUnit;

    @Autowired
    public UnitAddAction(
            PropertyService propertyService,
            CourseService courseService,
            CSSGroupService cssGroupService
            ) {
        super(propertyService);
        this.courseService = courseService;
        this.cssGroupService = cssGroupService;
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
    public void prepare() {
        super.prepare();
    }

    @Override
    @Actions({
        @Action(
        		value = "unitAdd", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "courseOverview.html", params = {"uncheckCourse", "%{course.uuid}"}),
        				@Result(name = "input", type="tiles", location = "courseAddForm"),
        				@Result(name = "error", type="tiles", location = "courseAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	if(course != null && courseUnit != null) {
    	    LOGGER.info("UUID des Kures: " + course.getUuid());
    	    if(courseService.addCourseUnit(course.getUuid(), courseUnit)) {
                return SUCCESS;
    	    }
    	}
   		return ERROR;
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.debug("getCssGroupList() aufgerufen.");
        List<CSSGroup> cssGroupList = cssGroupService.getAllCSSGroups();
        LOGGER.debug("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.courseUnit = new CourseUnit();
            this.courseUnit.setImage(new PictureItemImage());
            this.courseUnit.getImage().setPictureItem(this.courseUnit);
            this.courseUnit.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
