/**
 * 
 */
package de.tivsource.page.admin.actions.system.property;

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
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;
/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3163434204065331938L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

	private List<Property> gridModel;
	private List<Property> propertyList;
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
        		results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.users"}) }
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
		setRecord(this.propertyDaoLocal.countAll());

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
			if (getSidx() != null && getSidx().equalsIgnoreCase("key")) {
				propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.key", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("value")) {
				propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.value", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.modifiedBy", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedAddress")) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.modifiedAddress", "asc");
            } else {
				propertyList = this.propertyDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("key")) {
				propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.key", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("value")) {
				propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.value", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.modifiedBy", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedAddress")) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), "p.modifiedAddress", "desc");
            } else {
				propertyList = this.propertyDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(propertyList);

		LOGGER.info("Rows:" + rows);
		LOGGER.info("Page:" + page);
		LOGGER.info("Total:" + total);
		LOGGER.info("Record:" + record);
		LOGGER.info("Sord:" + sord);
		LOGGER.info("Sidx:" + sidx);
		
		return execute();
	}

	/**
	 * Methode, die die Anzahl der Zeilen zurück liefert, die in der
	 * Tabelle pro Seite enthalten sind.
	 *
	 * @return liefert die Anzahl der Zeilen in der Tablle pro Seite
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * Methode zum setzen der Anzahl der zurück zuliefernden Zeilen,
	 * kann durch den Benutzer gesetzt werden.
	 * Deshalb ist diese Methode mit @StrutsParameter annotiert.
	 *
	 * @param rows - Anzahl der Zeilen, die angezeigt werden sollen
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
	public List<Property> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Property> gridModel) {
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
