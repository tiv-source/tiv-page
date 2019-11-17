/**
 * 
 */
package de.tivsource.page.admin.actions.others.appointment;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8354239037998933809L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="AppointmentDao")
    private AppointmentDaoLocal appointmentDaoLocal;

	private List<Appointment> gridModel;
	private List<Appointment> appointmentList;
	private Integer rows = 0;
	private Integer page = 1;
	private Integer total = 0;
	private Integer record = 0;
	private String sord;
	private String sidx;

	@Override
    @Actions({
        @Action(
        		value = "table", 
        		results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.companions, gridModel.*.gallery, gridModel.*.pictureItems"}) }
        )
    })
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
		setRecord(this.appointmentDaoLocal.countAll());

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
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("pictureOnPage")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.pictureOnPage", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("pointInTime")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.pointInTime", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.visible", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.modifiedBy", "asc");
            } else {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("pictureOnPage")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.pictureOnPage", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("pointInTime")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.pointInTime", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.visible", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                appointmentList = this.appointmentDaoLocal.findAll(from, getRows(), "ap.modifiedBy", "desc");
            } else {
				appointmentList = this.appointmentDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(appointmentList);

		LOGGER.info("Rows:"   + rows);
		LOGGER.info("Page:"   + page);
		LOGGER.info("Total:"  + total);
		LOGGER.info("Record:" + record);
		LOGGER.info("Sord:"   + sord);
		LOGGER.info("Sidx:"   + sidx);
		
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
	public List<Appointment> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Appointment> gridModel) {
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
