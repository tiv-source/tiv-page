package de.tivsource.page.admin.actions.others.manual;

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
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.manual.Manual;
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

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Manual manual;

    private String lang;

    public Manual getManual() {
        return manual;
    }

	public void setManual(Manual manual) {
        this.manual = manual;
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
        				@Result(name = "input",   type = "tiles", location = "manualEditForm"),
        				@Result(name = "error",   type = "tiles", location = "manualEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(manual != null) {
    		LOGGER.info("News UUID: " + manual.getUuid());
    		Manual dbManual = manualDaoLocal.findByUuid(manual.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                manual.getContentMap().put(Language.DE, dbManual.getContentObject(Language.DE));
                dbManual.getContentMap().get(Language.EN).setContent(manual.getContent(Language.EN));
                dbManual.getContentMap().get(Language.EN).setModified(new Date());

                manual.getDescriptionMap().put(Language.DE, dbManual.getDescriptionObject(Language.DE));
                String noLineBreaks = manual.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbManual.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbManual.getDescriptionMap().get(Language.EN).setKeywords(manual.getKeywords(Language.EN));
                dbManual.getDescriptionMap().get(Language.EN).setName(manual.getName(Language.EN));
            } else {
                dbManual.getContentMap().get(Language.DE).setContent(manual.getContent(Language.DE));
                dbManual.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = manual.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbManual.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbManual.getDescriptionMap().get(Language.DE).setKeywords(manual.getKeywords(Language.DE));;
                dbManual.getDescriptionMap().get(Language.DE).setName(manual.getName(Language.DE));
            }


    		dbManual.setModified(new Date());
    		dbManual.setVisible(manual.getVisible());
    		dbManual.setModifiedBy(remoteUser);
    		dbManual.setModifiedAddress(remoteAddress);
    		dbManual.setPicture(manual.getPicture());
    		dbManual.setPictureOnPage(manual.getPictureOnPage());


    		manualDaoLocal.merge(dbManual);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("f8fed35d-6df2-4d74-835d-fcf64faf2b5a");
	}

}// Ende class
