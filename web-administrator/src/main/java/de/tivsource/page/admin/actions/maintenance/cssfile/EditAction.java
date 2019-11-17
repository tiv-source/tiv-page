package de.tivsource.page.admin.actions.maintenance.cssfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssFileEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/edit_form.jsp")
  }),
  @TilesDefinition(name="cssFileEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssfile/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -5770235481070099665L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="CSSFileDao")
    private CSSFileDaoLocal cssFileDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

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
            value = "edit",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input",   type = "tiles", location = "cssFileEditForm"),
                @Result(name = "error",   type = "tiles", location = "cssFileEditError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(cssFile != null) {
            LOGGER.info("UUID der CSS-Datei: " + cssFile.getUuid());
            CSSFile dbCssFile = cssFileDaoLocal.findByUuid(cssFile.getUuid());

            // Erstelle neue Version der CSS-Datei
            if(cssFile.getUploadFile() != null) {
                LOGGER.debug("Es wurde eine neue CSS-Datei hochgeladen.");
                dbCssFile.setUploadFile(cssFile.getUploadFile());
                dbCssFile.generate();
            }

            dbCssFile.setName(cssFile.getName());
            dbCssFile.setDescription(cssFile.getDescription());
            dbCssFile.setPriority(cssFile.getPriority());
            dbCssFile.setModifiedAddress(remoteAddress);
            dbCssFile.setModified(new Date());
            dbCssFile.setModifiedBy(remoteUser);
            cssFileDaoLocal.merge(dbCssFile);

            // Aktualisiere das Datum
            setLastModified();
            
            return SUCCESS;
        }

        // Wenn es keine Datei gab wird die Fehlerseite aufgrufen
        return ERROR;
    }// Ende execute()

    private void setLastModified() {
        // Lese Daten des Remote Benutzers
        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        // Erstelle neues Aktualisierungsdatum
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String newDate = simpleDateFormat.format(new Date());

        // Hole Schlüssel mit Aktualisierungsdatum aus der Datenbank oder erstelle neues Objekt
        Property lastModified = propertyDaoLocal.findByKey("cssFile.lastModified");

        if(lastModified != null) {
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        } else {
            lastModified = new Property();
            lastModified.setCreated(new Date());
            lastModified.setKey("cssFile.lastModified");
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        }
        propertyDaoLocal.merge(lastModified);
    }

}// Ende class
