package de.tivsource.page.admin.actions.others.gallery;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.enumeration.GalleryType;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="galleryEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/edit_form.jsp")
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

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Gallery gallery;

    private String lang;

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "galleryEditForm"),
        				@Result(name = "error",   type = "tiles", location = "galleryEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(gallery != null) {
    		LOGGER.info("UUID des Events: " + gallery.getUuid());
    		Gallery dbGallery = galleryDaoLocal.findByUuid(gallery.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                gallery.getDescriptionMap().put(Language.DE, dbGallery.getDescriptionObject(Language.DE));
                String noLineBreaks = gallery.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbGallery.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbGallery.getDescriptionMap().get(Language.EN).setKeywords(gallery.getKeywords(Language.EN));
                dbGallery.getDescriptionMap().get(Language.EN).setName(gallery.getName(Language.EN));
            } else {
            	String noLineBreaks = gallery.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbGallery.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbGallery.getDescriptionMap().get(Language.DE).setKeywords(gallery.getKeywords(Language.DE));;
                dbGallery.getDescriptionMap().get(Language.DE).setName(gallery.getName(Language.DE));
            }

    		dbGallery.setModifiedAddress(remoteAddress);
    		dbGallery.setModified(new Date());
    		dbGallery.setModifiedBy(remoteUser);
    		dbGallery.setVisible(gallery.getVisible());
    		dbGallery.setOrderNumber(gallery.getOrderNumber());
    		dbGallery.setTechnical(gallery.getTechnical());
    		dbGallery.setPicture(gallery.getPicture());
    		dbGallery.setPictureOnPage(gallery.getPictureOnPage());
    		dbGallery.setType(gallery.getType());

    		galleryDaoLocal.merge(dbGallery);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Check ob gallery gesetzt wurde
		return pictureDaoLocal.findAll(gallery.getUuid());
	}

	public List<GalleryType> getGalleryTypeList() {
        return Arrays.asList(GalleryType.values());
    }

}// Ende class
