package de.tivsource.page.admin.actions.system.captcha;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="captchaDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/delete_form.jsp")
  }),
  @TilesDefinition(name="captchaDeleteError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/delete_error.jsp")
  })
})
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6168653546389713341L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

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
                value = "delete",
                results = {
                        @Result(name = "success", type = "redirectAction", location = "index.html"),
                        @Result(name = "input", type="tiles", location = "captchaDeleteForm"),
                        @Result(name = "error", type="tiles", location = "captchaDeleteError")
                }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        if(captcha != null) {
            Captcha dbCaptcha = captchaDaoLocal.findByUuid(captcha.getUuid());
            captchaDaoLocal.delete(dbCaptcha);
            return SUCCESS;
        } else {
            return ERROR;
        }

    }// Ende execute()

}// Ende class
