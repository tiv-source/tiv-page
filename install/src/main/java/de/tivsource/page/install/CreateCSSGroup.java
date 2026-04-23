package de.tivsource.page.install;

import java.util.Date;
import java.util.logging.Logger;

import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

public class CreateCSSGroup {

    private static final Logger LOGGER = Logger.getLogger("INFO");
    
    private CSSGroupDaoLocal cssGroupDaoLocal;

    /**
     * @param cssGroupDaoLocal the cssGroupDaoLocal to set
     */
    public void setCssGroupDaoLocal(CSSGroupDaoLocal cssGroupDaoLocal) {
        this.cssGroupDaoLocal = cssGroupDaoLocal;
    }

    public void generate() {
        LOGGER.info("generate() aufgerufen.");
        CSSGroup cssGroup = new CSSGroup();
        cssGroup.setCreated(new Date());
        cssGroup.setDescription("Leere Gruppe muss befüllt oder ersetzt werden");
        cssGroup.setLanguage(Language.DE);
        cssGroup.setModified(new Date());
        cssGroup.setModifiedAddress("Installer");
        cssGroup.setName("Leere Gruppe");
        cssGroup.setUuid("579465e5-64ba-4f56-95b1-293d497139ea");
        cssGroupDaoLocal.save(cssGroup);
    }

}// Ende CreateCSSGroup
