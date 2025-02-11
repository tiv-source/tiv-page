package de.tivsource.page.admin.actions.others.exhibition;

import java.util.Date;

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
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.entity.exhibition.Exhibition;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="exhibitionDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/application.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/delete_error.jsp")
  }),
  @TilesDefinition(name="pageDatabaseError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/database_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1007882449894584851L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    private Exhibition exhibition;

    @StrutsParameter(depth=3)
    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

    @Override
    @Actions({
        @Action(
            value = "delete",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input", type="tiles", location = "exhibitionDeleteError"),
                @Result(name = "error", type="tiles", location = "exhibitionDeleteError"),
                @Result(name = "database", type="tiles", location = "pageDatabaseError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(exhibition != null) {
            Exhibition dbExhibition = exhibitionDaoLocal.findByUuid(exhibition.getUuid());
            if(!exhibitionDaoLocal.hasMenuEntry(dbExhibition.getUuid())) {
                dbExhibition.setModified(new Date());
                dbExhibition.setModifiedBy(remoteUser);
                dbExhibition.setModifiedAddress(remoteAddress);
                exhibitionDaoLocal.merge(dbExhibition);
                exhibitionDaoLocal.delete(dbExhibition);
                return SUCCESS;
            }
            return "database";
        }
        return ERROR;
    }// Ende execute()

}// Ende class
