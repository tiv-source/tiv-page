/**
 * 
 */
package de.tivsource.page.admin.actions.academy.course;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class UnitJsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 548780057634043569L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(UnitJsonAction.class);
	
	@InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

	private Course course;
	private List<CourseUnit> gridModel;
	private List<CourseUnit> courseList;
	private Integer rows = 0;
	private Integer page = 1;
	private Integer total = 0;
	private Integer record = 0;
	private String sord;
	private String sidx;

	@Override
    @Actions({
        @Action(
            value = "unit/*/table",
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
                            + "gridModel.*.course, "
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
                            + "gridModel.*.pictureItems, "
                            + "gridModel.*.units"
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

        String courseUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Course Uuid: " + courseUuid);
        courseUuid = courseUuid.replaceAll("/table.html", "");
        courseUuid = courseUuid.replaceAll("/academy/course/unit/", "");
        LOGGER.info("Course Uuid: " + courseUuid);
        course = courseDaoLocal.findByUuid(courseUuid);
	    
		/*
		 * Setze die Anzahl aller Objekte in der Datenbank.
		 */
		setRecord(course.getUnits().size());

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
			    // TODO: Sortierung per Comparator  !!??
			    // from, getRows()
				courseList = new ArrayList<CourseUnit>(course.getUnits());
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
			    courseList = new ArrayList<CourseUnit>(course.getUnits());
            } else {
                courseList = new ArrayList<CourseUnit>(course.getUnits());
			}
		} else if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
		    LOGGER.info("Sortieren nach desc");
			if (getSidx() != null && getSidx().equalsIgnoreCase("uuid")) {
			    courseList = new ArrayList<CourseUnit>(course.getUnits());
			} else if (getSidx() != null && getSidx().equalsIgnoreCase("name")) {
			    courseList = new ArrayList<CourseUnit>(course.getUnits());
            } else {
                courseList = new ArrayList<CourseUnit>(course.getUnits());
			}
		}

		if(courseList != null) {
		    courseList = courseList.subList(from, to);
		}
		setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
		setGridModel(courseList);

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
	public List<CourseUnit> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<CourseUnit> gridModel) {
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
