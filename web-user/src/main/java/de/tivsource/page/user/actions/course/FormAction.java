package de.tivsource.page.user.actions.course;

import java.util.List;
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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="form", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/course_form.jsp"),
    @TilesPutAttribute(name = "css",        value = "/WEB-INF/tiles/active/css/course.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/course.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/form.jsp")
  })
})
public class FormAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -4185479802786977211L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    private Course course;

    private String courseUuid;

    private Page page;

    private Boolean waiting = true;

    private List<Salutation> salutations;

    private List<Country> countryList;

    @Override
    public void prepare() {
        page = pageDaoLocal.findByTechnical("waitingform");
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
        countryList = countryDaoLocal.findAll(0, countryDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(value = "*/form", results = {
            @Result(name = "success", type = "tiles", location = "form"),
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
        courseUuid = courseUuid.replaceAll("/form.html", "");
        courseUuid = courseUuid.replaceAll("/course/", "");
        LOGGER.info("Course Uuid: " + courseUuid);

        /*
         * Wenn die Course Uuid keine nicht erlaubten Zeichen enthält und es
         * das Course Objekt mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(courseUuid) && courseDaoLocal.isCourseUuid(courseUuid)) {
            LOGGER.info("gültige Course Uuid.");
            course = courseDaoLocal.findByUuid(courseUuid);
            /*
             * Wenn das Course Objekt sichbar sein sollte und nicht in der
             * Vergangenheit liegt.
             */
            if(course.getVisible() && !course.isInPast()) {
                if(courseDaoLocal.registrations(course) < course.getMaxStudents()) {
                    page = pageDaoLocal.findByTechnical("registrationform");
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

    public List<Salutation> getSalutations() {
        return salutations;
    }

    public List<Country> getCountryList() {
        return countryList;
    }
    
    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

}// Ende class
