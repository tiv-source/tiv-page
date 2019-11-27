package de.tivsource.page.image.actions.slider;

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
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.slider.SliderImageDaoLocal;
import de.tivsource.page.entity.slider.SliderImage;

/**
 *
 * @author Marc Michele
 */
public class ImageAction extends ActionSupport implements ServletRequestAware {

    /**
     * Generierte Versions-Kennung der Klasse.
     */
    private static final long serialVersionUID = 7367696315767291095L;

    private static final Logger logger = LogManager.getLogger(ImageAction.class);

    /**
     * Servlet-Request der die Post und Get Daten der Session enthält.
     */
    private HttpServletRequest servletRequest;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name = "SliderImageDao")
    private SliderImageDaoLocal sliderImageDaoLocal;

    /**
     * Das Bild des aktuellen Sliders.
     */
    private SliderImage sliderImage;

    /**
     * Das Bild darf normalerweise aus dem Cache geladen werden.
     */
    private boolean cache = true;

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * @return the article
     */
    public SliderImage getSliderImage() {
        return sliderImage;
    }

    /**
     * @param cache the cache to set
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * Die Methode get() laedt eine Kategorie anhand des
     * ServletRequest-Parameters "category_id" aus der entsprechenden
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
                    params= { "contentType","image/png","inputName","sliderImage.microFileInputStream"}
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
                    params= { "contentType","image/png","inputName","sliderImage.thumbnailFileInputStream"}
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
                    params= { "contentType","image/png","inputName","sliderImage.smallFileInputStream"}
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
                    params= { "contentType","image/png","inputName","sliderImage.normalFileInputStream"}
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
                    params= { "contentType","image/png","inputName","sliderImage.largeFileInputStream"}
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
                    params= { "contentType","image/png","inputName","sliderImage.originalFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        )
    })
    public String execute() {
        logger.trace("execute() aufgerufen.");

        // Lese lastModified aus der Datenbank
        String lastModified = propertyDaoLocal.findByKey("slider.image.lastModified").getValue();
        // Hole If-Modified-Since aus der Anfrage
        String since = servletRequest.getHeader("If-Modified-Since");
        logger.info("If-Modified-Since: " + since);

        // Setze Fomatierung
        // "Thu, 01 Dec 1994 16:00:00 GMT"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.HOUR, 12);
        String expires = simpleDateFormat.format(gregorianCalendar.getTime());

        HttpServletResponse response = ServletActionContext.getResponse();
        //response.setHeader("Cache-control", "no-cache, no-store");
        //response.setHeader("Pragma", "no-cache");

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

        String sliderImageUuid = ServletActionContext.getRequest().getServletPath();

        logger.info("Slider Image UUID: " + sliderImageUuid);
        sliderImageUuid = sliderImageUuid.replaceAll("/slider/", "");
        sliderImageUuid = sliderImageUuid.replaceAll("/micro.png", "");
        sliderImageUuid = sliderImageUuid.replaceAll("/thumbnail.png", "");
        sliderImageUuid = sliderImageUuid.replaceAll("/small.png", "");
        sliderImageUuid = sliderImageUuid.replaceAll("/normal.png", "");
        sliderImageUuid = sliderImageUuid.replaceAll("/large.png", "");
        sliderImageUuid = sliderImageUuid.replaceAll("/original.png", "");
        logger.info("Slider Image UUID: " + sliderImageUuid);

        try {
            this.sliderImage = this.sliderImageDaoLocal.findByUuid(sliderImageUuid);
            return SUCCESS;
        } catch (NumberFormatException exception) {
            addActionError(getText("slider.actionError.get.numberFormatException"));
            exception.printStackTrace();
            return ERROR;
        } catch (NullPointerException exception) {
            addActionError(getText("slider.actionError.get.nullPointerException"));
            exception.printStackTrace();
            return ERROR;
        }
    }

}// Ende class
