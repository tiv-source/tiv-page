/**
 * 
 */
package de.tivsource.page.dao.slider;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.slider.SliderImage;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class SliderImageDao implements SliderImageDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SliderImageDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.slider.SliderImageDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public SliderImage findByUuid(String uuid) {
        LOGGER.info("findByUuid(String uuid) aufgerufen");
        return entityManager.find(SliderImage.class, uuid);
    }

}// Ende class
