package de.tivsource.page.admin.actions.page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -5428084532283133908L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pageAddForm"),
        				@Result(name = "error", type="tiles", location = "pageAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(page != null) {
    	    page.setUuid(UUID.randomUUID().toString());
    	    page.setModified(new Date());
    	    page.setCreated(new Date());
    	    page.setModifiedBy(remoteUser);
    	    page.setModifiedAddress(remoteAddress);


    	    page.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    page.getDescriptionMap().get(Language.DE).setNamingItem(page);
    	    page.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = page.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    page.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
    	    page.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    page.getContentMap().get(Language.DE).setContentItem(page);
    	    page.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    page.getContentMap().get(Language.DE).setCreated(new Date());
    	    page.getContentMap().get(Language.DE).setModified(new Date());


            page.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            page.getDescriptionMap().get(Language.EN).setNamingItem(page);
            page.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            page.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            page.getContentMap().get(Language.EN).setContentItem(page);
            page.getContentMap().get(Language.EN).setLanguage(Language.EN);
            page.getContentMap().get(Language.EN).setCreated(new Date());
            page.getContentMap().get(Language.EN).setModified(new Date());
    	    
    		pageDaoLocal.merge(page);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

	public List<Picture> getPictureList() {
		// TODO: Gallery UUID aus den Einstellungen auslesen und setzen
		return pictureDaoLocal.findAll("beb3351d-9303-43d3-8c91-62e892199227");
	}

}// Ende class
