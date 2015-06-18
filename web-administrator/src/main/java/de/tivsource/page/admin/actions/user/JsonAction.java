/**
 * 
 */
package de.tivsource.page.admin.actions.user;

import java.util.List;
import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.entity.administration.User;
/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5654549639176582755L;

	private static final Logger logger = Logger.getLogger("INFO");
	
	@InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

	private List<User> gridModel;
	private List<User> pageList;
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
        		results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.roles"}) }
        )
    })
	public String execute() {
		return SUCCESS;
	}

	public String getJSON() {

		logger.info("Page " + getPage() + " Rows " + getRows()
				+ " Sorting Order " + getSord() + " Index Row :" + getSidx());
		logger.info("Build new List");

		/*
		 * Setze die Anzahl aller Objekte in der Datenbank.
		 */
		setRecord(this.userDaoLocal.countAll());

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
			logger.info("Sortieren nach asc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("username")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.username", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.modified", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.visible", "asc");
			} else {
				pageList = this.userDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
			logger.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("username")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.username", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.modified", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				pageList = this.userDaoLocal.findAll(from, getRows(), "u.visible", "desc");
			} else {
				pageList = this.userDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(pageList);

		logger.info("Rows:" + rows);
		logger.info("Page:" + page);
		logger.info("Total:" + total);
		logger.info("Record:" + record);
		logger.info("Sord:" + sord);
		logger.info("Sidx:" + sidx);
		
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
	public List<User> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<User> gridModel) {
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