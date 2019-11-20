/**
 * 
 */
package de.tivsource.page.admin.actions.locations.reservation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class QueueJsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -7118393237057175007L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(QueueJsonAction.class);
    
    @InjectEJB(name="ReservationDao")
    private ReservationDaoLocal reservationDaoLocal;

	private List<Reservation> gridModel;
	private List<Reservation> eventList;
	private Integer rows = 0;
	private Integer page = 1;
	private Integer total = 0;
	private Integer record = 0;
	private String sord;
	private String sidx;

	@Override
    @Action(
            value = "queueTable",
            results = {
                @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.reservations, gridModel.*.events, gridModel.*.vacancies, gridModel.*.picture, gridModel.*.pictureItems"})
            }
        )
	public String execute() {
		return SUCCESS;
	}

	public String getJSON() {

	    LOGGER.info("Page " + getPage() + " Rows " + getRows()
				+ " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    LOGGER.info("Build new List");

		/*
		 * Setze die Anzahl aller Objekte in der Datenbank.
		 */
		setRecord(this.reservationDaoLocal.countConfirmationQueue());

		int to = (getRows() * getPage());
		int from = to - getRows();

		/*
		 * Setze die Maximalgrenze auf die Maximale Anzahl
		 */
		if (to > getRecord()) {
			to = getRecord();
		}
		
		/*
		 * Sortieren aufsteigen
		 */
		if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
		    LOGGER.info("Sortieren nach asc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
				eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "dm.name", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("location")) {
				eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "edm.name", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("price")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.price", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("beginning")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.beginning", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("ending")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.ending", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("deadline")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.deadline", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("reservation")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.reservation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.modifiedBy", "asc");
            } else {
				eventList = this.reservationDaoLocal.confirmationQueue(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
            if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("location")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "edm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("price")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.price", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("beginning")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.beginning", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("ending")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.ending", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("deadline")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.deadline", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("reservation")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.reservation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows(), "e.modifiedBy", "desc");
            } else {
                eventList = this.reservationDaoLocal.confirmationQueue(from, getRows());
            }
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(eventList);

		LOGGER.info("Rows:" + rows);
		LOGGER.info("Page:" + page);
		LOGGER.info("Total:" + total);
		LOGGER.info("Record:" + record);
		LOGGER.info("Sord:" + sord);
		LOGGER.info("Sidx:" + sidx);
		
		return execute();
	}

	/**
	 * @return how many rows we want to have into the grid
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            how many rows we want to have into the grid
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return current page of the query
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            current page of the query
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return total pages for the query
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            total pages for the query
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return total number of records for the query. e.g. select count(*) from
	 *         table
	 */
	public Integer getRecord() {
		return record;
	}

	/**
	 * @param record
	 *            total number of records for the query. e.g. select count(*)
	 *            from table
	 */
	public void setRecord(Integer record) {

		this.record = record;

		if (this.record > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.record
					/ (double) this.rows);
		} else {
			this.total = 0;
		}
	}

	/**
	 * @return an collection that contains the actual data
	 */
	public List<Reservation> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Reservation> gridModel) {
		this.gridModel = gridModel;
	}

	/**
	 * @return sorting order
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord
	 *            sorting order
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @return get index row - i.e. user click to sort.
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx
	 *            get index row - i.e. user click to sort.
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

}// Ende class
