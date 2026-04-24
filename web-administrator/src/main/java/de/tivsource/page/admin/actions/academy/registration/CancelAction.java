package de.tivsource.page.admin.actions.academy.registration;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.registration.Registration;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="registrationCancelError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/cancel_error.jsp")
  }),
  @TilesDefinition(name="registrationCancelForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/cancel_form.jsp")
  })
})
public class CancelAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 1971590195827863725L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CancelAction.class);

    @InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

    private Registration registration;

    private String redirection;

    @StrutsParameter(depth=3)
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getRedirection() {
        return redirection;
    }

    @Override
    @Actions({
        @Action(
            value = "cancel",
            results = {
                @Result(name = "success",     type = "redirectAction", location = "%{redirection}"),
                @Result(name = "input",       type="tiles", location = "registrationCancelForm"),
                @Result(name = "error",       type="tiles", location = "registrationCancelError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(registration != null) {
    	    Registration dbRegistration = registrationDaoLocal.findByUuid(registration.getUuid());

    	    dbRegistration.setModified(new Date());
            dbRegistration.setModifiedBy(remoteUser);
            dbRegistration.setModifiedAddress(remoteAddress);

            if(dbRegistration.getStatus() == RegistrationStatus.PREDECLARED) {
                dbRegistration.setStatus(RegistrationStatus.CANCELLED);
                registrationDaoLocal.merge(dbRegistration);
                // Zurück zu den zu bestätigenden Anmeldungen
                redirection = "complete.html";

                // TODO: Hier mus eine Bestätigungsmail für die Stornierung erstellt werden
                // Es wird noch keine Gutschrift benötigt

            }// Ende if()

    	    if(dbRegistration.getStatus() == RegistrationStatus.DECLARED) {
    	        dbRegistration.setStatus(RegistrationStatus.CANCELLED);
    	        registrationDaoLocal.merge(dbRegistration);
    	        // Zurück zu den zu bestätigenden Anmeldungen
    	        redirection = "index.html";

    	        // TODO: Hier mus eine Bestätigungsmail für die Stornierung erstellt werden
    	        // Es wird noch keine Gutschrift benötigt

    	    }// Ende if()

            if(dbRegistration.getStatus() == RegistrationStatus.CONFIRMED) {
                dbRegistration.setStatus(RegistrationStatus.CANCELLED);
                registrationDaoLocal.merge(dbRegistration);

                // Zurück zu den zu bestätigenden Anmeldungen
                redirection = "confirmed.html";

                // TODO: Hier mus eine Bestätigungsmail für die Stornierung erstellt werden
                // Es wird noch keine Gutschrift benötigt

            }// Ende if()

            if(dbRegistration.getStatus() == RegistrationStatus.PAID) {
                dbRegistration.setStatus(RegistrationStatus.CANCELLED);
                registrationDaoLocal.merge(dbRegistration);

                // Zurück zu den zu bestätigenden Anmeldungen
                redirection = "paid.html";

                // TODO: Hier mus eine Bestätigungsmail für die Stornierung erstellt werden
                // Es wird eine Gutschrift benötigt.
            }

            if(dbRegistration.getStatus() == RegistrationStatus.WAITING) {
                dbRegistration.setStatus(RegistrationStatus.CANCELLED);
                registrationDaoLocal.merge (dbRegistration);

                // Zurück zu den zu bestätigenden Anmeldungen
                redirection = "waiting.html";

                // TODO: Hier mus eine Bestätigungsmail für die Stornierung erstellt werden
                // Es wird noch keine Gutschrift benötigt
            }
            
    	    return SUCCESS;
    	}
   		return ERROR;
    	
    }// Ende execute()
	
}// Ende class
