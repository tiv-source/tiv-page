/**
 * 
 */
package de.tivsource.page.admin.actions.vacancy;

import java.util.List;

import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(JsonAction.class);
	
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
        		        @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.events, gridModel.*.vacancies, gridModel.*.applications"})
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
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.technical", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.topNavigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.topNavigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.navigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.navigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.bottomNavigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.bottomNavigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.responsiveNavigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.responsiveNavigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("special")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.special", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.modified", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.visible", "asc");
			} else {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.technical", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.topNavigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.topNavigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.navigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.navigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.bottomNavigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.bottomNavigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigation")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.responsiveNavigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigationOrder")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.responsiveNavigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("special")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.special", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.modified", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				vacancyList = this.vacancyDaoLocal.findAll(from, getRows(), "p.visible", "desc");
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
