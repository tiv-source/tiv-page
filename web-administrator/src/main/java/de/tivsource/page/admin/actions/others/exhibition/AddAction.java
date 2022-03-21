package de.tivsource.page.admin.actions.others.exhibition;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.entity.enumeration.CityType;
import de.tivsource.page.entity.enumeration.CountryType;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.exhibition.Exhibition;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.pictureitem.PictureItemImage;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="exhibitionAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/add_form.jsp")
  }),
  @TilesDefinition(name="exhibitionAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/exhibition/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3519856140395087415L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name = "ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    private Exhibition exhibition;

    private PictureItemImage exhibitionImage;

    private List<CSSGroup> cssGroupList;

    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

    /**
     * @return the exhibitionImage
     */
    public PictureItemImage getExhibitionImage() {
        return exhibitionImage;
    }

    /**
     * @param exhibitionImage - the exhibitionImage to set
     */
    public void setExhibitionImage(PictureItemImage exhibitionImage) {
        this.exhibitionImage = exhibitionImage;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

    @Override
    @Actions({ 
        @Action(value = "add", results = {
            @Result(name = "success", type = "redirectAction", location = "index.html"),
            @Result(name = "input", type = "tiles", location = "exhibitionAddForm"),
            @Result(name = "error", type = "tiles", location = "exhibitionAddError") 
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        // Hole Action Locale
        this.getLanguageFromActionContext();

        if (exhibition != null) {
            LOGGER.info("Größe der DescriptionMap: "
                    + exhibition.getDescriptionMap().size());

            /*
             *  Erzeuge notwendige Initialisierung des 
             *  PictureItemImage Objektes.
             */
            if(exhibitionImage != null) {
                exhibitionImage.setPictureItem(exhibition);
                exhibitionImage.setUuid(UUID.randomUUID().toString());
                exhibitionImage.generate();
                exhibitionImage.setCreated(new Date());
                exhibitionImage.setModified(new Date());
                exhibitionImage.setModifiedAddress(remoteAddress);
                exhibitionImage.setModifiedBy(remoteUser);
                exhibition.setImage(exhibitionImage);
            }
            
            exhibition.setCreated(new Date());
            exhibition.setModified(new Date());

            Description pd = new Description();
            pd.setUuid(UUID.randomUUID().toString());
            pd.setLanguage(Language.DE);
            pd.setName(exhibition.getDescriptionMap().get(Language.DE).getName());
            String noLineBreaks = exhibition.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
            pd.setDescription(noLineBreaks);
            pd.setKeywords(exhibition.getDescriptionMap().get(Language.DE).getKeywords());
            pd.setNamingItem(exhibition);
            exhibition.getDescriptionMap().put(pd.getLanguage(), pd);

            Description pdEn = new Description();
            pdEn.setUuid(UUID.randomUUID().toString());
            pdEn.setLanguage(Language.EN);
            pdEn.setName(exhibition.getDescriptionMap().get(Language.DE).getName());
            pdEn.setDescription(noLineBreaks);
            pdEn.setKeywords(exhibition.getDescriptionMap().get(Language.DE).getKeywords());
            pdEn.setNamingItem(exhibition);
            exhibition.getDescriptionMap().put(pdEn.getLanguage(), pdEn);

            exhibition.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
            exhibition.getContentMap().get(Language.DE).setContentItem(exhibition);
            exhibition.getContentMap().get(Language.DE).setLanguage(Language.DE);
            exhibition.getContentMap().get(Language.DE).setCreated(new Date());
            exhibition.getContentMap().get(Language.DE).setModified(new Date());

            exhibition.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            exhibition.getContentMap().get(Language.EN).setContentItem(exhibition);
            exhibition.getContentMap().get(Language.EN).setLanguage(Language.EN);
            exhibition.getContentMap().get(Language.EN).setContent(exhibition.getContent(Language.DE));
            exhibition.getContentMap().get(Language.EN).setCreated(new Date());
            exhibition.getContentMap().get(Language.EN).setModified(new Date());
            
            exhibition.setUuid(UUID.randomUUID().toString());
            exhibition.setCreated(new Date());
            exhibition.setModified(new Date());
            exhibition.setModifiedAddress(remoteAddress);
            exhibition.setModifiedBy(remoteUser);

            exhibitionDaoLocal.merge(exhibition);

            return SUCCESS;
        } else {
            return ERROR;
        }

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
