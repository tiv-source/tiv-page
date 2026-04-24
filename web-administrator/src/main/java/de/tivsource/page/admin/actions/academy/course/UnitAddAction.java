package de.tivsource.page.admin.actions.academy.course;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
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

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    private Course course;

    private CourseUnit courseUnit;

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

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
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

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(course != null) {
    	    LOGGER.info("UUID des Events: " + course.getUuid());
            Course dbCourse = courseDaoLocal.findByUuid(course.getUuid());
    	    if(courseUnit != null) {
    	        courseUnit.setUuid(UUID.randomUUID().toString());
    	        courseUnit.setModified(new Date());
    	        courseUnit.setCreated(new Date());
    	        courseUnit.setModifiedBy(remoteUser);
    	        courseUnit.setModifiedAddress(remoteAddress);

    	        courseUnit.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	        courseUnit.getDescriptionMap().get(Language.DE).setNamingItem(courseUnit);
    	        courseUnit.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
                String noLineBreaks = courseUnit.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                courseUnit.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

                courseUnit.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
                courseUnit.getContentMap().get(Language.DE).setContentItem(courseUnit);
                courseUnit.getContentMap().get(Language.DE).setLanguage(Language.DE);
                courseUnit.getContentMap().get(Language.DE).setCreated(new Date());
                courseUnit.getContentMap().get(Language.DE).setModified(new Date());

                courseUnit.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
                courseUnit.getDescriptionMap().get(Language.EN).setNamingItem(courseUnit);
                courseUnit.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
                courseUnit.getDescriptionMap().get(Language.EN).setDescription(courseUnit.getDescriptionMap().get(Language.DE).getDescription());
                courseUnit.getDescriptionMap().get(Language.EN).setName(courseUnit.getDescriptionMap().get(Language.DE).getName());
                courseUnit.getDescriptionMap().get(Language.EN).setKeywords(courseUnit.getDescriptionMap().get(Language.DE).getKeywords());

                courseUnit.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
                courseUnit.getContentMap().get(Language.EN).setContentItem(courseUnit);
                courseUnit.getContentMap().get(Language.EN).setLanguage(Language.EN);
                courseUnit.getContentMap().get(Language.EN).setCreated(new Date());
                courseUnit.getContentMap().get(Language.EN).setModified(new Date());
                courseUnit.getContentMap().get(Language.EN).setContent(courseUnit.getContentMap().get(Language.DE).getContent());

                courseUnit.getImage().setUuid(UUID.randomUUID().toString());
                courseUnit.getImage().generate();
                courseUnit.getImage().setCreated(new Date());
                courseUnit.getImage().setModified(new Date());
                courseUnit.getImage().setModifiedAddress(remoteAddress);
                courseUnit.getImage().setModifiedBy(remoteUser);

                courseUnit.setOrderNumber(1);
                courseUnit.setPictureOnPage(true);
                courseUnit.setVisible(true);
                courseUnit.setTechnical("CourseUnit_" + courseUnit.getUuid());
                courseUnit.setCourse(dbCourse);
                // TODO: Datem des Kurs-Beginn überprüfen und wenn das Datum dieser Unit davor liegt auf dieses setzen

                dbCourse.getUnits().add(courseUnit);
                courseDaoLocal.merge(dbCourse);

                return SUCCESS;
    	        
    	    }
    	    return ERROR;
    	}
   		return ERROR;

    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
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
