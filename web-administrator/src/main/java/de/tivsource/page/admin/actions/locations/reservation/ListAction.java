package de.tivsource.page.admin.actions.locations.reservation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.odftoolkit.simple.SpreadsheetDocument;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.helper.odf.CreateReservationODF;

/**
 * 
 * @author Marc Michele
 *
 */
public class ListAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2516229338515041300L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ListAction.class);

    @InjectEJB(name="EventDao")
    private EventDaoLocal eventDaoLocal;

    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

    private Event event;
    
    private String uncheckedEvent;

	private InputStream fileStream;

    public void setEvent(String uncheckedEvent) {
        this.uncheckedEvent = uncheckedEvent;
    }

    @Override
    @Actions({
        @Action(
        		value = "list", 
        		results = { @Result(
        				           name = "success", 
        				           type="stream", 
        				           params={"contentType", "text/plain", "inputName", "fileStream", "contentDisposition", "attachment;filename=reservierungen.ods"}
        				           ) }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	// Laden Event
    	loadPageParameter();

    	String filePath = "/tmp/list_" + UUID.randomUUID().toString() + ".ods";

    	List<Reservation> reservations = reservationDaoLocal.findAll(event, 0, 2000);

    	if(reservations.size() > 0) {
            CreateReservationODF createReservationODF = new CreateReservationODF();
            SpreadsheetDocument outputDocument = createReservationODF.create(reservations);
            outputDocument.save(filePath);
            File backupFile = new File(filePath);
            fileStream = new FileInputStream(backupFile);
            backupFile.delete();
    	}


    	return SUCCESS;
    }
    
    public InputStream getFileStream() {
		return fileStream;
	}

    private void loadPageParameter() {
        LOGGER.info("loadPageParameter() aufgerufen.");
        if( uncheckedEvent != null && uncheckedEvent != "" && uncheckedEvent.length() > 0) {
            LOGGER.info("UncheckedEvent: " + uncheckedEvent);
            event = eventDaoLocal.findByUuid(uncheckedEvent);
        }
    }// Ende loadPageParameter()

}// Ende class
