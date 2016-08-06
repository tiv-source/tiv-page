package de.tivsource.page.admin.actions.maintenance.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Marc Michele
 * 
 */
@ParentPackage(value = "administratorJson")
public class JsonAction extends ActionSupport {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3264469345260180786L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(JsonAction.class);

    private File folder = new File("/srv/www/htdocs/uploads");

    private File[] rawFileArray = folder.listFiles();

    private List<File> fileList = new ArrayList<File>();
    
    private List<File> gridModel;

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
                results = { @Result(name = "success", type="json", params={"excludeProperties", "gridModel.*.absolute, gridModel.*.absoluteFile, gridModel.*.absolutePath, gridModel.*.canonicalFile, gridModel.*.canonicalPath, gridModel.*.directory, gridModel.*.file, gridModel.*.freeSpace, gridModel.*.parentFile, gridModel.*.path, gridModel.*.totalSpace, gridModel.*.usableSpace, gridModel.*.parent"}) }
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
        for (int i = 0; i < rawFileArray.length; i++) {
            if (rawFileArray[i].isFile()) {
                fileList.add(rawFileArray[i]);
            }
        }
        */

        fileList = Arrays.asList(rawFileArray);

        setRecord(this.fileList.size());

        
        
        setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));
        
        Integer from = page * rows - rows;
        Integer to =  page * rows < record ? page * rows : record;

        setGridModel(fileList.subList(from , to));

        LOGGER.info("Rows:" + rows);
        LOGGER.info("Page:" + page);
        LOGGER.info("Total:" + total);
        LOGGER.info("Record:" + record);
        LOGGER.info("Sord:" + sord);
        LOGGER.info("Sidx:" + sidx);
        
        return execute();
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public List<File> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<File> gridModel) {
        this.gridModel = gridModel;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }
    
}// Ende class
