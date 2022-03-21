package de.tivsource.page.image.actions.pictureitem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.pictureitem.PictureItemDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.pictureitem.PictureItem;
import de.tivsource.page.entity.pictureitem.PictureItemImage;



/**
 *
 * @author Marc Michele
 */
public class ImageAction extends ActionSupport implements ServletRequestAware {

    /**
     * Generierte Versions-Kennung der Klasse.
     */
    private static final long serialVersionUID = 8022699405981368150L;

    private static final Logger LOGGER = LogManager.getLogger(ImageAction.class);

    /**
     * Servlet-Request der die Post und Get Daten der Session enthält.
     */
    private HttpServletRequest servletRequest;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name = "PictureItemDao")
    private PictureItemDaoLocal pictureItemDaoLocal;

    /**
     * Aktuelle Seite.
     */
    private PictureItem pictureItem;

    private PictureItemImage pictureItemImage;

    private boolean cache = true;

    /**
     * @return the pictureItemImage
     */
    public PictureItemImage getPictureItemImage() {
        return pictureItemImage;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * @param cache the cache to set
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * Die Methode get() laedt eine Allergen anhand des
     * ServletRequest-Parameters "allergen_uuid" aus der entsprechenden
     * Datenbanktabelle und speichert diese in die Variable "this.category"
     * 
     * @return SUCCESS oder ERROR (wenn Exception);
     */
    @Actions({
        @Action(
            value = "*/micro",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","image/png","inputName","pictureItemImage.microFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pictureItemImage.thumbnailFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pictureItemImage.smallFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pictureItemImage.normalFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pictureItemImage.largeFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pictureItemImage.originalFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        )
    })
    public String execute() {
        LOGGER.trace("execute() aufgerufen.");

        // Lese lastModified aus der Datenbank e. G.: "Sun, 01 Jan 2017 00:00:00 GMT"
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

        String pictureItemUuid = ServletActionContext.getRequest().getServletPath();

        LOGGER.info("PictureItem UUID: " + pictureItemUuid);
        pictureItemUuid = pictureItemUuid.replaceAll("/pictureitem/", "");
        pictureItemUuid = pictureItemUuid.replaceAll("/micro.png", "");
        pictureItemUuid = pictureItemUuid.replaceAll("/thumbnail.png", "");
        pictureItemUuid = pictureItemUuid.replaceAll("/small.png", "");
        pictureItemUuid = pictureItemUuid.replaceAll("/normal.png", "");
        pictureItemUuid = pictureItemUuid.replaceAll("/large.png", "");
        pictureItemUuid = pictureItemUuid.replaceAll("/original.png", "");
        LOGGER.info("PictureItem UUID: " + pictureItemUuid);
        
        try {
            // TODO: Check UUID Eingabe auf nicht erlaubte Zeichen  
            this.pictureItem = this.pictureItemDaoLocal.findByUuid(pictureItemUuid);
            LOGGER.info("PictureItem UUID aus Objekt: " + pictureItem.getUuid());
            pictureItemImage = pictureItem.getImage();
            LOGGER.info("PictureItemImage UUID aus Objekt: " + pictureItem.getImage().getUuid());
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

}// Ende class
