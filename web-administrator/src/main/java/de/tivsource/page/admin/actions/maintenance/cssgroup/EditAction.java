package de.tivsource.page.admin.actions.maintenance.cssgroup;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssGroupEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/edit_form.jsp")
  }),
  @TilesDefinition(name="cssGroupEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/edit_error.jsp")
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
    
    @InjectEJB(name="CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    private CSSGroup cssGroup;

    /**
     * @return the cssGroup
     */
    @StrutsParameter(depth=1)
    public CSSGroup getCssGroup() {
        return cssGroup;
    }

    /**
     * @param cssGroup
     *            the cssGroup to set
     */
    public void setCssGroup(CSSGroup cssGroup) {
        this.cssGroup = cssGroup;
    }

    @Override
    @Actions({
        @Action(
            value = "edit",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input",   type = "tiles", location = "cssGroupEditForm"),
                @Result(name = "error",   type = "tiles", location = "cssGroupEditError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if (cssGroup != null) {
            LOGGER.info("UUID der CSS-Datei: " + cssGroup.getUuid());
            // Lösche die vorhanden Files
            cssGroupDaoLocal.deleteFiles(cssGroup);
            CSSGroup dbCssGroup = cssGroupDaoLocal.findByUuid(cssGroup.getUuid());
            LOGGER.info("Anzahl der Einträge in Files Set des Datenbank Objektes: " + dbCssGroup.getFiles().size());
            dbCssGroup.setName(cssGroup.getName());
            dbCssGroup.setDescription(cssGroup.getDescription());
            dbCssGroup.setLanguage(cssGroup.getLanguage());
            dbCssGroup.setModifiedAddress(remoteAddress);
            dbCssGroup.setModified(new Date());
            dbCssGroup.setModifiedBy(remoteUser);
            dbCssGroup.setFiles(createFileSet(cssGroup.getFiles(), dbCssGroup));
            cssGroupDaoLocal.merge(dbCssGroup);
            return SUCCESS;
        } else {
            return ERROR;
        }

    }// Ende execute()

    private SortedSet<CSSFile> createFileSet(SortedSet<CSSFile> files, CSSGroup cssGroup) {
        LOGGER.info("createFileSet(SortedSet<CSSFile> files, CSSGroup cssGroup)");
        if (files != null) {
            Iterator<CSSFile> newCssFiles = files.iterator();
            while (newCssFiles.hasNext()) {
                CSSFile next = newCssFiles.next();
                LOGGER.info("UUID der CSS-Datei die hinzugefügt wird: " + next.getUuid());
                LOGGER.info("Version der CSS-Datei die hinzugefügt wird: " + next.getVersion());
                cssGroup.getFiles().add(cssFileDaoLocal.findByUuid(next.getUuid()));
            }
            // Füge die Gruppe in die Dateien ein
            Iterator<CSSFile> cssFiles = cssGroup.getFiles().iterator();
            while (cssFiles.hasNext()) {
                CSSFile next = cssFiles.next();
                next.getGroups().add(cssGroup);
            }
        }// Ende if
        return cssGroup.getFiles();
    }

}// Ende class
