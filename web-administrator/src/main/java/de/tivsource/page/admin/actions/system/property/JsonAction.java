/**
 * 
 */
package de.tivsource.page.admin.actions.system.property;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	
    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

	private List<Property> gridModel;
	private List<Property> propertyList;
	private Integer rows = 0;
	private Integer page = 1;
	private Integer total = 0;
	private Integer record = 0;
	private String sord;
	private String sidx;
	private String filters;

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

        LOGGER.info("Page " + page);
        LOGGER.info("Rows " + rows);
        LOGGER.info("Sorting Order " + sord);
        LOGGER.info("Index Row :" + sidx);
        LOGGER.info("Filters :" + filters);
        LOGGER.info("Build new List");

		int to = (getRows() * getPage());
		int from = to - getRows();

		/*
		 * Sortieren aufsteigen
		 */
		if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
		    LOGGER.info("Sortieren nach asc");
		    if (getSidx() != null) {
		        propertyList = this.propertyDaoLocal.findAll(from, getRows(), createSql("asc"));
		        setRecord(this.propertyDaoLocal.countAll(createSql("asc")));
		    } else {
	            propertyList = this.propertyDaoLocal.findAll(from, getRows());
	            setRecord(this.propertyDaoLocal.countAll());
		    }
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
            if (getSidx() != null) {
                propertyList = this.propertyDaoLocal.findAll(from, getRows(), createSql("desc"));
                setRecord(this.propertyDaoLocal.countAll(createSql("desc")));
            } else {
                propertyList = this.propertyDaoLocal.findAll(from, getRows());
                setRecord(this.propertyDaoLocal.countAll());
            }
		}

        /*
         * Setze die Maximalgrenze auf die Maximale Anzahl
         */
        if (to > getRecord()) {
            to = getRecord();
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

	@StrutsParameter
    public void setFilters(String filters) {
        this.filters = filters;
    }

    private String createSql(String order) {
        String sql = "";
        if(filters != null && createSelect() != null) {
            sql  = sql + createSelect();
            LOGGER.info("Inhalt sql: " +sql);
        }
        if(createOrderBy(order) != null) {
            sql  = sql + createOrderBy(order);
            LOGGER.info("Inhalt sql: " +sql);
        }
        return sql;
    }

    private String createSelect(){
        // Select String
        String selectString = " where";
        // Erzeuge das JsonObject
        JsonObject jsonObject = new JsonParser().parse(filters).getAsJsonObject();
        // Erzeuge JsonArray
        JsonArray jsonArray = jsonObject.getAsJsonArray("rules");

        if(!jsonArray.iterator().hasNext()) {
            return null;
        }

        // Hole die Verknüpfungsart aus dem JsonObjekt
        String groupOp = jsonObject.get("groupOp").getAsString();
        // Durchlaufe die Regeln
        Iterator<JsonElement> jsonIterator = jsonArray.iterator();
        while(jsonIterator.hasNext()) {
            String jsonElement = jsonIterator.next().toString();
            LOGGER.info("JsonElement: " + jsonElement);
            JsonObject jsonFieldObject = new JsonParser().parse(jsonElement).getAsJsonObject();
            String field = jsonFieldObject.get("field").getAsString();
            String op = jsonFieldObject.get("op").getAsString();
            String data = jsonFieldObject.get("data").getAsString();
            selectString = selectString + createField(field, op, data);
            LOGGER.info("SelectString: " + selectString);
            if(jsonIterator.hasNext()) {
                selectString = selectString + " AND";
            }
        }
        return selectString;
    }

    private String createField(String field, String op, String data) {
        if (field != null && field.equalsIgnoreCase("key")){
            return " p.key like '%" + data + "%'";
        } else if (field != null && field.equalsIgnoreCase("unchangeable")) {
            Boolean checkData = data.equalsIgnoreCase("true") ? true : false;
            return " p.unchangeable = " + checkData;
        } else {
            return null;
        }
    }
    
    private String createOrderBy(String order){
        if (sidx != null && sidx.equalsIgnoreCase("key")){
            return " order by p.key " + order;
        } else if (sidx != null && sidx.equalsIgnoreCase("value")) {
            return " order by p.value " + order;
        } else if (sidx != null && sidx.equalsIgnoreCase("unchangeable")) {
            return " order by p.unchangeable " + order;
        } else if (sidx != null && sidx.equalsIgnoreCase("modified")) {
            return " order by p.modified " + order;
        } else if (sidx != null && sidx.equalsIgnoreCase("modifiedBy")) {
            return " order by p.modifiedBy " + order;
        } else if (sidx != null && sidx.equalsIgnoreCase("modifiedAddress")) {
            return " order by p.modifiedAddress " + order;
        } else {
            return null;
        }
    }

}// Ende class
