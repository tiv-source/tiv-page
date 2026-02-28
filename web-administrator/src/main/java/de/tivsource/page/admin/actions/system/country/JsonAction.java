/**
 * 
 */
package de.tivsource.page.admin.actions.system.country;

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
import de.tivsource.page.common.country.Country;
import de.tivsource.page.dao.country.CountryDaoLocal;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = -4004692338955805249L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    private List<Country> gridModel;
    private List<Country> countryList;

    private Integer rows = 0;
    private Integer page = 1;
    private Integer total = 0;
    private Integer record = 0;
    private String sord;
    private String sidx;

    @Override
    @Actions({ @Action(value = "table", results = { @Result(name = "success", type = "json", params = {
            "excludeProperties", "gridModel.*.provinceSet" }) }) })
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
        setRecord(this.countryDaoLocal.countAll());

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
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.uuid", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("language")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.language", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("alpha2")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.alpha2", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("alpha3")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.alpha3", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("numeric")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.numeric", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("areaCode")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.areaCode", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.created", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.modifiedBy", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedAddress")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.modifiedAddress", "asc");
            } else {
                countryList = this.countryDaoLocal.findAll(from, getRows());
            }
        } else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
            LOGGER.info("Sortieren nach desc");
            if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.uuid", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("locale")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.locale", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("alpha2")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.alpha2", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("alpha3")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.alpha3", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("numeric")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.numeric", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("areaCode")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.areaCode", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.created", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.modifiedBy", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedAddress")) {
                countryList = this.countryDaoLocal.findAll(from, getRows(), "c.modifiedAddress", "desc");
            } else {
                countryList = this.countryDaoLocal.findAll(from, getRows());
            }
        }

        setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
        setGridModel(countryList);

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
    public List<Country> getGridModel() {
        return gridModel;
    }

    /**
     * @param gridModel
     *            an collection that contains the actual data
     */
    public void setGridModel(List<Country> gridModel) {
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
