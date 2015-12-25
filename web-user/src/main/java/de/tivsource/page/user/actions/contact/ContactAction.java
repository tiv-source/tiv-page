package de.tivsource.page.user.actions.contact;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class ContactAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8491900732546714341L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(ContactAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Page page;

    @Override
    @Actions({
        @Action(
        		value = "index", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "contactForm"),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	// Hole Action Locale
    	this.getLanguageFromActionContext();

    	this.page = pageDaoLocal.findByTechnical("contact");
    	
    	Boolean contactPageEnabled = propertyDaoLocal.findByKey("contact.page.enabled").getValue().equals("true") ? true : false;

    	if(contactPageEnabled) {
            LOGGER.info("Pfad zur temp.xml Klasse: " + this.getClass().getClassLoader().getResource("/template_mail.xml"));
            return SUCCESS;
    	} else {
    	    return ERROR;
    	}

    }// Ende execute()

    @Override
    public Page getPage() {
    	return this.page;
    }// Ende getPage()

}// Ende class