package de.tivsource.page.admin.actions.academy.registration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  @TilesDefinition(name="registrationAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/add_form.jsp")
  }),
  @TilesDefinition(name="registrationEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/edit_form.jsp")
  }),
  @TilesDefinition(name="registrationConfirmForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/confirm_form.jsp")
  }),
  @TilesDefinition(name="registrationPayForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/pay_form.jsp")
  }),
  @TilesDefinition(name="registrationDeclareForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/declare_form.jsp")
  }),
  @TilesDefinition(name="registrationPreDeclareForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/predeclare_form.jsp")
  }),
  @TilesDefinition(name="registrationRevokeForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/revoke_form.jsp")
  }),
  @TilesDefinition(name="registrationCancelForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/cancel_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2411428600503309764L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

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

	private String uncheckedRegistration;

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

    public Registration getRegistration() {
        return registration;
    }

    public RegistrationStatus[] getStatusList() {
        return RegistrationStatus.values();
    }

    @StrutsParameter
    public void setUncheckedRegistration(String uncheckedRegistration) {
        this.uncheckedRegistration = uncheckedRegistration;
    }

    @Override
    public void prepare() {
        super.prepare();
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
        countryList = countryDaoLocal.findAll(0, countryDaoLocal.countAll());
        courseList = courseDaoLocal.findAll(0, courseDaoLocal.countAll());
        sbtList = new ArrayList<SimpleBooleanType>();
        sbtList.add(createSimpleBooleanType("Privat", true));
        sbtList.add(createSimpleBooleanType("Geschäftlich", false));
        registration = new Registration();
    }

    @Override
    @Actions({
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "registrationAddForm") }
        ),
        @Action(
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "registrationEditForm") }
        ),
        @Action(
        		value = "confirmForm", 
        		results = { @Result(name = "success", type="tiles", location = "registrationConfirmForm") }
        ),
        @Action(
                value = "payForm", 
                results = { @Result(name = "success", type="tiles", location = "registrationPayForm") }
        ),
        @Action(
                value = "declareForm", 
                results = { @Result(name = "success", type="tiles", location = "registrationDeclareForm") }
        ),
        @Action(
                value = "predeclareForm", 
                results = { @Result(name = "success", type="tiles", location = "registrationPreDeclareForm") }
        ),
        @Action(
                value = "revokeForm", 
                results = { @Result(name = "success", type="tiles", location = "registrationRevokeForm") }
        ),
        @Action(
                value = "cancelForm", 
                results = { @Result(name = "success", type="tiles", location = "registrationCancelForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {

		if( uncheckedRegistration != null && uncheckedRegistration != "" && uncheckedRegistration.length() > 0) {
			registration = registrationDaoLocal.findByUuid(uncheckedRegistration);
		}

	}// Ende loadPageParameter()

    private SimpleBooleanType createSimpleBooleanType(String name, Boolean value) {
        SimpleBooleanType simpleBooleanType = new SimpleBooleanType();
        simpleBooleanType.setName(name);
        simpleBooleanType.setValue(value);
        return simpleBooleanType;
    }// Ende createSimpleBooleanType()

}// Ende class
