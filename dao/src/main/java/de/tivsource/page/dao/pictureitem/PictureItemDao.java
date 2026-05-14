/**
 * 
 */
package de.tivsource.page.dao.pictureitem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import de.tivsource.page.entity.pictureitem.PictureItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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
     * @see de.tivsource.page.dao.pictureitem.PictureItemDaoLocal#isPictureItemUuid(java.lang.String)
     */
    @Override
    public Boolean isPictureItemUuid(String uuid) {
        Query query = entityManager.createQuery("select pi from PictureItem pi where pi.uuid = :uuid and pi.visible = :visible order by pi.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pictureitem.PictureItemLocal#findByUuid(java.lang.String)
     */
    @Override
    public PictureItem findByUuid(String uuid) {
        LOGGER.info("findByUuid(String uuid) aufgerufen.");
        PictureItem pictureItem = entityManager.find(PictureItem.class, uuid); 
        Hibernate.initialize(pictureItem.getImage());
        return pictureItem;
    }

}// Ende class
