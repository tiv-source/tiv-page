package de.tivsource.page.user.actions.course;

import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.common.simple.SimpleBooleanType;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.registration.Registration;
import de.tivsource.page.user.actions.EmptyAction;
import de.tivsource.page.user.services.CourseService;

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

    private CourseService courseService;

    @Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("salutationDaoLocal")
    private SalutationDaoLocal salutationDaoLocal;

    @Autowired
    @Qualifier("countryDaoLocal")
    private CountryDaoLocal countryDaoLocal;

    private Course course;

    private String courseUuid;

    private Registration registration;

    private Page page;

    private Boolean waiting = true;

    private List<Salutation> salutations;

    private List<Country> countryList;

    private List<SimpleBooleanType> sbtList;

    @Autowired
    public FormAction(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    @Override
    public void prepare() {
        page = pageDaoLocal.findByTechnical("waitingform");
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
        countryList = countryDaoLocal.findAll(0, countryDaoLocal.countAll());
        sbtList = new ArrayList<SimpleBooleanType>();
        sbtList.add(createSimpleBooleanType("Privat", true));
        sbtList.add(createSimpleBooleanType("Geschäftlich", false));
        registration = new Registration();
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
        if (isValid(courseUuid) && courseService.checkCourseUuid(courseUuid)) {
            LOGGER.info("gültige Course Uuid.");
            course = courseService.getCourseWithAllRelations(courseUuid);
            /*
             * Wenn das Course Objekt sichbar sein sollte und nicht in der
             * Vergangenheit liegt.
             */
            if(course.getVisible() && !course.isInPast()) {
                if(course.getRegistrations().size() <= course.getMaxStudents()) {
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

    public Registration getRegistration() {
        return registration;
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

    public List<SimpleBooleanType> getSbtList() {
        return sbtList;
    }

    private Boolean isValid(String input) {
        if (input != null && Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

    private SimpleBooleanType createSimpleBooleanType(String name, Boolean value) {
        SimpleBooleanType simpleBooleanType = new SimpleBooleanType();
        simpleBooleanType.setName(name);
        simpleBooleanType.setValue(value);
        return simpleBooleanType;
    }// Ende createSimpleBooleanType()

}// Ende class
