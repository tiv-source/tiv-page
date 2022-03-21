package de.tivsource.page.admin.actions.others.exhibition.image;

import javax.servlet.http.HttpServletResponse;

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
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.entity.exhibition.Exhibition;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="exhibitionImage", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta", value = "/WEB-INF/tiles/active/meta/default_jquery.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/exhibition/image.jsp")
  })
})
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -2804223524981832247L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @InjectEJB(name = "ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    private Exhibition exhibition;

    private String uncheckExhibition;

    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(String uncheckExhibition) {
        this.uncheckExhibition = uncheckExhibition;
    }

    @Override
    @Actions({ 
        @Action(
            value = "index", 
            results = { @Result(name = "success", type = "tiles", location = "exhibitionImage") }
        ) 
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        this.loadPageParameter();

        // Cache abschalten
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

        return SUCCESS;
    }// Ende execute()

    private void loadPageParameter() {
        if( uncheckExhibition != null && uncheckExhibition != "" && uncheckExhibition.length() > 0) {
            exhibition = exhibitionDaoLocal.findByUuid(uncheckExhibition);
        } else {
            exhibition = new Exhibition();
        }
    }// Ende loadPageParameter()

}// Ende class
