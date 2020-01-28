/**
 * 
 */
package de.tivsource.page.admin.actions.others.companion;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.entity.companion.Companion;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -2954188570324511282L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="CompanionDao")
    private CompanionDaoLocal companionDaoLocal;

	private List<Companion> gridModel;
	private List<Companion> companionList;
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
		setRecord(this.companionDaoLocal.countAll());

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
				companionList = this.companionDaoLocal.findAll(from, getRows(), "c.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("appendix")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.appendix", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("telephone")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.contactDetails.telephone", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("fax")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.contactDetails.fax", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				companionList = this.companionDaoLocal.findAll(from, getRows(), "c.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				companionList = this.companionDaoLocal.findAll(from, getRows(), "c.visible", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.modifiedBy", "asc");
            } else {
				companionList = this.companionDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				companionList = this.companionDaoLocal.findAll(from, getRows(), "c.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("appendix")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.appendix", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("telephone")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.contactDetails.telephone", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("fax")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.contactDetails.fax", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				companionList = this.companionDaoLocal.findAll(from, getRows(), "c.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				companionList = this.companionDaoLocal.findAll(from, getRows(), "c.visible", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                companionList = this.companionDaoLocal.findAll(from, getRows(), "c.modifiedBy", "desc");
            } else {
				companionList = this.companionDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(companionList);

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
	public List<Companion> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Companion> gridModel) {
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
