/**
 * 
 */
package de.tivsource.page.dao.picture;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.entity.picture.PictureUrl;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PictureDao implements PictureDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PictureDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#save(de.tivsource.page.entity.picture.Picture)
	 */
	@Override
	public void save(Picture picture) {
        LOGGER.info("save(Picture picture) aufgerufen");
        entityManager.persist(picture);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#merge(de.tivsource.page.entity.picture.Picture)
	 */
	@Override
	public void merge(Picture picture) {
        LOGGER.info("merge(Picture picture) aufgerufen");
        entityManager.merge(picture);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#delete(de.tivsource.page.entity.picture.Picture)
	 */
	@Override
	public void delete(Picture picture) {
		// Lösche das Bild aus der Datenbank
		entityManager.remove(entityManager.find(Picture.class, picture.getUuid()));
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.picture.PictureDaoLocal#delete(java.lang.String)
     */
    @Override
    public void delete(String pictureUrlUuid) {
        // // Lösche die Bild Url aus der Datenbank
        entityManager.remove(entityManager.find(PictureUrl.class, pictureUrlUuid));
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#isPicture(java.lang.String)
	 */
	@Override
	public Boolean isPicture(String uuid) {
        Query query = entityManager.createQuery("select p from Picture p where p.uuid = :uuid and p.visible = :visible order by p.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#hasReferences(java.lang.String)
	 */
	@Override
	public Boolean hasReferences(String uuid) {
        Query query = entityManager.createQuery("select n from NamingItem n where n.picture.uuid = :uuid order by n.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public Picture findByUuid(String uuid) {
	    Picture picture = entityManager.find(Picture.class, uuid);
	    Hibernate.initialize(picture.getDescriptionMap());
	    Hibernate.initialize(picture.getPictureUrls());
		return picture;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#findAll(java.lang.String)
	 */
	@Override
	public List<Picture> findAll(String uuid) {
	    LOGGER.info("UUID set in findAll(String uuid): " + uuid);
        Query query = entityManager.createQuery("from Gallery g where g.uuid = :uuid");
        query.setParameter("uuid", uuid);
        
        /*
        List<Picture> pictureList = query.getResultList();
        Iterator<Picture> pictureListIterator = pictureList.iterator();
        while(pictureListIterator.hasNext()) {
            Picture next = pictureListIterator.next();
            next.getDescriptionMap().size();
            next.getPictureUrls().size();
        }
        LOGGER.info("Größe der Liste: " + pictureList.size());
        */
        Gallery gallery = (Gallery)query.getResultList().get(0);
        LOGGER.info("Größe der Picture Liste: " + gallery.getPictures().size());
        List<Picture> pictureList = gallery.getPictures();
        Iterator<Picture> pictureListIterator = pictureList.iterator();
        while(pictureListIterator.hasNext()) {
            Picture next = pictureListIterator.next();
            LOGGER.info("Größe der DescriptionMap: " + next.getDescriptionMap().size());
            LOGGER.info("Größe der PictureUrls: " + next.getPictureUrls().size());
        }
        return gallery.getPictures();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Picture> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Picture p order by p.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        LOGGER.info("Startwert: " + start);
        LOGGER.info("Maxwert: " + max);
        
        List<Picture> pictureList = query.getResultList();
        Iterator<Picture> pictureListIterator = pictureList.iterator();
        while(pictureListIterator.hasNext()) {
            Picture next = pictureListIterator.next();
            LOGGER.info("Größe der DescriptionMap: " + next.getDescriptionMap().size());
            LOGGER.info("Größe der PictureUrls: " + next.getPictureUrls().size());
        }
        return pictureList;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Picture> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT p FROM Picture p JOIN p.descriptionMap dm JOIN p.gallery.descriptionMap gdm WHERE dm.language = 'DE' and gdm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.picture.PictureDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Picture> findAll(Integer start, Integer max, String galleryUuid, String field, String order) {
        String queryString = "SELECT DISTINCT p FROM Picture p JOIN p.descriptionMap dm WHERE p.gallery.uuid = :galleryUuid AND dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("galleryUuid", galleryUuid);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.picture.PictureDaoLocal#findPreviousPicture(java.lang.Integer, java.lang.String)
     */
    @Override
    public Picture findPreviousPicture(Integer start, String galleryUuid) {
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.gallery.uuid = :galleryUuid ORDER BY p.orderNumber desc");
        query.setFirstResult(start);
        query.setMaxResults(1);
        query.setParameter("galleryUuid", galleryUuid);
        return (Picture)query.getSingleResult();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.picture.PictureDaoLocal#findCurrentPicture(java.lang.Integer, java.lang.String)
     */
    @Override
    public Picture findCurrentPicture(Integer start, String galleryUuid) {
        LOGGER.info("findCurrentPicture(Integer start, String galleryUuid) aufgerufen");
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.gallery.uuid = :galleryUuid ORDER BY p.orderNumber, p.uuid desc");
        query.setFirstResult(start);
        query.setMaxResults(1);
        query.setParameter("galleryUuid", galleryUuid);
        return (Picture)query.getResultList().get(0);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.picture.PictureDaoLocal#findNextPicture(java.lang.Integer, java.lang.String)
     */
    @Override
    public Picture findNextPicture(Integer start, String galleryUuid) {
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.gallery.uuid = :galleryUuid ORDER BY p.orderNumber desc");
        query.setFirstResult(start);
        query.setMaxResults(1);
        query.setParameter("galleryUuid", galleryUuid);
        return (Picture)query.getSingleResult();
    }
    
	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.picture.PictureDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(p) from Picture p");
        return Integer.parseInt(query.getSingleResult().toString());
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.picture.PictureDaoLocal#countAllInGallery(java.lang.String)
     */
    @Override
    public Integer countAllInGallery(String galleryUuid) {
        Query query = entityManager.createQuery("Select Count(p) from Picture p where p.gallery.uuid = :galleryUuid");
        query.setParameter("galleryUuid", galleryUuid);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
