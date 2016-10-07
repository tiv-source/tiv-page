package de.tivsource.page.admin.actions.maintenance.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
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
public class AddAction extends EmptyAction {

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

    private String contentType;

    private String filename;
    
    public void setFile(File file) {
        this.file = file;
    }

    public void setFileContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileFileName(String filename) {
        this.filename = filename;
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

    	File fileToCreate = new File(pathToFiles, filename);
        // Wenn die Datei noch nicht existiert wird Sie erstellt.
        if (!fileToCreate.exists()) {
        	saveCssFile(file, fileToCreate);
            return SUCCESS;
        }
        else {
        	return ERROR;
        }
        

    	
    }// Ende execute()

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
