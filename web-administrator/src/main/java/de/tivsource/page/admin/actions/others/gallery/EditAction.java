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
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
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
  }),
  @TilesDefinition(name="galleryEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/edit_error.jsp")
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

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Gallery gallery;

    private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
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
                LOGGER.info("Name der Gallery: " + gallery.getName(Language.EN));
            } else {
            	String noLineBreaks = gallery.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbGallery.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbGallery.getDescriptionMap().get(Language.DE).setKeywords(gallery.getKeywords(Language.DE));;
                dbGallery.getDescriptionMap().get(Language.DE).setName(gallery.getName(Language.DE));
                LOGGER.info("Name der Gallery: " + gallery.getName(Language.DE));
            }

    		dbGallery.setModifiedAddress(remoteAddress);
    		dbGallery.setModified(new Date());
    		dbGallery.setModifiedBy(remoteUser);
    		dbGallery.setVisible(gallery.getVisible());
    		dbGallery.setOrderNumber(gallery.getOrderNumber());
    		dbGallery.setTechnical(gallery.getTechnical());
    		dbGallery.setPictureOnPage(gallery.getPictureOnPage());
    		dbGallery.setType(gallery.getType());
    		dbGallery.setCssGroup(gallery.getCssGroup());

    		galleryDaoLocal.merge(dbGallery);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

	public List<GalleryType> getGalleryTypeList() {
        return Arrays.asList(GalleryType.values());
    }

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
