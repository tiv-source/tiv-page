/**
 * 
 */
package de.tivsource.page.dao.page;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.tivsource.page.entity.page.Page;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PageDao implements PageDaoLocal {

    private static final Logger LOGGER_INFO = Logger.getLogger("INFO");
    private static final Logger LOGGER_TRACE = Logger.getLogger("TRACE");

    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#save(de.tivsource.page.entity.page.Page)
     */
    @Override
    public void save(Page page) {
        LOGGER_INFO.info("save(Page page) aufgerufen");
        entityManager.persist(page);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#merge(de.tivsource.page.entity.page.Page)
     */
    @Override
    public void merge(Page page) {
        LOGGER_TRACE.info("merge(Page page) aufgerufen");
        entityManager.merge(page);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#delete(de.tivsource.page.entity.page.Page)
     */
    @Override
    public void delete(Page page) {
        entityManager.remove(entityManager.find(Page.class, page.getUuid()));
    }

    @Override
    public Boolean isPageUrl(String urlName) {
        Query query = entityManager.createQuery("select p from Page p where p.technical = ?1 and p.visible = 'Y' order by p.uuid asc");
        query.setParameter("1", urlName);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Page findByTechnical(String technical) {
        LOGGER_INFO.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select p from Page p where p.technical = ?1");
        query.setParameter("1", technical);
        return (Page)query.getSingleResult();
    }

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
    public List<Page> findAll(Integer start, Integer max, String field,
            String order) {
        String queryString = "select p from Page p order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findAllTopNavigation()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Page> findAllTopNavigation() {
        Query query = entityManager.createQuery(
                "from Page p where p.topNavigation = 'Y' "
                + "and p.visible = 'Y' "
                + "order by p.topNavigationOrder desc"
                );
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Page> findAllNavigation() {
        Query query = entityManager.createQuery("from Page p where p.navigation = 'Y' and p.visible = 'Y' order by p.navigationOrder asc");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Page> findAllBottomNavigation() {
        Query query = entityManager.createQuery("from Page p where p.bottomNavigation = 'Y' and p.visible = 'Y' order by p.bottomNavigationOrder asc");
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
