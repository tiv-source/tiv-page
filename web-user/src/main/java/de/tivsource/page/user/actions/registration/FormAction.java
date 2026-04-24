package de.tivsource.page.user.actions.registration;

import java.util.List;
import java.util.regex.Pattern;

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
import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.registration.Registration;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="registrationConfirmForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/registration_form.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/registration.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/confirm_form.jsp")
  }),
  @TilesDefinition(name="registrationCancelForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/registration_form.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/registration.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/cancel_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 6091688929002572335L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

	@InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

	private Registration registration;

	private String uncheckedRegistration;

	private Page page;

    private List<Salutation> salutations;

    private List<Country> countryList;

    public Registration getRegistration() {
        return registration;
    }

    @Override
    public void prepare() {
        super.prepare();
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
        countryList = countryDaoLocal.findAll(0, countryDaoLocal.countAll());
        uncheckedRegistration = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Registration Uuid: " + uncheckedRegistration);
        uncheckedRegistration = uncheckedRegistration.replaceAll("/confirmForm.html", "");
        uncheckedRegistration = uncheckedRegistration.replaceAll("/cancelForm.html", "");
        uncheckedRegistration = uncheckedRegistration.replaceAll("/registration/", "");
        LOGGER.info("Registration Uuid: " + uncheckedRegistration);
    }

    @Override
    @Actions({
        @Action(
        		value = "*/confirmForm", 
        		results = {
        		    @Result(name = "success", type="tiles", location = "registrationConfirmForm"),
        	        @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        ),
        @Action(
                value = "*/cancelForm", 
                results = { 
                    @Result(name = "success", type="tiles", location = "registrationCancelForm"),
                    @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
                }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        if( uncheckedRegistration != null && uncheckedRegistration != "" && uncheckedRegistration.length() > 0) {
            if( isValid(uncheckedRegistration) ) {
                registration = registrationDaoLocal.findUserToken(uncheckedRegistration);

                //8ec5080a-a325-4104-ba3b-6972f17d8e43/confirmForm
                String actionName = getActionName().substring(37);
                LOGGER.debug("ActionName: " + actionName);

                // Wenn es das Fomular zur Bestätigung ist
                // wird dieser Block ausgeführt
                if(actionName.equals("confirmForm")) {
                    if( registration != null && registration.getStatus() == RegistrationStatus.PREDECLARED ) {
                        page = pageDaoLocal.findByTechnical("confirmform");
                        return SUCCESS;
                    }
                    LOGGER.info("Registrierung kann nicht konvertiert werden (Nicht PreDeclared oder NULL).");
                }

                // Wenn es das Fomular für die Stronierung ist
                // wird dieser Block ausgeführt
                if(actionName.equals("cancelForm")) {
                    if( registration != null && registration.getStatus() == RegistrationStatus.PREDECLARED ) {
                        page = pageDaoLocal.findByTechnical("cancelform");
                        return SUCCESS;
                    }
                    LOGGER.info("Registrierung kann nicht konvertiert werden (Nicht PreDeclared oder NULL).");
                }
            }// Ende if()
        }// Ende if()

    	return ERROR;
    }// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }

    public List<Salutation> getSalutations() {
        return salutations;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    private Boolean isValid(String input) {
        Pattern UUID_REGEX = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
        if (UUID_REGEX.matcher(input).matches()) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

}// Ende class
