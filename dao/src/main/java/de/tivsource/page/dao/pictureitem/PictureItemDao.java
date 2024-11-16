/**
 * 
 */
package de.tivsource.page.dao.pictureitem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.pictureitem.PictureItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PictureItemDao implements PictureItemDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PictureItemDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pictureitem.PictureItemLocal#findByUuid(java.lang.String)
     */
    @Override
    public PictureItem findByUuid(String uuid) {
        LOGGER.info("findByUuid(String uuid) aufgerufen.");
        return entityManager.find(PictureItem.class, uuid);
    }

}// Ende class
