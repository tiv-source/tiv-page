package de.tivsource.page.admin.actions.locations.request;

import java.io.File;
import java.io.IOException;
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
import org.apache.tika.Tika;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.request.RequestDaoLocal;
import de.tivsource.page.entity.request.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @InjectEJB(name="RequestDao")
    private RequestDaoLocal requestDaoLocal;

    /**
     * Aktueller Antrag.
     */
    private Request request;

    /**
     * 
     */
    private String mimeType = "image/png";
    
    /**
     * Das Bild darf normalerweise aus dem Cache geladen werden.
     */
    private boolean cache = true;

    /**
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
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
            value = "*/original",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","mimeType","inputName","request.proofOfAuthorityFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        )
    })
    public String execute() {
        logger.trace("execute() aufgerufen.");

        // Lese lastModified aus der Datenbank
        String lastModified = propertyDaoLocal.findByKey("captcha.image.lastModified").getValue();
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

        String requestUuid = ServletActionContext.getRequest().getServletPath();

        logger.info("Request UUID: " + requestUuid);
        requestUuid = requestUuid.replaceAll("/locations/request/", "");
        requestUuid = requestUuid.replaceAll("/original.html", "");
        logger.info("Request UUID: " + requestUuid);

        try {
            this.request = this.requestDaoLocal.findByUuid(requestUuid);
            Tika tika = new Tika();
            mimeType = tika.detect(new File(request.getProofOfAuthority()));
            return SUCCESS;
        } catch (NumberFormatException exception) {
            addActionError(getText("request.actionError.get.numberFormatException"));
            exception.printStackTrace();
            return ERROR;
        } catch (NullPointerException exception) {
            addActionError(getText("request.actionError.get.nullPointerException"));
            exception.printStackTrace();
            return ERROR;
        } catch (IOException exception) {
            addActionError(getText("request.actionError.get.iOException"));
            exception.printStackTrace();
            return ERROR;
        }
    }

    @Override
    public void withServletRequest(HttpServletRequest httpServletRequest) {
        this.servletRequest = httpServletRequest;
    }

}// Ende class
