package de.tivsource.page.admin.actions.system.captcha;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="captchaEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/edit_form.jsp")
  }),
  @TilesDefinition(name="captchaEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8810065390227066052L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Captcha captcha;

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    @Override
    @Actions({
        @Action(
                value = "edit",
                results = {
                        @Result(name = "success", type = "redirectAction", location = "index.html"),
                        @Result(name = "input",   type = "tiles", location = "captchaEditForm"),
                        @Result(name = "error",   type = "tiles", location = "captchaEditError")
                }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(captcha != null) {
            LOGGER.info(captcha.getUuid());
            Captcha dbCaptcha = captchaDaoLocal.findByUuid(captcha.getUuid());
            if(captcha.getImage() != null && captcha.getImage().getUploadFile() != null) {
                dbCaptcha.getImage().setUploadFile(captcha.getImage().getUploadFile());
                dbCaptcha.getImage().generate();
            }
            dbCaptcha.setContent(captcha.getContent());
            dbCaptcha.setModified(new Date());
            dbCaptcha.setModifiedBy(remoteUser);
            dbCaptcha.setModifiedAddress(remoteAddress);
            captchaDaoLocal.merge(dbCaptcha);
            setLastModified();
            return SUCCESS;
        } else {
            return ERROR;
        }

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
        Property lastModified = propertyDaoLocal.findByKey("captcha.image.lastModified");

        if(lastModified != null) {
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        } else {
            lastModified = new Property();
            lastModified.setCreated(new Date());
            lastModified.setKey("captcha.image.lastModified");
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        }
        propertyDaoLocal.merge(lastModified);
    }// Ende setLastModified()

}// Ende class
