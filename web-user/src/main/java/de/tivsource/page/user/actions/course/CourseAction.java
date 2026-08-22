package de.tivsource.page.user.actions.course;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.services.CourseService;

@TilesDefinitions({
  @TilesDefinition(name="course", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/course.jsp"),
    @TilesPutAttribute(name = "css",        value = "/WEB-INF/tiles/active/css/course.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/course.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/course.jsp")
  })
})
public class CourseAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 1967055561703739984L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CourseAction.class);

    private CourseService courseService;

    private Course course;

    private String courseUuid;

    private Boolean waiting = true;

    @Autowired
    public CourseAction(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "course"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        courseUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Course Uuid: " + courseUuid);

        // /gallery/painting/index.html?page=1&request_locale=de

        courseUuid = courseUuid.replaceAll("/index.html", "");
        courseUuid = courseUuid.replaceAll("/course/", "");
            
        LOGGER.info("Course Uuid: " + courseUuid);

        /*
         * Wenn die Course Uuid keine nicht erlaubten Zeichen enthält und es
         * das Course Objekt mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(courseUuid) && courseService.checkCourseUuid(courseUuid)) {
            LOGGER.info("gültige Course Uuid.");
            course = courseService.getCourseWithAllRelations(courseUuid);
            LOGGER.info("Course Material: " + course.getMaterial());
            /*
             * Wenn das Course Objekt sichbar sein soll und auch schon das
             * Anzeigedtaum erreicht hat wird dieser Block ausgefüht.
             */
            if(course.getVisible()) {
                if(course.getRegistrations().size() <= course.getMaxStudents()) {
                    waiting = false;
                }
                return SUCCESS;
            }
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return null;
    }

    public Course getCourse() {
        return course;
    }

    public Boolean getWaiting() {
        return waiting;
    }

    private Boolean isValid(String input) {
        if (input != null && Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

}// Ende class
