package de.tivsource.page.admin.actions.maintenance.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.property.PropertyDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/files/add_form.jsp")
  }),
  @TilesDefinition(name="cssAddError",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/files/css_add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

    /**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 6514256494686145951L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private File file;

    private String fileContentType;

    private String fileFileName;

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    @StrutsParameter
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the fileContentType
     */
    public String getFileContentType() {
        return fileContentType;
    }

    @StrutsParameter
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    /**
     * @return the fileFileName
     */
    public String getFileFileName() {
        return fileFileName;
    }

    @StrutsParameter
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    
    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = {
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "cssAddForm"),
        				@Result(name = "error", type="tiles", location = "cssAddError") 
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	String pathToFiles = propertyDaoLocal.findByKey("upload-path").getValue();

    	File fileToCreate = new File(pathToFiles, fileFileName);
        // Wenn die Datei noch nicht existiert wird Sie erstellt.
        if (!fileToCreate.exists()) {
        	saveCssFile(file, fileToCreate);
            return SUCCESS;
        }
        else {
        	return ERROR;
        }
        

    	
    }// Ende execute()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            file = new File(uploadedFile.getAbsolutePath());
            fileContentType = uploadedFile.getContentType();
            fileFileName = uploadedFile.getOriginalName();
         }
    }

    private static void saveCssFile(File source, File destination) throws Exception {
        byte[] buffer = new byte[(int) source.length()];
        InputStream in = new FileInputStream(source);
        in.read(buffer);
        FileOutputStream fileOutStream = new FileOutputStream(destination);
        fileOutStream.write(buffer);
        fileOutStream.flush();
        fileOutStream.close();
        in.close();
    }
    
}// Ende class
