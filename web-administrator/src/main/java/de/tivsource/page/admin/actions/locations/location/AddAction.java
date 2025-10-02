package de.tivsource.page.admin.actions.locations.location;

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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="locationAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/add_form.jsp")
  }),
  @TilesDefinition(name="locationAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/locations.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/location/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7586573485098206790L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    private Location location;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
	public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
                @Result(name = "input", type="tiles", location = "locationAddForm"),
                @Result(name = "error", type="tiles", location = "locationAddError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(location != null) {
    	    location.setUuid(UUID.randomUUID().toString());
    	    location.setModified(new Date());
    	    location.setCreated(new Date());
    	    location.setModifiedBy(remoteUser);
    	    location.setModifiedAddress(remoteAddress);
    	    
    	    location.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    location.getDescriptionMap().get(Language.DE).setNamingItem(location);
    	    location.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    String noLineBreaks = location.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    location.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
            location.getDescriptionMap().get(Language.EN).setDescription(location.getDescriptionMap().get(Language.DE).getDescription());
            location.getDescriptionMap().get(Language.EN).setKeywords(location.getDescriptionMap().get(Language.DE).getKeywords());
            location.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
            location.getDescriptionMap().get(Language.EN).setName(location.getDescriptionMap().get(Language.DE).getName());
            location.getDescriptionMap().get(Language.EN).setNamingItem(location);
            location.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            
            location.setTechnical("LOCATION_" + location.getUuid());

            location.getImage().setUuid(UUID.randomUUID().toString());
            location.getImage().generate();
            location.getImage().setCreated(new Date());
            location.getImage().setModified(new Date());
            location.getImage().setModifiedAddress(remoteAddress);
            location.getImage().setModifiedBy(remoteUser);
            
    		locationDaoLocal.merge(location);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("Variable uploadedFiles ist nicht leer.");
            Iterator<UploadedFile> ufIterator = uploadedFiles.iterator();
            while(ufIterator.hasNext()) {
                UploadedFile next = ufIterator.next();
                LOGGER.info("UploadedFile für Input-Name: " + next.getInputName() + " gefunden.");
                if(next.getInputName().equalsIgnoreCase("location.image")) {
                    this.location = new Location();
                    this.location.setImage(new PictureItemImage());
                    this.location.getImage().setPictureItem(this.location);
                    this.location.getImage().setUploadFile(UploadedFileToUploadFile.convert(next));
                }                
            }// Ende while()
         }
    }// Ende withUploadedFiles(List<UploadedFile> uploadedFiles)

}// Ende class
