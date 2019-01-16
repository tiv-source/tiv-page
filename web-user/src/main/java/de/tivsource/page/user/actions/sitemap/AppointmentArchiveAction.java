package de.tivsource.page.user.actions.sitemap;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class AppointmentArchiveAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1210938078282517352L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AppointmentArchiveAction.class);

    @InjectEJB(name="AppointmentDao")
    private AppointmentDaoLocal appointmentDaoLocal;

    @Override
    @Actions({
        @Action(value = "appointmentArchive", results = {
            @Result(name = "success", type = "tiles", location = "sitemapAppointmentArchive")
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        return SUCCESS;
    }// Ende execute()

    public List<Appointment> getAppointments() {
        return appointmentDaoLocal.findAllArchiveVisible(0, appointmentDaoLocal.countAllArchiveVisible());
    }

}// Ende class
