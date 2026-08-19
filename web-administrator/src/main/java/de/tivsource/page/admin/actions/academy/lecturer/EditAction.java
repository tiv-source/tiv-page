package de.tivsource.page.admin.actions.academy.lecturer;

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
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.lecturer.Lecturer;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="lecturerEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/edit_form.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -8803882047925640411L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="LecturerDao")
    private LecturerDaoLocal lecturerDaoLocal;

    private Lecturer lecturer;

    private String lang;

    private List<CSSGroup> cssGroupList;

    @Autowired
    public EditAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=3)
    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
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
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "lecturerEditForm"),
        				@Result(name = "error",   type = "tiles", location = "lecturerEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(lecturer != null) {
    		LOGGER.info("UUID des Events: " + lecturer.getUuid());
    		Lecturer dbLecturer = lecturerDaoLocal.findByUuid(lecturer.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
            	lecturer.getContentMap().put(Language.DE, dbLecturer.getContentObject(Language.DE));
            	dbLecturer.getContentMap().get(Language.EN).setContent(lecturer.getContent(Language.EN));
            	dbLecturer.getContentMap().get(Language.EN).setModified(new Date());

                lecturer.getDescriptionMap().put(Language.DE, dbLecturer.getDescriptionObject(Language.DE));
                String noLineBreaks = lecturer.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbLecturer.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbLecturer.getDescriptionMap().get(Language.EN).setKeywords(lecturer.getKeywords(Language.EN));
                dbLecturer.getDescriptionMap().get(Language.EN).setName(lecturer.getName(Language.EN));
            } else {
            	dbLecturer.getContentMap().get(Language.DE).setContent(lecturer.getContent(Language.DE));
                dbLecturer.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = lecturer.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbLecturer.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbLecturer.getDescriptionMap().get(Language.DE).setKeywords(lecturer.getKeywords(Language.DE));;
                dbLecturer.getDescriptionMap().get(Language.DE).setName(lecturer.getName(Language.DE));
            }

            dbLecturer.setOrderNumber(lecturer.getOrderNumber());

    		dbLecturer.setModifiedAddress(remoteAddress);
    		dbLecturer.setModified(new Date());
    		dbLecturer.setModifiedBy(remoteUser);
    		dbLecturer.setVisible(lecturer.getVisible());
    		dbLecturer.setPictureOnPage(lecturer.getPictureOnPage());
    		dbLecturer.setCssGroup(lecturer.getCssGroup());
    		dbLecturer.setTechnical(lecturer.getTechnical());
    		
    		lecturerDaoLocal.merge(dbLecturer);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
