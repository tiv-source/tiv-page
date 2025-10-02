package de.tivsource.page.admin.actions.others.subsumption;

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
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.entity.subsumption.Subsumption;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="subsumptionAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/add_form.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 4585178769978663997L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="SubsumptionDao")
    private SubsumptionDaoLocal subsumptionDaoLocal;
    
    private Subsumption subsumption;
    
    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Subsumption getSubsumption() {
        return subsumption;
    }

    public void setSubsumption(Subsumption subsumption) {
        this.subsumption = subsumption;
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
        				@Result(name = "input", type="tiles", location = "subsumptionAddForm"),
        				@Result(name = "error", type="tiles", location = "subsumptionAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(subsumption != null) {
    	    subsumption.setUuid(UUID.randomUUID().toString());
    	    subsumption.setModified(new Date());
    	    subsumption.setCreated(new Date());
    	    subsumption.setModifiedBy(remoteUser);
    	    subsumption.setModifiedAddress(remoteAddress);


    	    subsumption.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    subsumption.getDescriptionMap().get(Language.DE).setNamingItem(subsumption);
    	    subsumption.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = subsumption.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    subsumption.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
    	    subsumption.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    subsumption.getContentMap().get(Language.DE).setContentItem(subsumption);
    	    subsumption.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    subsumption.getContentMap().get(Language.DE).setCreated(new Date());
    	    subsumption.getContentMap().get(Language.DE).setModified(new Date());


            subsumption.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            subsumption.getDescriptionMap().get(Language.EN).setNamingItem(subsumption);
            subsumption.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            subsumption.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            subsumption.getContentMap().get(Language.EN).setContentItem(subsumption);
            subsumption.getContentMap().get(Language.EN).setLanguage(Language.EN);
            subsumption.getContentMap().get(Language.EN).setCreated(new Date());
            subsumption.getContentMap().get(Language.EN).setModified(new Date());

            subsumption.getImage().setUuid(UUID.randomUUID().toString());
            subsumption.getImage().generate();
            subsumption.getImage().setCreated(new Date());
            subsumption.getImage().setModified(new Date());
            subsumption.getImage().setModifiedAddress(remoteAddress);
            subsumption.getImage().setModifiedBy(remoteUser);

    		subsumptionDaoLocal.merge(subsumption);

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
            this.subsumption = new Subsumption();
            this.subsumption.setImage(new PictureItemImage());
            this.subsumption.getImage().setPictureItem(this.subsumption);
            this.subsumption.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
