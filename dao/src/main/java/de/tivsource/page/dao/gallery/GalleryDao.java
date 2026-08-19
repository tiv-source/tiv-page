/**
 * 
 */
package de.tivsource.page.dao.gallery;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.jpa.AvailableHints;

import de.tivsource.page.entity.gallery.Gallery;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class GalleryDao implements GalleryDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(GalleryDao.class);

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
        Query query = entityManager.createQuery("select g from Gallery g where g.uuid = :uuid and g.visible = :visible order by g.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#isGalleryWithoutVisibleCheck(java.lang.String)
     */
    @Override
    public Boolean isGalleryWithoutVisibleCheck(String uuid) {
        Query query = entityManager.createQuery("select g from Gallery g where g.uuid = :uuid order by g.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#isGalleryTechnical(java.lang.String)
	 */
	@Override
	public Boolean isGalleryTechnical(String technical) {
        Query query = entityManager.createQuery("select g from Gallery g where g.technical = :technical and g.visible = :visible order by g.uuid asc");
        query.setParameter("visible", true);
        query.setParameter("technical", technical);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#hasReferences(java.lang.String)
	 */
	@Override
	public Boolean hasReferences(String uuid) {
        Query query = entityManager.createQuery("select p from Picture p where p.gallery.uuid = :uuid order by p.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#hasMenuEntry(java.lang.String)
     */
    @Override
    public Boolean hasMenuEntry(String uuid) {
        Gallery gallery = entityManager.find(Gallery.class, uuid);
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.contentItem = :contentItem order by ce.uuid asc");
        query.setParameter("contentItem", gallery);
        return (query.getResultList().size() > 0 ? true : false);
    }

    @Override
    public Boolean hasSubSumption(String uuid) {
        Gallery gallery = entityManager.find(Gallery.class, uuid);
        Query query = entityManager.createQuery("select s from Subsumption s where :contentItem MEMBER OF s.contentItems order by s.uuid asc");
        query.setParameter("contentItem", gallery);
        return (query.getResultList().size() > 0 ? true : false);
    }
	
	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public Gallery findByUuid(String uuid) {
	    Gallery gallery = entityManager.find(Gallery.class, uuid); 
        Hibernate.initialize(gallery.getDescriptionMap());
        Hibernate.initialize(gallery.getImage());
        Hibernate.initialize(gallery.getContentMap());
        Hibernate.initialize(gallery.getCssGroup());
        Hibernate.initialize(gallery.getCssGroup().getFiles());
        Hibernate.initialize(gallery.getPictures());
        return gallery;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#findByTechnical(java.lang.String)
	 */
	@Override
	public Gallery findByTechnical(String technical) {
        Query query = entityManager.createQuery("select g from Gallery g where g.technical = :technical and g.visible = :visible order by g.uuid asc");
        query.setParameter("visible", true);
        query.setParameter("technical", technical);
        Gallery gallery = query.getResultList().isEmpty() ? null : (Gallery)query.setHint(AvailableHints.HINT_READ_ONLY, true).getSingleResult(); 
        if(gallery != null) {
            Hibernate.initialize(gallery.getDescriptionMap());
            Hibernate.initialize(gallery.getImage());
            Hibernate.initialize(gallery.getContentMap());
            Hibernate.initialize(gallery.getCssGroup());
            Hibernate.initialize(gallery.getCssGroup().getFiles());
            
            Hibernate.unproxy(gallery.getDescriptionMap());
            Hibernate.unproxy(gallery.getContentMap());
            Hibernate.unproxy(gallery.getCssGroup());
            Hibernate.unproxy(gallery.getCssGroup().getFiles());
        }
        return gallery;
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
        List<Gallery> galleryList = query.getResultList();
        for (Gallery gallery : galleryList) {
            Hibernate.initialize(gallery.getDescriptionMap());
            Hibernate.initialize(gallery.getImage());
        }
        return galleryList;
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
        List<Gallery> galleryList = query.getResultList();
        for (Gallery gallery : galleryList) {
            Hibernate.initialize(gallery.getDescriptionMap());
            Hibernate.initialize(gallery.getImage());
        }
        return galleryList;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Gallery> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Gallery g where g.visible = :visible order by g.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        List<Gallery> galleryList = query.getResultList();
        for (Gallery gallery : galleryList) {
            Hibernate.initialize(gallery.getDescriptionMap());
            Hibernate.initialize(gallery.getImage());
        }
        return galleryList;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(g) from Gallery g");
        return Integer.parseInt(query.getSingleResult().toString());
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.gallery.GalleryDaoLocal#countAllVisible()
	 */
	@Override
	public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(g) from Gallery g where g.visible = :visible");
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class
