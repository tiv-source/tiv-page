package de.tivsource.page.admin.actions.academy.registration;

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
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
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
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/pay_form.jsp")
  }),
  @TilesDefinition(name="registrationDeclareForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/declare_form.jsp")
  }),
  @TilesDefinition(name="registrationPredeclareForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/predeclare_form.jsp")
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

	@InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

	private Registration registration;

	private String uncheckedRegistration;
	
    public Registration getRegistration() {
        return registration;
    }

    @StrutsParameter
    public void setUncheckedRegistration(String uncheckedRegistration) {
        this.uncheckedRegistration = uncheckedRegistration;
    }

    @Override
    public void prepare() {
        super.prepare();
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
                results = { @Result(name = "success", type="tiles", location = "registrationPredeclareForm") }
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
		} else {
			registration = new Registration();
		}

	}// Ende loadPageParameter()

}// Ende class
