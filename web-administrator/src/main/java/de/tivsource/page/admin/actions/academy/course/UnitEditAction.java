package de.tivsource.page.admin.actions.academy.course;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="courseEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/course/edit_form.jsp")
  })
})
public class UnitEditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -691857255889419945L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(UnitEditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    private Course course;

    private CourseUnit courseUnit;

    private String lang;

    private List<CSSGroup> cssGroupList;

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
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
        		value = "unitEdit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "courseOverview.html", params = {"uncheckCourse", "%{course.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "courseEditForm"),
        				@Result(name = "error",   type = "tiles", location = "courseEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(course != null && courseUnit != null) {
    		LOGGER.info("UUID des Course: " + course.getUuid());
    		Course dbCourse = courseDaoLocal.findByUuid(course.getUuid());

    		// Durchlaufe alle Einheiten des Kurses
    		for(CourseUnit dbCourseUnit : dbCourse.getUnits()) {
    		    // Wenn die Einheit der gesuchten entspricht
    		    if(dbCourseUnit.getUuid().compareTo(this.courseUnit.getUuid()) == 0) {
    	            if(lang.contentEquals(new StringBuffer("EN"))) {
    	                courseUnit.getContentMap().put(Language.DE, dbCourseUnit.getContentObject(Language.DE));
    	                dbCourseUnit.getContentMap().get(Language.EN).setContent(courseUnit.getContent(Language.EN));
    	                dbCourseUnit.getContentMap().get(Language.EN).setModified(new Date());

    	                courseUnit.getDescriptionMap().put(Language.DE, dbCourseUnit.getDescriptionObject(Language.DE));
    	                String noLineBreaks = courseUnit.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
    	                dbCourseUnit.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
    	                dbCourseUnit.getDescriptionMap().get(Language.EN).setKeywords(courseUnit.getKeywords(Language.EN));
    	                dbCourseUnit.getDescriptionMap().get(Language.EN).setName(courseUnit.getName(Language.EN));
    	            } else {
    	                dbCourseUnit.getContentMap().get(Language.DE).setContent(courseUnit.getContent(Language.DE));
    	                dbCourseUnit.getContentMap().get(Language.DE).setModified(new Date());

    	                String noLineBreaks = courseUnit.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	                dbCourseUnit.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	                dbCourseUnit.getDescriptionMap().get(Language.DE).setKeywords(courseUnit.getKeywords(Language.DE));;
    	                dbCourseUnit.getDescriptionMap().get(Language.DE).setName(courseUnit.getName(Language.DE));
    	            }

    	            dbCourseUnit.setDuration(courseUnit.getDuration());
    	            dbCourseUnit.setPointInTime(courseUnit.getPointInTime());
    	            dbCourseUnit.setModifiedAddress(remoteAddress);
    	            dbCourseUnit.setModified(new Date());
    	            dbCourseUnit.setModifiedBy(remoteUser);
    	            dbCourseUnit.setCssGroup(courseUnit.getCssGroup());
    	            // TODO: Datem des Kurs-Beginn überprüfen und wenn das Datum dieser Unit davor liegt auf dieses setzen
    	            
    	            courseDaoLocal.merge(dbCourse);
    	            return SUCCESS;
    		    }// Ende if()
    		}// Ende for()
    		// Wenn die Schleife durchlaufen wurde und es die Kurseinheit nicht gab.
    		return ERROR;
    	}// Ende if()
    	// Wenn kein Kurs-Objekt vorhanden ist
    	return ERROR;
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
