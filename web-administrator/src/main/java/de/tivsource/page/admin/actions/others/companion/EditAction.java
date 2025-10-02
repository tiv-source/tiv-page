package de.tivsource.page.admin.actions.others.companion;

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
import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.entity.companion.Companion;
import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="companionAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companion/edit_form.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -8055397581589809541L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="CompanionDao")
    private CompanionDaoLocal companionDaoLocal;

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    private Companion companion;

    @StrutsParameter(depth=2)
    public Companion getCompanion() {
        return companion;
    }

	public void setCompanion(Companion companion) {
        this.companion = companion;
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "companionEditForm"),
        				@Result(name = "error",   type = "tiles", location = "companionEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companion != null) {
    		LOGGER.info("Companion UUID: " + companion.getUuid());
    		Companion dbCompanion = companionDaoLocal.findByUuid(companion.getUuid());
    		
    		dbCompanion.setAddress(companion.getAddress());
    		dbCompanion.setAppendix(companion.getAppendix());
    		dbCompanion.setContactDetails(companion.getContactDetails());
    		dbCompanion.setGroup(companion.getGroup());
    		dbCompanion.setModified(new Date());
    		dbCompanion.setModifiedAddress(remoteAddress);
    		dbCompanion.setModifiedBy(remoteUser);
    		dbCompanion.setName(companion.getName());
    		dbCompanion.setVisible(companion.getVisible());

    		companionDaoLocal.merge(dbCompanion);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<CompanionGroup> getCompanionGroupList() {
        return companionGroupDaoLocal.findAll(0, companionGroupDaoLocal.countAll());
    }

}// Ende class
