package de.tivsource.page.admin.actions.others.manual;

import java.util.Date;
import java.util.List;

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
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="manualEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/edit_form.jsp")
  }),
  @TilesDefinition(name="manualEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/manual/edit_error.jsp")
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

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    @InjectEJB(name="PictureDao")
    private PictureDaoLocal pictureDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Manual manual;

    private String lang = "DE";

    private List<Picture> pictureList;

    private List<CSSGroup> cssGroupList;

    public Manual getManual() {
        return manual;
    }

	public void setManual(Manual manual) {
        this.manual = manual;
    }
	
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
        pictureList = pictureDaoLocal.findAll(propertyDaoLocal.findByKey("gallery.uuid.for.manual.picture").getValue());
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "manualEditForm"),
        				@Result(name = "error",   type = "tiles", location = "manualEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(manual != null) {
    		LOGGER.info("News UUID: " + manual.getUuid());
    		Manual dbManual = manualDaoLocal.findByUuid(manual.getUuid());
    		

            if(lang.contentEquals(new StringBuffer("EN"))) {
                manual.getContentMap().put(Language.DE, dbManual.getContentObject(Language.DE));
                dbManual.getContentMap().get(Language.EN).setContent(manual.getContent(Language.EN));
                dbManual.getContentMap().get(Language.EN).setModified(new Date());

                manual.getDescriptionMap().put(Language.DE, dbManual.getDescriptionObject(Language.DE));
                String noLineBreaks = manual.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbManual.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbManual.getDescriptionMap().get(Language.EN).setKeywords(manual.getKeywords(Language.EN));
                dbManual.getDescriptionMap().get(Language.EN).setName(manual.getName(Language.EN));
            } else {
                dbManual.getContentMap().get(Language.DE).setContent(manual.getContent(Language.DE));
                dbManual.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = manual.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbManual.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbManual.getDescriptionMap().get(Language.DE).setKeywords(manual.getKeywords(Language.DE));;
                dbManual.getDescriptionMap().get(Language.DE).setName(manual.getName(Language.DE));
            }

            dbManual.setTechnical(manual.getTechnical());
    		dbManual.setModified(new Date());
    		dbManual.setVisible(manual.getVisible());
    		dbManual.setModifiedBy(remoteUser);
    		dbManual.setModifiedAddress(remoteAddress);
    		dbManual.setPicture(manual.getPicture());
    		dbManual.setPictureOnPage(manual.getPictureOnPage());
    		dbManual.setOrderNumber(manual.getOrderNumber());
    		dbManual.setCssGroup(manual.getCssGroup());


    		manualDaoLocal.merge(dbManual);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

	public List<Picture> getPictureList() {
	    return pictureList;
	}

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }

}// Ende class
