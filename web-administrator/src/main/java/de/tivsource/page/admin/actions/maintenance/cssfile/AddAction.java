package de.tivsource.page.admin.actions.maintenance.cssfile;

import java.util.Date;
import java.util.UUID;

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
import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssFileAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/add_form.jsp")
  }),
  @TilesDefinition(name="cssFileAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3442711359941308571L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="CSSFileDao")
    private CSSFileDaoLocal cssFileDaoLocal;

    private CSSFile cssFile;

    public CSSFile getCssFile() {
        return cssFile;
    }

    public void setCssFile(CSSFile cssFile) {
        this.cssFile = cssFile;
    }

	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "cssFileAddForm"),
        				@Result(name = "error", type="tiles", location = "cssFileAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(cssFile != null) {
    		/*
             *  Erzeuge die CSS-Datei.
             */
            if(cssFile.getUploadFile() != null) {
                cssFile.generate();
            }

    	    cssFile.setUuid(UUID.randomUUID().toString());
    	    cssFile.setModified(new Date());
    	    cssFile.setCreated(new Date());
    	    cssFile.setModifiedBy(remoteUser);
    	    cssFile.setModifiedAddress(remoteAddress);
    	    cssFileDaoLocal.merge(cssFile);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

}// Ende class
