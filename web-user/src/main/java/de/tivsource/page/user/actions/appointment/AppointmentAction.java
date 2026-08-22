package de.tivsource.page.user.actions.appointment;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="appointment", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/appointment.jsp"),
    @TilesPutAttribute(name = "css",        value = "/WEB-INF/tiles/active/css/appointment.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/appointment.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/appointment/appointment.jsp")
  })
})
public class AppointmentAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 6773842640719364262L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AppointmentAction.class);

    @Autowired
    @Qualifier("appointmentDaoLocal")
    private AppointmentDaoLocal appointmentDaoLocal;

    private Appointment appointment;

    private String appointmentUuid;

    @Override
    @Actions({
        @Action(value = "*/index", results = {
            @Result(name = "success", type = "tiles", location = "appointment"),
            @Result(name = "input", type = "redirectAction", location = "index.html", params={"namespace", "/"}),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        appointmentUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Appointment Uuid: " + appointmentUuid);

        // /gallery/painting/index.html?page=1&request_locale=de
        
        
        appointmentUuid = appointmentUuid.replaceAll("/index.html", "");
        appointmentUuid = appointmentUuid.replaceAll("/appointment/", "");
            
        LOGGER.info("Appointment Uuid: " + appointmentUuid);

        /*
         * Wenn die Appointment Uuid keine nicht erlaubten Zeichen enthält und es
         * das Appointment Objekt mit der Uuid gibt dann wird der Block ausgeführt.
         */
        if (isValid(appointmentUuid) && appointmentDaoLocal.isAppointmentUuid(appointmentUuid)) {
            LOGGER.info("gültige Appointment Uuid.");
            appointment = appointmentDaoLocal.findByUuid(appointmentUuid);
            
            /*
             * Wenn das Appointment Objekt sichbar sein soll und auch schon das
             * Anzeigedtaum erreicht hat wird dieser Block ausgefüht.
             */
            if(appointment.getVisible() && new Date().after(appointment.getVisibleFrom())) {
                // Setze Daten in ein Page Objekt

                return SUCCESS;
            }
        }

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
         return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return null;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

}// Ende class
