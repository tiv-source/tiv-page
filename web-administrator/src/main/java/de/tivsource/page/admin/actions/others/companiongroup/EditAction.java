package de.tivsource.page.admin.actions.others.companiongroup;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

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
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -8055397581589809541L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private CompanionGroup companionGroup;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private String lang;

    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

	public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
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
        				@Result(name = "input",   type = "tiles", location = "companionGroupEditForm"),
        				@Result(name = "error",   type = "tiles", location = "companionGroupEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companionGroup != null) {
    		LOGGER.info("News UUID: " + companionGroup.getUuid());
    		CompanionGroup dbCompanionGroup = companionGroupDaoLocal.findByUuid(companionGroup.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                companionGroup.getDescriptionMap().put(Language.DE, dbCompanionGroup.getDescriptionObject(Language.DE));
                String noLineBreaks = companionGroup.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbCompanionGroup.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbCompanionGroup.getDescriptionMap().get(Language.EN).setKeywords(companionGroup.getKeywords(Language.EN));
                dbCompanionGroup.getDescriptionMap().get(Language.EN).setName(companionGroup.getName(Language.EN));
            } else {
                String noLineBreaks = companionGroup.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbCompanionGroup.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbCompanionGroup.getDescriptionMap().get(Language.DE).setKeywords(companionGroup.getKeywords(Language.DE));;
                dbCompanionGroup.getDescriptionMap().get(Language.DE).setName(companionGroup.getName(Language.DE));
            }


            dbCompanionGroup.setOrderNumber(companionGroup.getOrderNumber());
            dbCompanionGroup.setTechnical(companionGroup.getTechnical());
    		dbCompanionGroup.setModified(new Date());
    		dbCompanionGroup.setVisible(companionGroup.getVisible());
    		dbCompanionGroup.setModifiedBy(remoteUser);
    		dbCompanionGroup.setModifiedAddress(remoteAddress);
    		dbCompanionGroup.setPicture(companionGroup.getPicture());
    		dbCompanionGroup.setPictureOnPage(companionGroup.getPictureOnPage());


    		companionGroupDaoLocal.merge(dbCompanionGroup);
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
