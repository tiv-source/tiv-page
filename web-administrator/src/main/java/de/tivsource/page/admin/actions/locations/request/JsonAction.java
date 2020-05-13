/**
 * 
 */
package de.tivsource.page.admin.actions.locations.request;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.request.RequestDaoLocal;
import de.tivsource.page.entity.request.Request;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6847957890182885407L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="RequestDao")
    private RequestDaoLocal requestDaoLocal;

	private List<Request> gridModel;
	private List<Request> requestList;
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
        		results = { 
        		        @Result(
        		                name = "success", 
        		                type="json", 
        		                params={
        		                        "contentType", "application/json", 
        		                        "excludeProperties",
        		                        "gridModel.*.uploadFile, "
        		                        + "gridModel.*.location, "
        		                        + "gridModel.*.proofOfAuthorityFileInputStream, "
        		                        + "gridModel.*.reason.requests, "
        		                        + "gridModel.*.namingItem"
        		                }
        		        ) 
        		}
        )
    })
	public String execute() {
		return SUCCESS;
	}

	public String getJSON() {

	    LOGGER.info("Page: " + getPage() + " Rows: " + getRows()
				+ " Sorting Order: " + getSord() + " Index Row: " + getSidx());
	    LOGGER.info("Build new List");

		/*
		 * Setze die Anzahl aller Objekte in der Datenbank.
		 */
		setRecord(this.requestDaoLocal.countAll());

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
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("gender")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.gender", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("firstname")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.firstname", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("lastname")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.lastname", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("reason")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.reason", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmed")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmed", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmedAddress")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmedAddress", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmedDate")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmedDate", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmedBy")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmedBy", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.created", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("createdAddress")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.createdAddress", "asc");
            } else {
				requestList = this.requestDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("gender")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.gender", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("firstname")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.firstname", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("lastname")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.lastname", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("reason")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.reason", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmed")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmed", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmedAddress")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmedAddress", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmedDate")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmedDate", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("confirmedBy")) {
				requestList = this.requestDaoLocal.findAll(from, getRows(), "r.confirmedBy", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.created", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("createdAddress")) {
                requestList = this.requestDaoLocal.findAll(from, getRows(), "r.createdAddress", "desc");
            } else {
				requestList = this.requestDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(requestList);

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
	public List<Request> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Request> gridModel) {
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
