package de.tivsource.page.admin.actions.maintenance.restore;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.restore.RestoreZipFile;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="restore",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/maintenance.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/restore/restore.jsp")
  })
})
public class RestoreAction extends EmptyAction {

    /**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -4275472563540907020L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreAction.class);

    @Autowired
    @Qualifier("galleryDaoLocal")
    private GalleryDaoLocal galleryDaoLocal;

    @Autowired
    @Qualifier("pictureDaoLocal")
    private PictureDaoLocal pictureDaoLocal;

    @Autowired
    @Qualifier("userDaoLocal")
    private UserDaoLocal userDaoLocal;

    @Autowired
    @Qualifier("roleDaoLocal")
    private RoleDaoLocal roleDaoLocal;

    @Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

    @Autowired
    @Qualifier("locationDaoLocal")
    private LocationDaoLocal locationDaoLocal;

    @Autowired
    @Qualifier("eventDaoLocal")
    private EventDaoLocal eventDaoLocal;

    @Autowired
    @Qualifier("messageDaoLocal")
    private MessageDaoLocal messageDaoLocal;

    @Autowired
    @Qualifier("newsDaoLocal")
    private NewsDaoLocal newsDaoLocal;
    
    @Autowired
    @Qualifier("reservationDaoLocal")
    private ReservationDaoLocal reservationDaoLocal;

    @Autowired
    @Qualifier("vacancyDaoLocal")
    private VacancyDaoLocal vacancyDaoLocal;

    @Autowired
    @Qualifier("manualDaoLocal")
    private ManualDaoLocal manualDaoLocal;

    @Autowired
    @Qualifier("sliderDaoLocal")
    private SliderDaoLocal sliderDaoLocal;

    private File restoreFile;

    @Autowired
    public RestoreAction(PropertyService propertyService) {
        super(propertyService);
    }

    public void setRestoreFile(File restoreFile) {
        this.restoreFile = restoreFile;
    }

    @Override
    @Actions({
        @Action(
        		value = "restore", 
        		results = { @Result(name = "success", type="tiles", location = "restore") }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        RestoreZipFile restoreZipFile = new RestoreZipFile(
                galleryDaoLocal, pictureDaoLocal, userDaoLocal,
                roleDaoLocal, pageDaoLocal, propertyDaoLocal, locationDaoLocal,
                eventDaoLocal, messageDaoLocal, newsDaoLocal, reservationDaoLocal, 
                vacancyDaoLocal, manualDaoLocal, sliderDaoLocal
        );
    	restoreZipFile.restoreZip(restoreFile);

    	return SUCCESS;
    }// Ende execute()

}// Ende class
