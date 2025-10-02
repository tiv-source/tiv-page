package de.tivsource.page.admin.actions.others.exhibition;

import java.util.Arrays;
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
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.enumeration.CityType;
import de.tivsource.page.entity.enumeration.CountryType;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.exhibition.Exhibition;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="exhibitionEditForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/edit_form.jsp")
  }),
  @TilesDefinition(name="exhibitionEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -2265845145443104587L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    private Exhibition exhibition;

    private String lang = "DE";

    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
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
                @Result(name = "input", type = "tiles", location = "exhibitionEditForm"),
                @Result(name = "error", type = "tiles", location = "exhibitionEditError")
            }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        // Hole Action Locale
        this.getLanguageFromActionContext();

        if( exhibition.getUuid() != null && exhibition.getUuid() != "" && exhibition.getUuid().length() > 0) {
            LOGGER.info("UUID des Exhibtion Objektes: " + exhibition.getUuid());
            Exhibition dbExhibition = exhibitionDaoLocal.findByUuid(exhibition.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                exhibition.getContentMap().put(Language.DE, dbExhibition.getContentObject(Language.DE));
                dbExhibition.getContentMap().get(Language.EN).setContent(exhibition.getContent(Language.EN));
                dbExhibition.getContentMap().get(Language.EN).setModified(new Date());

                exhibition.getDescriptionMap().put(Language.DE, dbExhibition.getDescriptionObject(Language.DE));
                String noLineBreaks = exhibition.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbExhibition.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbExhibition.getDescriptionMap().get(Language.EN).setKeywords(exhibition.getKeywords(Language.EN));
                dbExhibition.getDescriptionMap().get(Language.EN).setName(exhibition.getName(Language.EN));
            } else {
                dbExhibition.getContentMap().get(Language.DE).setContent(exhibition.getContent(Language.DE));
                dbExhibition.getContentMap().get(Language.DE).setModified(new Date());

                String noLineBreaks = exhibition.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbExhibition.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbExhibition.getDescriptionMap().get(Language.DE).setKeywords(exhibition.getKeywords(Language.DE));;
                dbExhibition.getDescriptionMap().get(Language.DE).setName(exhibition.getName(Language.DE));
            }

            dbExhibition.setCountry(exhibition.getCountry());
            dbExhibition.setEnd(exhibition.getEnd());
            dbExhibition.setMoment(exhibition.getMoment());
            dbExhibition.setPlace(exhibition.getPlace());
            dbExhibition.setStart(exhibition.getStart());
            dbExhibition.setVisible(exhibition.getVisible());
            dbExhibition.setVisibleFrom(exhibition.getVisibleFrom());
            dbExhibition.setTechnical(exhibition.getTechnical());
            dbExhibition.setCssGroup(exhibition.getCssGroup());

            dbExhibition.setModified(new Date());
            dbExhibition.setModifiedAddress(remoteAddress);
            dbExhibition.setModifiedBy(remoteUser);
            
            dbExhibition.setPictureOnPage(exhibition.getPictureOnPage());
            dbExhibition.setOrderNumber(exhibition.getOrderNumber());
            

            exhibitionDaoLocal.merge(dbExhibition);
            return SUCCESS;
        }

        return ERROR;
    }// Ende execute()

    public List<CountryType> getCountryList() {
        return Arrays.asList(CountryType.values());
    }

    public List<CityType> getCityList() {
        return Arrays.asList(CityType.values());
    }

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }// Ende getCssGroupList()

}// Ende class
