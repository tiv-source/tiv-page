package de.tivsource.page.admin.actions.academy.lecturer;

import java.util.Date;

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
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.entity.lecturer.Lecturer;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="lecturerDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/delete_error.jsp")
  }),
  @TilesDefinition(name="lecturerMenuEntryError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/menuentry_error.jsp")
  }),
  @TilesDefinition(name="lecturerSubSumptionError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/subsumption_error.jsp")
  }),
  @TilesDefinition(name="lecturerCourseError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/course_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7055770864456875292L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="LecturerDao")
    private LecturerDaoLocal lecturerDaoLocal;

    private Lecturer lecturer;

    @StrutsParameter(depth=3)
    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success",     type="redirectAction", location = "index.html"),
        				@Result(name = "input",       type="tiles", location = "lecturerDeleteError"),
        				@Result(name = "error",       type="tiles", location = "lecturerDeleteError"),
                        @Result(name = "menuentry",   type="tiles", location = "lecturerMenuEntryError"),
                        @Result(name = "subsumption", type="tiles", location = "lecturerSubSumptionError"),
                        @Result(name = "course",      type="tiles", location = "lecturerCourseError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(lecturer != null) {
    	    Lecturer dbLecturer = lecturerDaoLocal.findByUuid(lecturer.getUuid());
            if(!lecturerDaoLocal.hasMenuEntry(dbLecturer.getUuid())) {
                if(!lecturerDaoLocal.hasSubSumption(dbLecturer.getUuid())) {
                    if(!lecturerDaoLocal.hasCourse(dbLecturer.getUuid())) {
                        dbLecturer.setModified(new Date());
                        dbLecturer.setModifiedBy(remoteUser);
                        dbLecturer.setModifiedAddress(remoteAddress);
                        lecturerDaoLocal.merge(dbLecturer);
                        lecturerDaoLocal.delete(dbLecturer);
                        return SUCCESS;
                    }
                    // Wenn der Dozent ein Kurs enthält
                    // kann er nicht gelöscht werden
                    return "course";
                }
                // Wenn der Dozent in einer SubSumption ist
                // kann er nicht gelöscht werden
                return "subsumption";
            }
            // Wenn der Dozent zu einem MenuEntry gehört
            // kann er nicht gelöscht werden
            return "menuentry";
    	}
   		return ERROR;
    	
    }// Ende execute()
	
}// Ende class
