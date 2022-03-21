package de.tivsource.page.admin.actions.others.exhibition.image;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.exhibition.Exhibition;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.entity.property.Property;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="exhibitionImage", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta", value = "/WEB-INF/tiles/active/meta/default_jquery.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content", value = "/WEB-INF/tiles/active/view/exhibition/image.jsp")
  })
})
public class EditAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 2331059756873703652L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name = "ExhibitionDao")
    private ExhibitionDaoLocal exhibitionDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Exhibition exhibition;

    private String uuid;

    private PictureItemImage exhibitionImage;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the pageImage
     */
    public PictureItemImage getExhibitionImage() {
        return exhibitionImage;
    }

    /**
     * @param exhibitionImage the articleImage to set
     */
    public void setExhibitionImage(PictureItemImage exhibitionImage) {
        this.exhibitionImage = exhibitionImage;
    }

    @Override
    @Actions({
        @Action(
            value = "addImage",
            results = {
                    @Result(name = "success", type = "redirectAction", params = {"namespace", "/others/exhibition/image", "exhibition", "${uuid}", "reload", "true", "actionName", "index"}),
                    @Result(name = "input", type="tiles", location = "exhibitionImage"),
                    @Result(name = "error", type="tiles", location = "imageEditError")
            },
            interceptorRefs=@InterceptorRef("defaultImageStack")
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        
        exhibition = exhibitionDaoLocal.findByUuid(uuid);
        
        if(exhibition != null) {
            // Bild löschen
            exhibition.getImage().delete();

            exhibition.getImage().setUploadFile(exhibitionImage.getUploadFile());
            exhibition.getImage().generate();
            exhibition.getImage().setStandard(true);

            String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

            exhibition.setModified(new Date());
            exhibition.setModifiedAddress(remoteAddress);
            exhibition.setModifiedBy(remoteUser);

            exhibitionDaoLocal.merge(exhibition);

            // Aktualisiere den Zeitstemple "lastModified"
            setLastModified();

            return SUCCESS;
        }
        // Gebe einen Fehler zurück
        return ERROR;
    }// Ende execute()

    private void setLastModified() {
        // Lese Daten des Remote Benutzers
        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        // Erstelle neues Aktualisierungsdatum
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String newDate = simpleDateFormat.format(new Date());

        // Hole Schlüssel mit Aktualisierungsdatum aus der Datenbank oder erstelle neues Objekt
        Property lastModified = propertyDaoLocal.findByKey("pictureItem.image.lastModified");

        if(lastModified != null) {
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        } else {
            lastModified = new Property();
            lastModified.setCreated(new Date());
            lastModified.setKey("pictureItem.image.lastModified");
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        }
        propertyDaoLocal.merge(lastModified);
    }

}// Ende class
