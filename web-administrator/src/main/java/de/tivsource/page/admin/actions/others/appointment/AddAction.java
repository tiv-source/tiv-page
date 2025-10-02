package de.tivsource.page.admin.actions.others.appointment;

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
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="appointmentAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/add_form.jsp")
  }),
  @TilesDefinition(name="appointmentAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -6268903742931993162L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="AppointmentDao")
    private AppointmentDaoLocal appointmentDaoLocal;

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Appointment appointment;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "appointmentAddForm"),
        				@Result(name = "error", type="tiles", location = "appointmentAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(appointment != null) {
    	    appointment.setUuid(UUID.randomUUID().toString());
    	    appointment.setModified(new Date());
    	    appointment.setCreated(new Date());
    	    appointment.setModifiedBy(remoteUser);
    	    appointment.setModifiedAddress(remoteAddress);

    	    appointment.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    appointment.getDescriptionMap().get(Language.DE).setNamingItem(appointment);
    	    appointment.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
            String noLineBreaks = appointment.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
            appointment.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
            
            appointment.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
            appointment.getContentMap().get(Language.DE).setContentItem(appointment);
            appointment.getContentMap().get(Language.DE).setLanguage(Language.DE);
            appointment.getContentMap().get(Language.DE).setCreated(new Date());
            appointment.getContentMap().get(Language.DE).setModified(new Date());

            appointment.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            appointment.getDescriptionMap().get(Language.EN).setNamingItem(appointment);
            appointment.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            appointment.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            appointment.getContentMap().get(Language.EN).setContentItem(appointment);
            appointment.getContentMap().get(Language.EN).setLanguage(Language.EN);
            appointment.getContentMap().get(Language.EN).setCreated(new Date());
            appointment.getContentMap().get(Language.EN).setModified(new Date());

            appointment.setTechnical("APPOINTMENT_" + appointment.getUuid());

            appointment.getImage().setUuid(UUID.randomUUID().toString());
            appointment.getImage().generate();
            appointment.getImage().setCreated(new Date());
            appointment.getImage().setModified(new Date());
            appointment.getImage().setModifiedAddress(remoteAddress);
            appointment.getImage().setModifiedBy(remoteUser);

    		appointmentDaoLocal.merge(appointment);

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

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.appointment = new Appointment();
            this.appointment.setImage(new PictureItemImage());
            this.appointment.getImage().setPictureItem(this.appointment);
            this.appointment.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
