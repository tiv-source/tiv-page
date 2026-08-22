/**
 * 
 */
package de.tivsource.page.admin.actions.newsletter.mailaddresslock;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.dao.mail.MailAddressLockDaoLocal;
import de.tivsource.page.entity.mail.MailAddressLock;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8459298637694071542L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@Autowired
    @Qualifier("mailAddressLockDaoLocal")
    private MailAddressLockDaoLocal mailAddressLockDaoLocal;

	private List<MailAddressLock> gridModel;
	private List<MailAddressLock> mailAddressLockList;
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
        		results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.companions, gridModel.*.gallery, gridModel.*.pictureItems, gridModel.*.groups"}) }
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
		setRecord(this.mailAddressLockDaoLocal.countAll());

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
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("hash")) {
                mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.hash", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("used")) {
                mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.used", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("createdBy")) {
                mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.createdBy", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("createdAddress")) {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.createdAddress", "asc");
			} else {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("hash")) {
                mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.hash", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("used")) {
                mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.used", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("createdBy")) {
                mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.createdBy", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("createdAddress")) {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows(), "ma.createdAddress", "desc");
			} else {
				mailAddressLockList = this.mailAddressLockDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(mailAddressLockList);

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
	@StrutsParameter
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
	@StrutsParameter
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
	public List<MailAddressLock> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<MailAddressLock> gridModel) {
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
	@StrutsParameter
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
	@StrutsParameter
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

}// Ende class
