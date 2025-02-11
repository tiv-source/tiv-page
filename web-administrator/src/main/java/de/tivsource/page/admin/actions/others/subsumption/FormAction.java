package de.tivsource.page.admin.actions.others.subsumption;

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
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.subsumption.Subsumption;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="subsumptionAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/add_form.jsp")
  }),
  @TilesDefinition(name="subsumptionEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/edit_form.jsp")
  }),
  @TilesDefinition(name="subsumptionDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/delete_form.jsp")
  }),
  @TilesDefinition(name="imageForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/subsumption/image_form.jsp")
  })
})
public class FormAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -2984651595691911759L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="ContentItemDao")
    private ContentItemDaoLocal contentItemDaoLocal;

	@InjectEJB(name="SubsumptionDao")
    private SubsumptionDaoLocal subsumptionDaoLocal;

	private Subsumption subsumption;

	private String uncheckSubsumption;

	private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    private List<ContentItem> contentItems;
    
	public Subsumption getSubsumption() {
        return subsumption;
    }

	@StrutsParameter
	public void setUncheckSubsumption(String uncheckSubsumption) {
        this.uncheckSubsumption = uncheckSubsumption;
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
        		value = "editForm", 
        		results = { @Result(name = "success", type="tiles", location = "subsumptionEditForm") }
        ),
        @Action(
        		value = "addForm", 
        		results = { @Result(name = "success", type="tiles", location = "subsumptionAddForm") }
        ),
        @Action(
        		value = "deleteForm", 
        		results = { @Result(name = "success", type="tiles", location = "subsumptionDeleteForm") }
        ),
        @Action(
                value = "imageForm", 
                results = { @Result(name = "success", type="tiles", location = "imageForm") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");
    	
    	this.loadPageParameter();
    	return SUCCESS;
    }// Ende execute()

	private void loadPageParameter() {

		if( uncheckSubsumption != null && uncheckSubsumption != "" && uncheckSubsumption.length() > 0) {
		    contentItems = contentItemDaoLocal.findAllVisible(uncheckSubsumption, 0, contentItemDaoLocal.countAllVisible(uncheckSubsumption));
			subsumption = subsumptionDaoLocal.findByUuid(uncheckSubsumption);
		} else {
			subsumption = new Subsumption();
		}

	}// Ende loadPageParameter()

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
