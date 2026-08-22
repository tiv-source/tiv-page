package de.tivsource.page.admin.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;

/**
 * @author Marc Michele
 *
 */
@Service
public class CSSGroupService {

    private static final Logger LOGGER = LogManager.getLogger(CSSGroupService.class);

    @Autowired
    private CSSGroupDaoLocal cssGroupDaoLocal;

    public List<CSSGroup> getAllCSSGroups() {
        LOGGER.debug("getAllCSSGroups() aufgerufen.");
        return cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

}// Ende class
