package de.tivsource.page.user.actions.sitemap;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
public class ManualAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
	private static final long serialVersionUID = 7281611881470319945L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(ManualAction.class);

    @InjectEJB(name="ManualDao")
    private ManualDaoLocal manualDaoLocal;

    @Override
    @Actions({
        @Action(value = "manual", results = {
            @Result(name = "success", type = "tiles", location = "sitemapManual")
        })
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
        this.getLanguageFromActionContext();

        return SUCCESS;
    }// Ende execute()

    public List<Manual> getManuals() {
        return manualDaoLocal.findAll(0, manualDaoLocal.countAll());
    }

}// Ende class
