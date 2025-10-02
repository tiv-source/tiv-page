package de.tivsource.page.admin.actions.others.gallery;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.enumeration.GalleryType;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="galleryAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/add_form.jsp")
  }),
  @TilesDefinition(name="galleryAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/gallery/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3442711359941308571L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

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
            value = "add",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input", type="tiles", location = "galleryAddForm"),
                @Result(name = "error", type="tiles", location = "galleryAddError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(gallery != null) {
    	    gallery.setUuid(UUID.randomUUID().toString());
    	    gallery.setModified(new Date());
    	    gallery.setCreated(new Date());
    	    gallery.setModifiedBy(remoteUser);
    	    gallery.setModifiedAddress(remoteAddress);

    	    gallery.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    gallery.getDescriptionMap().get(Language.DE).setNamingItem(gallery);
    	    gallery.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = gallery.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    gallery.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    gallery.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    gallery.getDescriptionMap().get(Language.EN).setNamingItem(gallery);
    	    gallery.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    gallery.getDescriptionMap().get(Language.EN).setName(gallery.getDescriptionMap().get(Language.DE).getName());
    	    gallery.getDescriptionMap().get(Language.EN).setDescription(gallery.getDescriptionMap().get(Language.DE).getDescription());
    	    gallery.getDescriptionMap().get(Language.EN).setKeywords(gallery.getDescriptionMap().get(Language.DE).getKeywords());

    	    gallery.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    gallery.getContentMap().get(Language.DE).setContentItem(gallery);
    	    gallery.getContentMap().get(Language.DE).setLanguage(Language.DE);
            gallery.getContentMap().get(Language.DE).setCreated(new Date());
            gallery.getContentMap().get(Language.DE).setModified(new Date());

            gallery.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            gallery.getContentMap().get(Language.EN).setContentItem(gallery);
            gallery.getContentMap().get(Language.EN).setLanguage(Language.EN);
            gallery.getContentMap().get(Language.EN).setContent(gallery.getContent(Language.DE));
            gallery.getContentMap().get(Language.EN).setCreated(new Date());
            gallery.getContentMap().get(Language.EN).setModified(new Date());

    	    gallery.getImage().setUuid(UUID.randomUUID().toString());
    	    gallery.getImage().generate();
    	    gallery.getImage().setCreated(new Date());
    	    gallery.getImage().setModified(new Date());
    	    gallery.getImage().setModifiedAddress(remoteAddress);
    	    gallery.getImage().setModifiedBy(remoteUser);
    	    
    	    galleryDaoLocal.merge(gallery);
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

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.gallery = new Gallery();
            this.gallery.setImage(new PictureItemImage());
            this.gallery.getImage().setPictureItem(this.gallery);
            this.gallery.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
