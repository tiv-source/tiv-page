package de.tivsource.page.admin.actions.others.companiongroup;

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
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="companionGroupAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companiongroup/add_form.jsp")
  }),
  @TilesDefinition(name="companionGroupAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companiongroup/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 142660822594614677L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private CompanionGroup companionGroup;

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

    public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
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
                @Result(name = "input", type="tiles", location = "companionGroupAddForm"),
                @Result(name = "error", type="tiles", location = "companionGroupAddError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companionGroup != null) {
    	    companionGroup.setUuid(UUID.randomUUID().toString());
    	    companionGroup.setModified(new Date());
    	    companionGroup.setCreated(new Date());
    	    companionGroup.setModifiedBy(remoteUser);
    	    companionGroup.setModifiedAddress(remoteAddress);


    	    companionGroup.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    companionGroup.getDescriptionMap().get(Language.DE).setNamingItem(companionGroup);
    	    companionGroup.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = companionGroup.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    companionGroup.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

            companionGroup.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            companionGroup.getDescriptionMap().get(Language.EN).setNamingItem(companionGroup);
            companionGroup.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            companionGroup.getImage().setUuid(UUID.randomUUID().toString());
            companionGroup.getImage().generate();
            companionGroup.getImage().setCreated(new Date());
            companionGroup.getImage().setModified(new Date());
            companionGroup.getImage().setModifiedAddress(remoteAddress);
            companionGroup.getImage().setModifiedBy(remoteUser);

            companionGroupDaoLocal.merge(companionGroup);

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
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.companionGroup = new CompanionGroup();
            this.companionGroup.setImage(new PictureItemImage());
            this.companionGroup.getImage().setPictureItem(this.companionGroup);
            this.companionGroup.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
