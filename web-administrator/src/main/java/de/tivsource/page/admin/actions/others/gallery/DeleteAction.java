package de.tivsource.page.admin.actions.others.gallery;

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
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.entity.gallery.Gallery;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="galleryDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/delete_error.jsp")
  }),
  @TilesDefinition(name="galleryReferences", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/references.jsp")
  }),
  @TilesDefinition(name="galleryMenuEntryError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/menuentry_error.jsp")
  }),
  @TilesDefinition(name="gallerySubSumptionError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/subsumption_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -4785386555841300052L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Gallery gallery;

    @StrutsParameter(depth=3)
    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    @Override
    @Actions({
        @Action(
            value = "delete",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input", type="tiles", location = "galleryDeleteError"),
                @Result(name = "error", type="tiles", location = "galleryDeleteError"),
                @Result(name = "references", type="tiles", location = "galleryReferences"),
                @Result(name = "menuentry", type="tiles", location = "galleryMenuEntryError"),
                @Result(name = "subsumption", type="tiles", location = "gallerySubSumptionError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(gallery != null) {
            Gallery dbGallery = galleryDaoLocal.findByUuid(gallery.getUuid());
            if(!galleryDaoLocal.hasReferences(dbGallery.getUuid())) {
                if(!galleryDaoLocal.hasMenuEntry(dbGallery.getUuid())) {
                    if(!galleryDaoLocal.hasSubSumption(dbGallery.getUuid())) {
                        dbGallery.setModified(new Date());
                        dbGallery.setModifiedBy(remoteUser);
                        dbGallery.setModifiedAddress(remoteAddress);
                        galleryDaoLocal.merge(dbGallery);
                        galleryDaoLocal.delete(dbGallery);
                        return SUCCESS;
                    }
                    return "subsumption";
                }
                return "menuentry";
            }
          	return "references";
    	}
   		return ERROR;

    }// Ende execute()
	
}// Ende class
