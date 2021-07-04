/**
 * 
 */
package de.tivsource.page.dao.page;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.page.Page;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PageDao implements PageDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PageDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#save(de.tivsource.page.entity.page.Page)
     */
    @Override
    public void save(Page page) {
        LOGGER.info("save(Page page) aufgerufen");
        entityManager.persist(page);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#merge(de.tivsource.page.entity.page.Page)
     */
    @Override
    public void merge(Page page) {
        LOGGER.info("merge(Page page) aufgerufen");
        entityManager.merge(page);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#delete(de.tivsource.page.entity.page.Page)
     */
    @Override
    public void delete(Page page) {
        entityManager.remove(entityManager.find(Page.class, page.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#isPageUrl(java.lang.String)
     */
    @Override
    public Boolean isPageUrl(String urlName) {
        Query query = entityManager.createQuery("select p from Page p where p.technical = :urlName and p.visible = 'Y' order by p.uuid asc");
        query.setParameter("urlName", urlName);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#hasMenuEntry(java.lang.String)
     */
    @Override
    public Boolean hasMenuEntry(String uuid) {
        Page page = entityManager.find(Page.class, uuid);
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.contentItem = :contentItem order by ce.uuid asc");
        query.setParameter("contentItem", page);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Page findByTechnical(String technical) {
        LOGGER.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select p from Page p where p.technical = :technical");
        query.setParameter("technical", technical);
        return (Page)query.getSingleResult();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Page findByUuid(String uuid) {
        return entityManager.find(Page.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Page> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Page p");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Page> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT p FROM Page p JOIN p.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(p) from Page p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
