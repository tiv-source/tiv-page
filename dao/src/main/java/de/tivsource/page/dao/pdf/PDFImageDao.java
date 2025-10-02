/**
 * 
 */
package de.tivsource.page.dao.pdf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.slider.SliderImageDao;
import de.tivsource.page.entity.pdf.PDFImage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PDFImageDao implements PDFImageDaoLocal {

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
     * @see de.tivsource.page.dao.pdf.PDFImageDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public PDFImage findByUuid(String uuid) {
        LOGGER.info("findByUuid(String uuid) aufgerufen");
        return entityManager.find(PDFImage.class, uuid);
    }

}// Ende class
