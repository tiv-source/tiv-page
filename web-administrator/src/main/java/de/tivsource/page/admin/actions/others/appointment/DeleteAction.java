package de.tivsource.page.admin.actions.others.appointment;

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
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="appointmentDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/delete_error.jsp")
  }),
  @TilesDefinition(name="appointmentMenuEntryError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/menuentry_error.jsp")
  }),
  @TilesDefinition(name="appointmentSubSumptionError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/subsumption_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 4018486413901664427L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="AppointmentDao")
    private AppointmentDaoLocal appointmentDaoLocal;

    private Appointment appointment;

    @StrutsParameter(depth=3)
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

	@Override
    @Actions({
        @Action(
        		value = "delete", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "appointmentDeleteError"),
        				@Result(name = "error", type="tiles", location = "appointmentDeleteError"),
                        @Result(name = "menuentry", type="tiles", location = "appointmentMenuEntryError"),
                        @Result(name = "subsumption", type="tiles", location = "appointmentSubSumptionError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(appointment != null) {
    	    Appointment dbAppointment = appointmentDaoLocal.findByUuid(appointment.getUuid());
            if(!appointmentDaoLocal.hasMenuEntry(dbAppointment.getUuid())) {
                if(!appointmentDaoLocal.hasSubSumption(dbAppointment.getUuid())) {
                    dbAppointment.setModified(new Date());
                    dbAppointment.setModifiedBy(remoteUser);
                    dbAppointment.setModifiedAddress(remoteAddress);
                    appointmentDaoLocal.merge(dbAppointment);
                    appointmentDaoLocal.delete(dbAppointment);
                    return SUCCESS;
                }
                return "subsumption";
            }
            return "menuentry";
    	}
    	return ERROR;
    }// Ende execute()
	
}// Ende class
