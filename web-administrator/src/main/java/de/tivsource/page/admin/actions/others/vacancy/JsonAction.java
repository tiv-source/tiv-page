/**
 * 
 */
package de.tivsource.page.admin.actions.others.vacancy;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -1054161515297319240L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="VacancyDao")
    private VacancyDaoLocal vacancyDaoLocal;

	private List<Vacancy> gridModel;
	private List<Vacancy> vacancyList;
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
        		        @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.events, gridModel.*.vacancies, gridModel.*.applications, gridModel.*.picture, gridModel.*.pictureItems, gridModel.*.groups"})
        		}
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
		setRecord(this.vacancyDaoLocal.countAll());

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
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.modifiedBy", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.technical", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("beginning")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.beginning", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("workingTime")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.workingTime", "asc");
            } else {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.created", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.modifiedBy", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.technical", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("beginning")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.beginning", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("workingTime")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "v.workingTime", "desc");
            } else {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(vacancyList);

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
	public List<Vacancy> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Vacancy> gridModel) {
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
