package de.tivsource.page.admin.actions.others.companiongroup;

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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.companion.CompanionGroup;
import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="companionGroupEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companiongroup/edit_form.jsp")
  }),
  @TilesDefinition(name="companionGroupEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/companiongroup/edit_error.jsp")
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

    @InjectEJB(name="CompanionGroupDao")
    private CompanionGroupDaoLocal companionGroupDaoLocal;

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private CompanionGroup companionGroup;

    private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public CompanionGroup getCompanionGroup() {
        return companionGroup;
    }

	public void setCompanionGroup(CompanionGroup companionGroup) {
        this.companionGroup = companionGroup;
    }
	
    public String getLang() {
        return lang;
    }

    @StrutsParameter
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
            value = "edit",
            results = {
                @Result(name = "success", type = "redirectAction", location = "index.html"),
                @Result(name = "input",   type = "tiles", location = "companionGroupEditForm"),
                @Result(name = "error",   type = "tiles", location = "companionGroupEditError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(companionGroup != null) {
    		LOGGER.info("News UUID: " + companionGroup.getUuid());
    		CompanionGroup dbCompanionGroup = companionGroupDaoLocal.findByUuid(companionGroup.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                companionGroup.getDescriptionMap().put(Language.DE, dbCompanionGroup.getDescriptionObject(Language.DE));
                String noLineBreaks = companionGroup.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbCompanionGroup.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbCompanionGroup.getDescriptionMap().get(Language.EN).setKeywords(companionGroup.getKeywords(Language.EN));
                dbCompanionGroup.getDescriptionMap().get(Language.EN).setName(companionGroup.getName(Language.EN));
            } else {
                String noLineBreaks = companionGroup.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbCompanionGroup.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbCompanionGroup.getDescriptionMap().get(Language.DE).setKeywords(companionGroup.getKeywords(Language.DE));;
                dbCompanionGroup.getDescriptionMap().get(Language.DE).setName(companionGroup.getName(Language.DE));
            }


            dbCompanionGroup.setOrderNumber(companionGroup.getOrderNumber());
            dbCompanionGroup.setTechnical(companionGroup.getTechnical());
    		dbCompanionGroup.setModified(new Date());
    		dbCompanionGroup.setVisible(companionGroup.getVisible());
    		dbCompanionGroup.setModifiedBy(remoteUser);
    		dbCompanionGroup.setModifiedAddress(remoteAddress);
    		dbCompanionGroup.setPictureOnPage(companionGroup.getPictureOnPage());
    		dbCompanionGroup.setCssGroup(companionGroup.getCssGroup());


    		companionGroupDaoLocal.merge(dbCompanionGroup);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
