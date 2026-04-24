package de.tivsource.page.image.actions.osm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.location.image.OSMImage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 *
 * @author Marc Michele
 */
public class ImageAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 5138741235965879832L;

    /**
     * Generierte Versions-Kennung der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ImageAction.class);

    /**
     * Servlet-Request der die Post und Get Daten der Session enthält.
     */
    private HttpServletRequest servletRequest;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name = "LocationDao")
    private LocationDaoLocal locationDaoLocal;

    /**
     * Aktuelle Seite.
     */
    private Location location;

    private OSMImage osmImage;

    private boolean cache = true;

    public OSMImage getOsmImage() {
        return osmImage;
    }

    /**
     * @param cache the cache to set
     */
    @StrutsParameter
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    @Actions({
        @Action(
            value = "*/micro",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","osmImage.microFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        ),
        @Action(
            value = "*/thumbnail",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","osmImage.thumbnailFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        ),
        @Action(
            value = "*/small",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","osmImage.smallFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        ),
        @Action(
            value = "*/normal",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","osmImage.normalFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        ),
        @Action(
            value = "*/large",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","osmImage.largeFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        ),
        @Action(
            value = "*/original",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","osmImage.originalFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        )
    })
    public String execute() {
        LOGGER.trace("execute() aufgerufen.");

        // Lese lastModified aus der Datenbank e. G.: "Sun, 01 Jan 2017 00:00:00 GMT"
        // TODO: Hier muss noch ein Schlüssel für lastModified erstellt werden
        // vorrübergehend wird der Schlüssel von pictureItem mitbenutzt
        String lastModified = propertyDaoLocal.findByKey("pictureItem.image.lastModified").getValue();
        // Hole If-Modified-Since aus der Anfrage
        String since = servletRequest.getHeader("If-Modified-Since");
        LOGGER.info("If-Modified-Since: " + since);

        // Setze Fomatierung
        // "Thu, 01 Dec 1994 16:00:00 GMT"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.HOUR, 12);
        String expires = simpleDateFormat.format(gregorianCalendar.getTime());

        HttpServletResponse response = ServletActionContext.getResponse();

        if(cache) {
            response.setHeader("Expires", expires);
            response.setHeader("Last-Modified", lastModified);
            response.setHeader("Retry-After", expires);
        } else {
            response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");
        }

        if(cache && since!=null && since.equals(lastModified)) {
            response.setStatus(304);
            return INPUT;
        }

        String locationUuid = ServletActionContext.getRequest().getServletPath();

        LOGGER.info("Location UUID: " + locationUuid);
        locationUuid = locationUuid.replaceAll("/osm/", "");
        locationUuid = locationUuid.replaceAll("/micro.png", "");
        locationUuid = locationUuid.replaceAll("/thumbnail.png", "");
        locationUuid = locationUuid.replaceAll("/small.png", "");
        locationUuid = locationUuid.replaceAll("/normal.png", "");
        locationUuid = locationUuid.replaceAll("/large.png", "");
        locationUuid = locationUuid.replaceAll("/original.png", "");
        LOGGER.info("Location UUID: " + locationUuid);
        
        try {
            // TODO: Check UUID Eingabe auf nicht erlaubte Zeichen  
            this.location = this.locationDaoLocal.findByUuid(locationUuid);
            LOGGER.info("Location UUID aus Objekt: " + location.getUuid());
            osmImage = location.getOsmImage();
            LOGGER.info("OSMImage UUID aus Objekt: " + location.getOsmImage().getUuid());
            return SUCCESS;
        } catch (NumberFormatException exception) {
            addActionError(getText("page.actionError.get.numberFormatException"));
            exception.printStackTrace();
            return ERROR;
        } catch (NullPointerException exception) {
            addActionError(getText("page.actionError.get.nullPointerException"));
            exception.printStackTrace();
            return ERROR;
        }
    }

    @Override
    public void withServletRequest(HttpServletRequest httpServletRequest) {
        this.servletRequest = httpServletRequest;
    }

}// Ende class
