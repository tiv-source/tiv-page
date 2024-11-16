/**
 * 
 */
package de.tivsource.page.dao.cssgroup;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.common.css.CSSGroup;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class CSSGroupDao implements CSSGroupDaoLocal {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CSSGroupDao.class);

    /**
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#save(de.tivsource.shop.common.css.CSSGroup)
     */
    @Override
    public void save(CSSGroup cssGroup) {
        LOGGER.info("save(CSSGroup cssGroup) aufgerufen");
        entityManager.persist(cssGroup);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#merge(de.tivsource.shop.common.css.CSSGroup)
     */
    @Override
    public void merge(CSSGroup cssGroup) {
        LOGGER.info("merge(CSSGroup cssGroup) aufgerufen");
        Iterator<CSSFile> newCssFiles = cssGroup.getFiles().iterator();
        while (newCssFiles.hasNext()) {
            CSSFile next = newCssFiles.next();
            CSSFile dbCssFile = entityManager.find(CSSFile.class, next.getUuid());
            LOGGER.info("Version der CSS-Datei in der Datenbank: " + dbCssFile.getVersion());
        }
        entityManager.merge(cssGroup);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#delete(de.tivsource.shop.common.css.CSSGroup)
     */
    @Override
    public void delete(CSSGroup cssGroup) {
        CSSGroup dbCSSGroup = entityManager.find(CSSGroup.class, cssGroup.getUuid());
        if (dbCSSGroup.getFiles().size() > 0) {
            Iterator<CSSFile> cssFiles = dbCSSGroup.getFiles().iterator();
            while (cssFiles.hasNext()) {
                CSSFile next = cssFiles.next();
                next.getGroups().remove(dbCSSGroup);
                entityManager.merge(next);
            }
            dbCSSGroup.setFiles(null);
        }
        entityManager.remove(dbCSSGroup);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#deleteFiles(de.tivsource.shop.common.css.CSSGroup)
     */
    @Override
    public void deleteFiles(CSSGroup cssGroup) {
        CSSGroup dbCSSGroup = entityManager.find(CSSGroup.class, cssGroup.getUuid());
        if (dbCSSGroup.getFiles().size() > 0) {
            Iterator<CSSFile> cssFiles = dbCSSGroup.getFiles().iterator();
            while (cssFiles.hasNext()) {
                CSSFile next = cssFiles.next();
                next.getGroups().remove(dbCSSGroup);
                entityManager.merge(next);
            }
            dbCSSGroup.setFiles(null);
            entityManager.merge(dbCSSGroup);
        }
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#hasReferences(java.lang.String)
     */
    @Override
    public Boolean hasReferences(String uuid) {
        Query query = entityManager.createQuery("SELECT ci FROM ContentItem ci JOIN ci.cssGroup cg WHERE cg.uuid = :uuid order by ci.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public CSSGroup findByUuid(String uuid) {
        return entityManager.find(CSSGroup.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CSSGroup> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from CSSGroup cg order by cg.language asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CSSGroup> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT cg FROM CSSGroup cg ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssgroup.CSSGroupDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager
                .createQuery("Select Count(cg) from CSSGroup cg");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
