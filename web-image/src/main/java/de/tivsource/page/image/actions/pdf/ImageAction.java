package de.tivsource.page.image.actions.pdf;

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
import de.tivsource.page.dao.pdf.PDFImageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.pdf.PDFImage;
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
    private static final long serialVersionUID = -8593906400567637254L;

    private static final Logger logger = LogManager.getLogger(ImageAction.class);

    /**
     * Servlet-Request der die Post und Get Daten der Session enthält.
     */
    private HttpServletRequest servletRequest;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name = "PDFImageDao")
    private PDFImageDaoLocal pdfImageDaoLocal;

    /**
     * Das Bild des aktuellen Sliders.
     */
    private PDFImage pdfImage;

    /**
     * Das Bild darf normalerweise aus dem Cache geladen werden.
     */
    private boolean cache = true;

    public PDFImage getPdfImage() {
        return pdfImage;
    }

    /**
     * @param cache the cache to set
     */
    @StrutsParameter
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
                    params= { "contentType","image/png","inputName","pdfImage.microFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pdfImage.thumbnailFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pdfImage.smallFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pdfImage.normalFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pdfImage.largeFileInputStream"}
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
                    params= { "contentType","image/png","inputName","pdfImage.originalFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="imageTemplate")
            }
        )
    })
    public String execute() {
        logger.trace("execute() aufgerufen.");

        // Lese lastModified aus der Datenbank
        String lastModified = propertyDaoLocal.findByKey("pdf.image.lastModified").getValue();
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

        String pdfImageUuid = ServletActionContext.getRequest().getServletPath();

        logger.info("PDF Image UUID: " + pdfImageUuid);
        pdfImageUuid = pdfImageUuid.replaceAll("/pdf/", "");
        pdfImageUuid = pdfImageUuid.replaceAll("/micro.png", "");
        pdfImageUuid = pdfImageUuid.replaceAll("/thumbnail.png", "");
        pdfImageUuid = pdfImageUuid.replaceAll("/small.png", "");
        pdfImageUuid = pdfImageUuid.replaceAll("/normal.png", "");
        pdfImageUuid = pdfImageUuid.replaceAll("/large.png", "");
        pdfImageUuid = pdfImageUuid.replaceAll("/original.png", "");
        logger.info("PDF Image UUID: " + pdfImageUuid);

        try {
            this.pdfImage = this.pdfImageDaoLocal.findByUuid(pdfImageUuid);
            return SUCCESS;
        } catch (NumberFormatException exception) {
            addActionError(getText("pdfImage.actionError.get.numberFormatException"));
            exception.printStackTrace();
            return ERROR;
        } catch (NullPointerException exception) {
            addActionError(getText("pdfImage.actionError.get.nullPointerException"));
            exception.printStackTrace();
            return ERROR;
        }
    }

    @Override
    public void withServletRequest(HttpServletRequest httpServletRequest) {
        this.servletRequest = httpServletRequest;
    }

}// Ende class
