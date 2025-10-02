package de.tivsource.page.css.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 *
 * @author Marc Michele
 */
public class CssAction extends ActionSupport implements ServletRequestAware {

    /**
     * Generierte Versions-Kennung der Klasse.
     */
    private static final long serialVersionUID = 7367696315767291095L;

    private static final Logger logger = LogManager.getLogger(CssAction.class);

    /**
     * Servlet-Request der die Post und Get Daten der Session enthält.
     */
    private HttpServletRequest servletRequest;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="CSSFileDao")
    private CSSFileDaoLocal cssFileDaoLocal;

    private FileInputStream cssFileInputStream;
    
    /**
     * @return the cssFileInputStream
     */
    public FileInputStream getCssFileInputStream() {
        return cssFileInputStream;
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
            value = "*",
            results = {
                @Result(
                    name = "success", 
                    type="stream", 
                    params= { "contentType","text/css","inputName","cssFileInputStream"}
                ),
                @Result(name = "input", type="tiles", location="cssTemplate")
            }
        )
    })
    public String execute() {
        logger.trace("execute() aufgerufen.");

        // Lese lastModified aus der Datenbank
        String lastModified = propertyDaoLocal.findByKey("cssFile.lastModified").getValue();
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

        response.setHeader("Expires", expires);
        response.setHeader("Last-Modified", lastModified);
        response.setHeader("Retry-After", expires);

        if(since!=null && since.equals(lastModified)) {
            response.setStatus(304);
            return INPUT;
        }

        String cssFileUuid = ServletActionContext.getRequest().getServletPath();

        logger.info("CSS-Datei UUID: " + cssFileUuid);
        cssFileUuid = cssFileUuid.replaceAll("/", "");
        cssFileUuid = cssFileUuid.replaceAll(".css", "");
        logger.info("CSS-Datei UUID: " + cssFileUuid);

        CSSFile cssFile = this.cssFileDaoLocal.findByUuid(cssFileUuid);
        try {
            this.cssFileInputStream = new FileInputStream(new File(cssFile.getPath()));
            return SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ERROR;
        }

    }// Ende execute()

    @Override
    public void withServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

}// Ende class
