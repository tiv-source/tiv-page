package de.tivsource.page.admin.actions.others.subsumption;

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
import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.subsumption.Subsumption;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="subsumptionEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/edit_form.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 7108534700587832664L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="ContentItemDao")
    private ContentItemDaoLocal contentItemDaoLocal;

    @InjectEJB(name="SubsumptionDao")
    private SubsumptionDaoLocal subsumptionDaoLocal;

    private Subsumption subsumption;

    private String lang = "DE";
    
    private List<CSSGroup> cssGroupList;

    private List<ContentItem> contentItems;

    @StrutsParameter(depth=3)
    public Subsumption getSubsumption() {
        return subsumption;
    }

	public void setSubsumption(Subsumption subsumption) {
        this.subsumption = subsumption;
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
        				@Result(name = "input",   type = "tiles", location = "subsumptionEditForm"),
        				@Result(name = "error",   type = "tiles", location = "subsumptionEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(subsumption != null) {
    		LOGGER.info(subsumption.getTechnical());
    		Subsumption dbSubsumption = subsumptionDaoLocal.findByUuid(subsumption.getUuid());
    		contentItems = contentItemDaoLocal.findAllVisible(dbSubsumption.getUuid(), 0, contentItemDaoLocal.countAllVisible(dbSubsumption.getUuid()));
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                subsumption.getContentMap().put(Language.DE, dbSubsumption.getContentObject(Language.DE));
                dbSubsumption.getContentMap().get(Language.EN).setContent(subsumption.getContent(Language.EN));
                dbSubsumption.getContentMap().get(Language.EN).setModified(new Date());

                subsumption.getDescriptionMap().put(Language.DE, dbSubsumption.getDescriptionObject(Language.DE));
                String noLineBreaks = subsumption.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbSubsumption.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbSubsumption.getDescriptionMap().get(Language.EN).setKeywords(subsumption.getKeywords(Language.EN));
                dbSubsumption.getDescriptionMap().get(Language.EN).setName(subsumption.getName(Language.EN));
            } else {
                dbSubsumption.getContentMap().get(Language.DE).setContent(subsumption.getContent(Language.DE));
                dbSubsumption.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = subsumption.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbSubsumption.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbSubsumption.getDescriptionMap().get(Language.DE).setKeywords(subsumption.getKeywords(Language.DE));;
                dbSubsumption.getDescriptionMap().get(Language.DE).setName(subsumption.getName(Language.DE));
            }


    		dbSubsumption.setModified(new Date());
    		dbSubsumption.setTechnical(subsumption.getTechnical());
    		dbSubsumption.setVisible(subsumption.getVisible());
    		dbSubsumption.setModifiedBy(remoteUser);
    		dbSubsumption.setModifiedAddress(remoteAddress);
    		dbSubsumption.setPictureOnPage(subsumption.getPictureOnPage());
    		dbSubsumption.setCssGroup(subsumption.getCssGroup());

    		LOGGER.info("Anzahl der ContentItems: " + subsumption.getContentItems().size());
    		dbSubsumption.setContentItems(subsumption.getContentItems());

    		dbSubsumption.setShowDates(subsumption.getShowDates());
    		dbSubsumption.setShowDescriptions(subsumption.getShowDescriptions());
    		dbSubsumption.setShowPictures(subsumption.getShowPictures());
    		dbSubsumption.setShowTitles(subsumption.getShowTitles());
    		dbSubsumption.setOrderDates(subsumption.getOrderDates());
    		

    		subsumptionDaoLocal.merge(dbSubsumption);
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
    }

    /**
     * @return the contentItems
     */
    public List<ContentItem> getContentItems() {
        return contentItems;
    }

}// Ende class
