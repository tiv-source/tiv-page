package de.tivsource.page.user.actions.registration;

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
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.services.CourseService;

@TilesDefinitions({
  @TilesDefinition(name="registrationSuccess", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/course.jsp"),
    @TilesPutAttribute(name = "css",        value = "/WEB-INF/tiles/active/css/course.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/course.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/registration_success.jsp")
  }),
  @TilesDefinition(name="waitingSuccess", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/course.jsp"),
    @TilesPutAttribute(name = "css",        value = "/WEB-INF/tiles/active/css/course.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/course.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/waiting_success.jsp")
  })
})
public class SuccessAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -4185479802786977211L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SuccessAction.class);

    private CourseService courseService;

    @Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

    private Course course;

    private String courseUuid;

    private Page page;

    private Boolean waiting = true;

    @Autowired
    public SuccessAction(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    @Override
    public void prepare() {
        page = pageDaoLocal.findByTechnical("waitingsuccess");
    }

    @Override
    @Actions({
        @Action(value = "*/registrationsuccess", results = {
            @Result(name = "success", type = "tiles", location = "registrationSuccess"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        }),
        @Action(value = "*/waitingsuccess", results = {
            @Result(name = "success", type = "tiles", location = "waitingSuccess"),
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
        courseUuid = courseUuid.replaceAll("/registrationsuccess.html", "");
        courseUuid = courseUuid.replaceAll("/waitingsuccess.html", "");
        courseUuid = courseUuid.replaceAll("/registration/", "");
        LOGGER.info("Course Uuid: " + courseUuid);

        /*
         * Wenn die Course Uuid keine nicht erlaubten Zeichen enthält und es
         * das Course Objekt mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(courseUuid) && courseService.checkCourseUuid(courseUuid)) {
            LOGGER.info("gültige Course Uuid.");
            course = courseService.getCourseWithAllRelations(courseUuid);
            /*
             * Wenn das Course Objekt sichbar sein sollte und nicht in der
             * Vergangenheit liegt.
             */
            if(course.getVisible() && !course.isInPast()) {
                if(course.getRegistrations().size() <= course.getMaxStudents()) {
                    page = pageDaoLocal.findByTechnical("registrationsuccess");
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

    public Course getCourse() {
        return course;
    }

    @Override
    public Page getPage() {
        return page;
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
