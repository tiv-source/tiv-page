package de.tivsource.page.user.actions.registration;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.enumeration.OrderChannel;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.registration.Registration;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="registrationConfirmForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/registration_form.jsp"),
    @TilesPutAttribute(name = "opengraph",  value = "/WEB-INF/tiles/active/opengraph/registration.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/confirm_form.jsp")
  })
})
public class ConfirmAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6339779084470001441L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ConfirmAction.class);

    @InjectEJB(name = "PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;

    @InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    /**
     * Kann durch den User geändert werden. Sollte 
     * deshalb vor der Verwedung überprüft werden.
     */
    private String uncheckedRegistration;

    private Registration dbRegistration;

    private Course course;
    
    private Registration registration;

    private Page page;

    private List<Salutation> salutations;

    private List<Country> countryList;

    @Override
    public void prepare() {
        page = pageDaoLocal.findByTechnical("waitingform");
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
        countryList = countryDaoLocal.findAll(0, countryDaoLocal.countAll());
        uncheckedRegistration = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("Registration Uuid: " + uncheckedRegistration);
        uncheckedRegistration = uncheckedRegistration.replaceAll("/confirm.html", "");
        uncheckedRegistration = uncheckedRegistration.replaceAll("/registration/", "");
        LOGGER.info("Registration Uuid: " + uncheckedRegistration);
    }// Ende prepare()

    @Override
    @Actions({
        @Action(value = "*/confirm", results = {
            @Result(name = "input", type = "tiles", location = "registrationConfirmForm"),
            @Result(name = "success", type = "redirectAction", location = "%{course.uuid}/registrationsuccess.html"),
            @Result(name = "waiting", type = "redirectAction", location = "%{course.uuid}/waitingsuccess.html"),
            @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        // Hole Action Locale
        this.getLanguageFromActionContext();

        if( uncheckedRegistration != null && uncheckedRegistration != "" && uncheckedRegistration.length() > 0) {
            if( isValid(uncheckedRegistration) ) {
                dbRegistration = registrationDaoLocal.findUserToken(uncheckedRegistration);
                if( dbRegistration.getStatus() == RegistrationStatus.PREDECLARED ) {

                    // Setze den Kurs für die Bestätigungsseite
                    course = dbRegistration.getCourse();
                    
                    dbRegistration.setStatus(RegistrationStatus.DECLARED);
                    dbRegistration.setChannel(OrderChannel.WEBSITE);
                    dbRegistration.setModified(new Date());
                    dbRegistration.setModifiedAddress(remoteAddress);
                    dbRegistration.setModifiedBy("Kunden");

                    registrationDaoLocal.merge(dbRegistration);
                    return SUCCESS;
                }
                LOGGER.info("Registrierung kann nicht konvertiert werden (Nicht PreDeclared).");
            }// Ende if()
        }// Ende if()

        /*
         * Wenn es die Seite nicht gibt oder es einen Manipulationsversuch
         * gab.
         */
        LOGGER.info("Die Methode execute() wurde mit einem Fehler beendet. (Manipulationsversuch)");
        return ERROR;
    }// Ende execute()

    @StrutsParameter(depth=1)
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public Course getCourse() {
        return course;
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
