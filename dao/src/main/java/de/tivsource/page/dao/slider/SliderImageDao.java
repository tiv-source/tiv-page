/**
 * 
 */
package de.tivsource.page.dao.slider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.slider.SliderImage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
