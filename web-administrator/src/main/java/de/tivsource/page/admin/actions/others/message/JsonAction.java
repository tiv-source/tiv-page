/**
 * 
 */
package de.tivsource.page.admin.actions.others.message;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.entity.message.Message;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -8473419739773335585L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="MessageDao")
    private MessageDaoLocal messageDaoLocal;

	private List<Message> gridModel;
	private List<Message> pageList;
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

	    LOGGER.info("Page " + getPage() + " Rows " + getRows()
				+ " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    LOGGER.info("Build new List");

		/*
		 * Setze die Anzahl aller Objekte in der Datenbank.
		 */
		setRecord(this.messageDaoLocal.countAll());

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
			if (getSidx() != null && getSidx().equalsIgnoreCase("gender")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.gender", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("firstname")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.firstname", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("lastname")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.lastname", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("telephone")) {
                pageList = this.messageDaoLocal.findAll(from, getRows(), "m.telephone", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("fax")) {
                pageList = this.messageDaoLocal.findAll(from, getRows(), "m.fax", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("email")) {
                pageList = this.messageDaoLocal.findAll(from, getRows(), "m.mail", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("ip")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.ip", "asc");
			} else {
				pageList = this.messageDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("gender")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.gender", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("firstname")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.firstname", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("lastname")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.lastname", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("telephone")) {
                pageList = this.messageDaoLocal.findAll(from, getRows(), "m.telephone", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("fax")) {
                pageList = this.messageDaoLocal.findAll(from, getRows(), "m.fax", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("email")) {
                pageList = this.messageDaoLocal.findAll(from, getRows(), "m.mail", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("ip")) {
				pageList = this.messageDaoLocal.findAll(from, getRows(), "m.ip", "desc");
			} else {
				pageList = this.messageDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(pageList);

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
	public List<Message> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Message> gridModel) {
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
