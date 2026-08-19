package de.tivsource.page.admin.actions.academy.registration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.common.simple.SimpleBooleanType;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.registration.Registration;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="registrationEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/edit_form.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 2709168621141239818L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;

    @InjectEJB(name="CountryDao")
    private CountryDaoLocal countryDaoLocal;

    @InjectEJB(name="CourseDao")
    private CourseDaoLocal courseDaoLocal;

    @InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

    private List<Salutation> salutations;

    private List<Country> countryList;

    private List<Course> courseList;

    private List<SimpleBooleanType> sbtList;

    private Registration registration;

    public List<Salutation> getSalutations() {
        return salutations;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public List<SimpleBooleanType> getSbtList() {
        return sbtList;
    }

    @StrutsParameter(depth=3)
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public RegistrationStatus[] getStatusList() {
        return RegistrationStatus.values();
    }

    @Override
    public void prepare() {
        super.prepare();
        LOGGER.info("prepare() aufgerufen.");
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
        countryList = countryDaoLocal.findAll(0, countryDaoLocal.countAll());
        courseList = courseDaoLocal.findAll(0, courseDaoLocal.countAll());
        sbtList = new ArrayList<SimpleBooleanType>();
        sbtList.add(createSimpleBooleanType("Privat", true));
        sbtList.add(createSimpleBooleanType("Geschäftlich", false));
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "complete.html"),
        				@Result(name = "input",   type = "tiles", location = "courseEditForm"),
        				@Result(name = "error",   type = "tiles", location = "courseEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
    	
    	if(registration != null) {
    		LOGGER.info("UUID der Anmeldung: " + registration.getUuid());
    		Registration dbRegistration = registrationDaoLocal.findByUuid(registration.getUuid());

    		dbRegistration.setBirthday(registration.getBirthday());
    		//dbRegistration.setChannel(registration.getChannel());
    		dbRegistration.setCity(registration.getCity());
    		dbRegistration.setConsumer(registration.getConsumer());
    		dbRegistration.setCountry(registration.getCountry());
    		dbRegistration.setCourse(registration.getCourse());
    		//dbRegistration.setCreated(registration.getCreated());
    		dbRegistration.setEmail(registration.getEmail());
    		dbRegistration.setFirm(registration.getFirm());
    		dbRegistration.setFirstname(registration.getFirstname());
    		//dbRegistration.setInvoiceNumber(registration.getInvoiceNumber());
    		dbRegistration.setLastname(registration.getLastname());
    		dbRegistration.setListOfMaterials(registration.getListOfMaterials());
    		dbRegistration.setModified(new Date());
    		dbRegistration.setModifiedAddress(remoteAddress);
    		dbRegistration.setModifiedBy(remoteUser);
    		//dbRegistration.setOrderConfirmationNumber(registration.getOrderConfirmationNumber());
    		//dbRegistration.setPrivacy(registration.getPrivacy());
    		dbRegistration.setSalutation(registration.getSalutation());
    		dbRegistration.setStatus(registration.getStatus());
    		dbRegistration.setStreet(registration.getStreet());
    		dbRegistration.setTelephone(registration.getTelephone());
    		//dbRegistration.setTerms(registration.getTerms());
    		//dbRegistration.setUserToken(registration.getUserToken());
    		//dbRegistration.setUuid(registration.getUuid());
    		//dbRegistration.setVersion(registration.getVersion());
    		dbRegistration.setZip(registration.getZip());

    		registrationDaoLocal.merge(dbRegistration);

            return SUCCESS;
    	}
    	// Wenn kein Kurs-Objekt vorhanden ist
    	return ERROR;
    }// Ende execute()

    private SimpleBooleanType createSimpleBooleanType(String name, Boolean value) {
        SimpleBooleanType simpleBooleanType = new SimpleBooleanType();
        simpleBooleanType.setName(name);
        simpleBooleanType.setValue(value);
        return simpleBooleanType;
    }// Ende createSimpleBooleanType()

}// Ende class
