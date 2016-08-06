/**
 * 
 */
package de.tivsource.page.admin.actions.others.news;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.entity.news.News;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 2722055924214990160L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="NewsDao")
    private NewsDaoLocal newsDaoLocal;

	private List<News> gridModel;
	private List<News> newsList;
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
        		results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.gallery"}) }
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
		setRecord(this.newsDaoLocal.countAll());

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
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("releaseDate")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.releaseDate", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("special")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.special", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modified", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.visible", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modifiedBy", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedAddress")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modifiedAddress", "asc");
            } else {
				newsList = this.newsDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("releaseDate")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.releaseDate", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("special")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.special", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modified", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.created", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
				newsList = this.newsDaoLocal.findAll(from, getRows(), "n.visible", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modifiedBy", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedAddress")) {
                newsList = this.newsDaoLocal.findAll(from, getRows(), "n.modifiedAddress", "desc");
            } else {
				newsList = this.newsDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(newsList);

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
	public List<News> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<News> gridModel) {
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
