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
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.lecturer.Lecturer;
import de.tivsource.page.entity.location.Location;

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
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 3508446383120113438L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="LecturerDao")
    private LecturerDaoLocal lecturerDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Course course;

    private String lang;

    private List<CSSGroup> cssGroupList;

    private List<Lecturer> lecturerList;

    private List<Location> locationList;

    @StrutsParameter(depth=3)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        lecturerList = lecturerDaoLocal.findAll(0, lecturerDaoLocal.countAll());
        locationList = locationDaoLocal.findAll(0, locationDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "courseEditForm"),
        				@Result(name = "error",   type = "tiles", location = "courseEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(course != null) {
    		LOGGER.info("UUID des Events: " + course.getUuid());
    		Course dbCourse = courseDaoLocal.findByUuid(course.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
            	course.getContentMap().put(Language.DE, dbCourse.getContentObject(Language.DE));
            	dbCourse.getContentMap().get(Language.EN).setContent(course.getContent(Language.EN));
            	dbCourse.getContentMap().get(Language.EN).setModified(new Date());

                course.getDescriptionMap().put(Language.DE, dbCourse.getDescriptionObject(Language.DE));
                String noLineBreaks = course.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbCourse.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbCourse.getDescriptionMap().get(Language.EN).setKeywords(course.getKeywords(Language.EN));
                dbCourse.getDescriptionMap().get(Language.EN).setName(course.getName(Language.EN));
            } else {
            	dbCourse.getContentMap().get(Language.DE).setContent(course.getContent(Language.DE));
                dbCourse.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = course.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbCourse.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbCourse.getDescriptionMap().get(Language.DE).setKeywords(course.getKeywords(Language.DE));;
                dbCourse.getDescriptionMap().get(Language.DE).setName(course.getName(Language.DE));
            }

            
            
            dbCourse.setCourseDocumentDescription(course.getCourseDocumentDescription());
            dbCourse.setPrice(course.getPrice());
            dbCourse.setPriceTax(course.getPriceTax());
            dbCourse.setStartDate(course.getStartDate());
            dbCourse.setMaterial(course.getMaterial());
            dbCourse.setMaterialDocumentDescription(course.getMaterialDocumentDescription());
            dbCourse.setMaterialPrice(course.getMaterialPrice());
            dbCourse.setMaterialTax(course.getMaterialTax());
            dbCourse.setMaxStudents(course.getMaxStudents());
            dbCourse.setMinimumAge(course.getMinimumAge());
            dbCourse.setNumber(course.getNumber());
            

            dbCourse.setOrderNumber(course.getOrderNumber());
    		dbCourse.setModifiedAddress(remoteAddress);
    		dbCourse.setModified(new Date());
    		dbCourse.setModifiedBy(remoteUser);
    		dbCourse.setVisible(course.getVisible());
    		dbCourse.setPictureOnPage(course.getPictureOnPage());
    		dbCourse.setCssGroup(course.getCssGroup());
    		
    		courseDaoLocal.merge(dbCourse);
    		
            // Update das LecturerSet
            LOGGER.info("Course LecturerSet Anzahl: " + course.getLecturers().size());
            courseDaoLocal.updateLecturerSet(dbCourse, course.getLecturers());
            LOGGER.info("Course: " + dbCourse.getName("de") + " LecturerSet erfolgreich aktualisiert.");
    		
            return SUCCESS;
    	}
    	// Wenn kein Kurs-Objekt vorhanden ist
    	return ERROR;
    }// Ende execute()

    public List<Lecturer> getLecturerList() {
        return lecturerList;
    }// Ende getLecturerList()

    public List<Location> getLocationList() {
        return locationList;
    }// Ende getLocationList()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
