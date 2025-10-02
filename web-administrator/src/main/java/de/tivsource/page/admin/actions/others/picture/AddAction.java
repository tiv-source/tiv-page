package de.tivsource.page.admin.actions.others.picture;

import java.util.Date;
import java.util.Iterator;
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
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.entity.picture.PictureImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pictureAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/picture/add_form.jsp")
  }),
  @TilesDefinition(name="pictureAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/picture/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5152279376430754651L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    @InjectEJB(name="GalleryDao")
    private GalleryDaoLocal galleryDaoLocal;

    private Picture picture;

    @StrutsParameter(depth=3)
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pictureAddForm"),
        				@Result(name = "error", type="tiles", location = "pictureAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(picture != null) {
    	    picture.setUuid(UUID.randomUUID().toString());
    	    picture.setModified(new Date());
    	    picture.setCreated(new Date());
    	    picture.setModifiedBy(remoteUser);
    	    picture.setModifiedAddress(remoteAddress);

    	    picture.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    picture.getDescriptionMap().get(Language.DE).setPicture(picture);
    	    picture.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = picture.getDescriptionMap().get(Language.DE).getDescription().replaceAll("(\\r|\\n)", "");
    	    picture.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

    	    picture.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
    	    picture.getDescriptionMap().get(Language.EN).setPicture(picture);
    	    picture.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
    	    picture.getDescriptionMap().get(Language.EN).setName(picture.getDescriptionMap().get(Language.DE).getName());
    	    picture.getDescriptionMap().get(Language.EN).setDescription(picture.getDescriptionMap().get(Language.DE).getDescription());
    	    picture.getDescriptionMap().get(Language.EN).setKeywords(picture.getDescriptionMap().get(Language.DE).getKeywords());

    	    picture.getImage().setUuid(UUID.randomUUID().toString());
    	    picture.getImage().generate();
    	    picture.getImage().setCreated(new Date());
    	    picture.getImage().setModified(new Date());
    	    picture.getImage().setModifiedAddress(remoteAddress);
    	    picture.getImage().setModifiedBy(remoteUser);

    	    // Speichere Bild in der Datenbank
    	    pictureDaoLocal.merge(picture);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

	public List<Gallery> getGalleryList() {
		return galleryDaoLocal.findAll(0, galleryDaoLocal.countAll());
	}

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("Variable uploadedFiles ist nicht leer.");
            Iterator<UploadedFile> ufIterator = uploadedFiles.iterator();
            while(ufIterator.hasNext()) {
                UploadedFile next = ufIterator.next();
                LOGGER.info("UploadedFile für Input-Name: " + next.getInputName() + " gefunden.");
                if(next.getInputName().equalsIgnoreCase("picture.image")) {
                    this.picture = new Picture();
                    this.picture.setImage(new PictureImage());
                    this.picture.getImage().setPicture(this.picture);
                    this.picture.getImage().setUploadFile(UploadedFileToUploadFile.convert(next));
                }                
            }// Ende while()
         }
    }// Ende withUploadedFiles(List<UploadedFile> uploadedFiles)

}// Ende class
