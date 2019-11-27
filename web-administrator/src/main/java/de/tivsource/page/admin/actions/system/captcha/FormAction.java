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
  @TilesDefinition(name="captchaAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/add_form.jsp")
  }),
  @TilesDefinition(name="captchaEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/edit_form.jsp")
  }),
  @TilesDefinition(name="captchaDeleteForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/system.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/captcha/delete_form.jsp")
  })
})
public class FormAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 2077185860382503376L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(FormAction.class);

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

    private Captcha captcha;

    private String uncheckCaptcha;

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String uncheckCaptcha) {
        this.uncheckCaptcha = uncheckCaptcha;
    }

    @Override
    @Actions({
        @Action(
                value = "editForm",
                results = { @Result(name = "success", type="tiles", location = "captchaEditForm") }
        ),
        @Action(
                value = "addForm",
                results = { @Result(name = "success", type="tiles", location = "captchaAddForm") }
        ),
        @Action(
                value = "deleteForm",
                results = { @Result(name = "success", type="tiles", location = "captchaDeleteForm") }
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        this.loadPageParameter();
        return SUCCESS;
    }// Ende execute()

    private void loadPageParameter() {
        if( uncheckCaptcha != null && uncheckCaptcha != "" && uncheckCaptcha.length() > 0) {
            captcha = captchaDaoLocal.findByUuid(uncheckCaptcha);
        } else {
            captcha = new Captcha();
        }
    }// Ende loadPageParameter()

}// Ende class
