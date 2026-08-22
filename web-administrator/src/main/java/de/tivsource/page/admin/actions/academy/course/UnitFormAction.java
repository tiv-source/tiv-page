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
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="unitAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/unit_add_form.jsp")
  }),
  @TilesDefinition(name="unitEditForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/unit_edit_form.jsp")
  }),
  @TilesDefinition(name="unitDeleteForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/unit_delete_form.jsp")
  }),
  @TilesDefinition(name="unitImageForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/unit_image_form.jsp")
  })
})
public class UnitFormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -5834473935692474755L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(UnitFormAction.class);

    private CourseService courseService;

    private CSSGroupService cssGroupService;

	private Course course;

    private CourseUnit courseUnit;

	private String uncheckCourse;

    private String uncheckedCourseUnit;

	private String lang = "DE";

    @Autowired
    public UnitFormAction(
            PropertyService propertyService,
            CourseService courseService,
            CSSGroupService cssGroupService
            ) {
        super(propertyService);
        this.courseService = courseService;
        this.cssGroupService = cssGroupService;
    }

	public Course getCourse() {
        return course;
    }

    public CourseUnit getCourseUnit() {
        return courseUnit;
    }

	@StrutsParameter
	public void setUncheckCourse(String uncheckCourse) {
        this.uncheckCourse = uncheckCourse;
    }

    @StrutsParameter
    public void setUncheckedCourseUnit(String uncheckedCourseUnit) {
        this.uncheckedCourseUnit = uncheckedCourseUnit;
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
            value = "unitAddForm",
            results = { @Result(name = "success", type="tiles", location = "unitAddForm") }
        ),
        @Action(
            value = "unitEditForm",
            results = { @Result(name = "success", type="tiles", location = "unitEditForm") }
        ),
        @Action(
            value = "unitDeleteForm",
            results = { @Result(name = "success", type="tiles", location = "unitDeleteForm") }
        ),
        @Action(
            value = "unitImageForm",
            results = { @Result(name = "success", type="tiles", location = "unitImageForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.debug("getCssGroupList() aufgerufen.");
        List<CSSGroup> cssGroupList = cssGroupService.getAllCSSGroups();
        LOGGER.debug("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    private void loadPageParameter() {
        LOGGER.trace("loadPageParameter() aufgerufen.");
		if( uncheckCourse != null && uncheckCourse != "" && uncheckCourse.length() > 0) {
		    LOGGER.trace("Course Objekt geladen.");
			course = courseService.getCourseWithAllRelations(uncheckCourse);
			if(uncheckedCourseUnit != null) {
	            for(CourseUnit courseUnit : course.getUnits()) {
	                LOGGER.trace("Suche CourseUnit Objekt: " + uncheckedCourseUnit);
	                if(courseUnit.getUuid().compareTo(uncheckedCourseUnit) == 0) {
	                    LOGGER.trace("CourseUnit Objekt geladen.");
	                    this.courseUnit = courseUnit;
	                }
	            }
			}
		}

	}// Ende loadPageParameter()

}// Ende class
