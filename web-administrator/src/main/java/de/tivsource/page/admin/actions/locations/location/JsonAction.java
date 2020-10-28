/**
 * 
 */
package de.tivsource.page.admin.actions.locations.location;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.location.Location;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1771692240110579820L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

	private List<Location> gridModel;
	private List<Location> pageList;
	private Integer rows = 0;
	private Integer page = 1;
	private Integer total = 0;
	private Integer record = 0;
	private String sord;
	private String sidx;

	@Override
    @Action(
        value = "table",
        results = {
            @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.events, gridModel.*.vacancies, gridModel.*.picture, gridModel.*.pictureItems, gridModel.*.groups"})
        }
    )
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
		setRecord(this.locationDaoLocal.countAll());

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
				pageList = this.locationDaoLocal.findAll(from, getRows(), "l.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("zip")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.address.zip", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("city")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.address.city", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("country")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.address.country", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("telephone")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.contactDetails.telephone", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("fax")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.contactDetails.fax", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("mobile")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.contactDetails.mobile", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("event")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.event", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				pageList = this.locationDaoLocal.findAll(from, getRows(), "l.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "modifiedBy", "asc");
            } else {
				pageList = this.locationDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
            if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.uuid", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("zip")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.address.zip", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("city")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.address.city", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("country")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.address.country", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("telephone")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.contactDetails.telephone", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("fax")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.contactDetails.fax", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("mobile")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.contactDetails.mobile", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("event")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.event", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.created", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "l.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                pageList = this.locationDaoLocal.findAll(from, getRows(), "modifiedBy", "desc");
            } else {
                pageList = this.locationDaoLocal.findAll(from, getRows());
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
	public List<Location> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Location> gridModel) {
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
