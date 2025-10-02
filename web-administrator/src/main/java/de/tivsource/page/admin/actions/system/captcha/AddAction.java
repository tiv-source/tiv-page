package de.tivsource.page.admin.actions.system.captcha;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.common.captcha.image.CaptchaImage;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.property.Property;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="captchaAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/add_form.jsp")
  }),
  @TilesDefinition(name="captchaAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -1217385198172019511L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private Captcha captcha;

    @StrutsParameter(depth=2)
    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    @Override
    @Actions({
        @Action(
                value = "add",
                results = {
                        @Result(name = "success", type = "redirectAction", location = "index.html"),
                        @Result(name = "input", type="tiles", location = "captchaAddForm"),
                        @Result(name = "error", type="tiles", location = "captchaAddError")
                }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        if(captcha != null) {
            captcha.getImage().setCaptcha(captcha);
            captcha.getImage().setUuid(UUID.randomUUID().toString());
            captcha.getImage().generate();
            captcha.getImage().setCreated(new Date());
            captcha.getImage().setModified(new Date());
            captcha.getImage().setModifiedAddress(remoteAddress);
            captcha.getImage().setModifiedBy(remoteUser);
            
            captcha.setUuid(UUID.randomUUID().toString());
            captcha.setCreated(new Date());
            captcha.setModified(new Date());
            captcha.setModifiedBy(remoteUser);
            captcha.setModifiedAddress(remoteAddress);
            captchaDaoLocal.merge(captcha);
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

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.captcha = new Captcha();
            this.captcha.setImage(new CaptchaImage());
            this.captcha.getImage().setCaptcha(this.captcha);
            this.captcha.getImage().setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class
