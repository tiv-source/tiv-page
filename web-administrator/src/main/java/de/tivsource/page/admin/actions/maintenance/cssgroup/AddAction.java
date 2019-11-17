package de.tivsource.page.admin.actions.maintenance.cssgroup;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="cssGroupAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/add_form.jsp")
  }),
  @TilesDefinition(name="cssGroupAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/cssgroup/add_error.jsp")
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

    @InjectEJB(name="CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    private CSSGroup cssGroup;

    public CSSGroup getCssGroup() {
        return cssGroup;
    }

    public void setCssGroup(CSSGroup cssGroup) {
        this.cssGroup = cssGroup;
    }

    @Override
    @Actions({
        @Action(
            value = "add",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input", type="tiles", location = "cssGroupAddForm"),
                @Result(name = "error", type="tiles", location = "cssGroupAddError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if (cssGroup != null) {
            // Füge die Gruppe in die Dateien ein
            Iterator<CSSFile> cssFiles = cssGroup.getFiles().iterator();
            while (cssFiles.hasNext()) {
                CSSFile next = cssFiles.next();
                next.getGroups().add(cssGroup);
            }
            cssGroup.setUuid(UUID.randomUUID().toString());
            cssGroup.setModified(new Date());
            cssGroup.setCreated(new Date());
            cssGroup.setModifiedBy(remoteUser);
            cssGroup.setModifiedAddress(remoteAddress);
            cssGroupDaoLocal.merge(cssGroup);
            return SUCCESS;
        } else {
            return ERROR;
        }

    }// Ende execute()

    public List<CSSFile> getCssFileList() {
        return cssFileDaoLocal.findAll(0, cssFileDaoLocal.countAll());
    }

    public List<Language> getLanguageList() {
        return Arrays.asList(Language.values());
    }

}// Ende class
