package de.tivsource.page.admin.actions.others.companiongroup;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.picture.Picture;

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
  })
})
public class AddAction extends EmptyAction {

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

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private CompanionGroup companionGroup;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

    public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
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
    	    
    		companionGroupDaoLocal.merge(companionGroup);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll(propertyDaoLocal.findByKey("gallery.uuid.for.companion.group.picture").getValue());
	}

}// Ende class
