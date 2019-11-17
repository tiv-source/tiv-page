package de.tivsource.page.user.actions.contact;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

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
@TilesDefinitions({
  @TilesDefinition(name="contactForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contact/contact_form.jsp")
  })
})
public class ContactAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8491900732546714341L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ContactAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Page page;

    @Override
    public void prepare() {
        // Lade die Kontaktseite aus der Datenbank
        this.page = pageDaoLocal.findByTechnical("contact");
    }

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

    	Boolean contactPageEnabled = propertyDaoLocal.findByKey("contact.page.enabled").getValue().equals("true") ? true : false;

    	if(contactPageEnabled) {
    	    URL templatePath = new URL(propertyDaoLocal.findByKey("mail.template.path").getValue());
            LOGGER.info("Pfad zur temp.xml Klasse: " + templatePath);
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