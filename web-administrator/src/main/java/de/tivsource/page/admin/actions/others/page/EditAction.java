package de.tivsource.page.admin.actions.others.page;

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
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.page.Page;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pageEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/edit_form.jsp")
  }),
  @TilesDefinition(name="pageEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7907625857484921700L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Page page;

    private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Page getPage() {
        return page;
    }

	public void setPage(Page page) {
        this.page = page;
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
        				@Result(name = "input",   type = "tiles", location = "pageEditForm"),
        				@Result(name = "error",   type = "tiles", location = "pageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(page != null) {
    		LOGGER.info(page.getTechnical());
    		Page dbPage = pageDaoLocal.findByUuid(page.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                page.getContentMap().put(Language.DE, dbPage.getContentObject(Language.DE));
                dbPage.getContentMap().get(Language.EN).setContent(page.getContent(Language.EN));
                dbPage.getContentMap().get(Language.EN).setModified(new Date());

                page.getDescriptionMap().put(Language.DE, dbPage.getDescriptionObject(Language.DE));
                String noLineBreaks = page.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbPage.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbPage.getDescriptionMap().get(Language.EN).setKeywords(page.getKeywords(Language.EN));
                dbPage.getDescriptionMap().get(Language.EN).setName(page.getName(Language.EN));
            } else {
                dbPage.getContentMap().get(Language.DE).setContent(page.getContent(Language.DE));
                dbPage.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = page.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbPage.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbPage.getDescriptionMap().get(Language.DE).setKeywords(page.getKeywords(Language.DE));;
                dbPage.getDescriptionMap().get(Language.DE).setName(page.getName(Language.DE));
            }


    		dbPage.setModified(new Date());
    		dbPage.setSpecial(page.getSpecial());
    		dbPage.setTechnical(page.getTechnical());
    		dbPage.setVisible(page.getVisible());
    		dbPage.setModifiedBy(remoteUser);
    		dbPage.setModifiedAddress(remoteAddress);
    		dbPage.setPictureOnPage(page.getPictureOnPage());
    		dbPage.setCssGroup(page.getCssGroup());
    		dbPage.setOrderNumber(page.getOrderNumber());

    		pageDaoLocal.merge(dbPage);
            return SUCCESS;
    	}
   		return ERROR;
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }

}// Ende class
