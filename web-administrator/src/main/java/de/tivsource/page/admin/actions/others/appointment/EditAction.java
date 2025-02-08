package de.tivsource.page.admin.actions.others.appointment;

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
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="appointmentEditForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/edit_form.jsp")
  }),
  @TilesDefinition(name="appointmentEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7633432653598001392L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="AppointmentDao")
    private AppointmentDaoLocal appointmentDaoLocal;

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Appointment appointment;

    private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Appointment getAppointment() {
        return appointment;
    }

	public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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
        				@Result(name = "input",   type = "tiles", location = "appointmentEditForm"),
        				@Result(name = "error",   type = "tiles", location = "appointmentEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(appointment != null) {
    		LOGGER.info("Appointment UUID: " + appointment.getUuid());
    		Appointment dbAppointment = appointmentDaoLocal.findByUuid(appointment.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                appointment.getContentMap().put(Language.DE, dbAppointment.getContentObject(Language.DE));
                dbAppointment.getContentMap().get(Language.EN).setContent(appointment.getContent(Language.EN));
                dbAppointment.getContentMap().get(Language.EN).setModified(new Date());

                appointment.getDescriptionMap().put(Language.DE, dbAppointment.getDescriptionObject(Language.DE));
                String noLineBreaks = appointment.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbAppointment.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbAppointment.getDescriptionMap().get(Language.EN).setKeywords(appointment.getKeywords(Language.EN));
                dbAppointment.getDescriptionMap().get(Language.EN).setName(appointment.getName(Language.EN));
            } else {
                dbAppointment.getContentMap().get(Language.DE).setContent(appointment.getContent(Language.DE));
                dbAppointment.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = appointment.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbAppointment.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbAppointment.getDescriptionMap().get(Language.DE).setKeywords(appointment.getKeywords(Language.DE));;
                dbAppointment.getDescriptionMap().get(Language.DE).setName(appointment.getName(Language.DE));
            }


            dbAppointment.setPointInTime(appointment.getPointInTime());
            dbAppointment.setBooking(appointment.getBooking());
            dbAppointment.setBookingUrl(appointment.getBookingUrl());
            dbAppointment.setHasVenue(appointment.getHasVenue());
            dbAppointment.setVenue(appointment.getVenue());
            dbAppointment.setVisibleFrom(appointment.getVisibleFrom());
    		dbAppointment.setModified(new Date());
    		dbAppointment.setVisible(appointment.getVisible());
    		dbAppointment.setModifiedBy(remoteUser);
    		dbAppointment.setModifiedAddress(remoteAddress);
    		dbAppointment.setPictureOnPage(appointment.getPictureOnPage());
    		dbAppointment.setCssGroup(appointment.getCssGroup());


    		appointmentDaoLocal.merge(dbAppointment);
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
