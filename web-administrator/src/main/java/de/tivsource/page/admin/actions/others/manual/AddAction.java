package de.tivsource.page.admin.actions.others.manual;

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
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="manualAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/add_form.jsp")
  }),
  @TilesDefinition(name="manualAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1439494906783805043L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Manual manual;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Manual getManual() {
        return manual;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
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
        				@Result(name = "input", type="tiles", location = "manualAddForm"),
        				@Result(name = "error", type="tiles", location = "manualAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(manual != null) {
    	    manual.setUuid(UUID.randomUUID().toString());
    	    manual.setModified(new Date());
    	    manual.setCreated(new Date());
    	    manual.setModifiedBy(remoteUser);
    	    manual.setModifiedAddress(remoteAddress);


    	    manual.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    manual.getDescriptionMap().get(Language.DE).setNamingItem(manual);
    	    manual.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = manual.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    manual.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
    	    manual.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    manual.getContentMap().get(Language.DE).setContentItem(manual);
    	    manual.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    manual.getContentMap().get(Language.DE).setCreated(new Date());
    	    manual.getContentMap().get(Language.DE).setModified(new Date());


            manual.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            manual.getDescriptionMap().get(Language.EN).setNamingItem(manual);
            manual.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            manual.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            manual.getContentMap().get(Language.EN).setContentItem(manual);
            manual.getContentMap().get(Language.EN).setLanguage(Language.EN);
            manual.getContentMap().get(Language.EN).setCreated(new Date());
            manual.getContentMap().get(Language.EN).setModified(new Date());

            manual.getImage().setUuid(UUID.randomUUID().toString());
            manual.getImage().generate();
            manual.getImage().setCreated(new Date());
            manual.getImage().setModified(new Date());
            manual.getImage().setModifiedAddress(remoteAddress);
            manual.getImage().setModifiedBy(remoteUser);

    		manualDaoLocal.merge(manual);

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
    }

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.manual = new Manual();
            this.manual.setImage(new PictureItemImage());
            this.manual.getImage().setPictureItem(this.manual);
            this.manual.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
