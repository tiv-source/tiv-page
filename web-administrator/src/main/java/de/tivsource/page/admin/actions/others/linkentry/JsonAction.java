/**
 * 
 */
package de.tivsource.page.admin.actions.others.linkentry;

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
import de.tivsource.page.common.menuentry.LinkEntry;
import de.tivsource.page.dao.linkentry.LinkEntryDaoLocal;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 1211255295822079053L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="LinkEntryDao")
    private LinkEntryDaoLocal linkEntryDaoLocal;

	private List<LinkEntry> gridModel;
	private List<LinkEntry> linkEntryList;
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
        		results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.contentItem"}) }
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
		setRecord(this.linkEntryDaoLocal.countAll());

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
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.technical", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.topNavigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.topNavigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.navigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.navigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.bottomNavigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.bottomNavigationOrder", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.responsiveNavigation", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.responsiveNavigationOrder", "asc");
            }else if (getSidx() != null && getSidx().equalsIgnoreCase("special")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.special", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.modified", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.visible", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.modifiedBy", "asc");
            } else {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.technical", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.topNavigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("topNavigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.topNavigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.navigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("navigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.navigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.bottomNavigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("bottomNavigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.bottomNavigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigation")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.responsiveNavigation", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("responsiveNavigationOrder")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.responsiveNavigationOrder", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("special")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.special", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.modified", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.visible", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows(), "le.modifiedBy", "desc");
            } else {
				linkEntryList = this.linkEntryDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(linkEntryList);

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
	public List<LinkEntry> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<LinkEntry> gridModel) {
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
