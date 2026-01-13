/**
 * 
 */
package de.tivsource.page.admin.actions.academy.lecturer;

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
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.entity.lecturer.Lecturer;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 7579349948888829648L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);
	
	@InjectEJB(name="LecturerDao")
    private LecturerDaoLocal lecturerDaoLocal;

	private List<Lecturer> gridModel;
	private List<Lecturer> lecturerList;
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
                @Result(name = "success", type="json", 
                    params={"excludeProperties", ""
                            + "gridModel.*.contentMap.DE.contentItem, "
                            + "gridModel.*.contentMap.EN.contentItem, "
                            + "gridModel.*.contentMap, "
                            + "gridModel.*.cssGroup.files.*.uploadFile, "
                            + "gridModel.*.cssGroup.files.*.uploadFileContentType, "
                            + "gridModel.*.cssGroup.files.*.uploadFileFileName, "
                            + "gridModel.*.cssGroup, "
                            + "gridModel.*.courses, "
                            + "gridModel.*.descriptionMap.DE.namingItem, "
                            + "gridModel.*.descriptionMap.EN.namingItem, "
                            + "gridModel.*.groups, "
                            + "gridModel.*.image.largeFileInputStream, "
                            + "gridModel.*.image.microFileInputStream, "
                            + "gridModel.*.image.normalFileInputStream, "
                            + "gridModel.*.image.originalFileInputStream, "
                            + "gridModel.*.image.pictureItem, "
                            + "gridModel.*.image.smallFileInputStream, "
                            + "gridModel.*.image.thumbnailFileInputStream, "
                            + "gridModel.*.image.uploadFile, "
                            + "gridModel.*.image.uploadFileContentType, "
                            + "gridModel.*.image.uploadFileFileName, "
                            + "gridModel.*.image.large, "                            
                            + "gridModel.*.image.micro, "
                            + "gridModel.*.image.normal, "
                            + "gridModel.*.image.original, "
                            + "gridModel.*.image.small, "
                            + "gridModel.*.image.thumbnail, "
                            + "gridModel.*.picture, "
                            + "gridModel.*.pictureItems"
                    }// Ende params
                )// Ende result
            }// Ende results
        )// Ende Action
    })// Ende Actions
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
		setRecord(this.lecturerDaoLocal.countAll());

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
				lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.uuid", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "dm.name", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.visible", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
				lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.created", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.modified", "asc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
				lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.modifiedBy", "asc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.technical", "asc");
            } else {
				lecturerList = this.lecturerDaoLocal.findAll(from, getRows());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
				lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.uuid", "desc");
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "dm.name", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("visible")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.visible", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("created")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.created", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modified")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.modified", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("modifiedBy")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.modifiedBy", "desc");
            } else if (getSidx() != null && getSidx().equalsIgnoreCase("technical")) {
                lecturerList = this.lecturerDaoLocal.findAll(from, getRows(), "l.technical", "desc");
            } else {
				lecturerList = this.lecturerDaoLocal.findAll(from, getRows());
			}
		}

		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(lecturerList);

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
	public List<Lecturer> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Lecturer> gridModel) {
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
