/**
 * 
 */
package de.tivsource.page.dao.gallery;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import de.tivsource.page.entity.gallery.Gallery;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class GalleryDao implements GalleryDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(GalleryDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#save(de.tivsource.page.entity.gallery.Gallery)
	 */
	@Override
	public void save(Gallery gallery) {
        LOGGER.info("save(Gallery gallery) aufgerufen");
        entityManager.persist(gallery);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#merge(de.tivsource.page.entity.gallery.Gallery)
	 */
	@Override
	public void merge(Gallery gallery) {
        LOGGER.info("merge(Gallery gallery) aufgerufen");
        entityManager.merge(gallery);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#delete(de.tivsource.page.entity.gallery.Gallery)
	 */
	@Override
	public void delete(Gallery gallery) {
		entityManager.remove(entityManager.find(Gallery.class, gallery.getUuid()));
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#isGallery(java.lang.String)
	 */
	@Override
	public Boolean isGallery(String uuid) {
        Query query = entityManager.createQuery("select g from Gallery g where g.uuid = ?1 and g.visible = 'Y' order by g.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public Gallery findByUuid(String uuid) {
		return entityManager.find(Gallery.class, uuid);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Gallery> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Gallery g order by g.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Gallery> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT g FROM Gallery g JOIN g.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(g) from Gallery g");
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class