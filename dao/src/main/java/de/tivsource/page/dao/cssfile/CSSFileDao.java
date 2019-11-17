/**
 * 
 */
package de.tivsource.page.dao.cssfile;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.css.CSSFile;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class CSSFileDao implements CSSFileDaoLocal {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CSSFileDao.class);

    /**
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#save(de.tivsource.shop.common.css.CSSFile)
     */
    @Override
    public void save(CSSFile cssFile) {
        LOGGER.info("save(CSSFile cssFile) aufgerufen");
        entityManager.persist(cssFile);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#merge(de.tivsource.shop.common.css.CSSFile)
     */
    @Override
    public void merge(CSSFile cssFile) {
        LOGGER.info("merge(CSSFile cssFile) aufgerufen");
        entityManager.merge(cssFile);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#delete(de.tivsource.shop.common.css.CSSFile)
     */
    @Override
    public void delete(CSSFile cssFile) {
        entityManager.remove(entityManager.find(CSSFile.class,
                cssFile.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#hasReferences(java.lang.String)
     */
    @Override
    public Boolean hasReferences(String uuid) {
        Query query = entityManager.createQuery("SELECT cg FROM CSSGroup cg JOIN cg.files cgf WHERE cgf.uuid = :uuid order by cg.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public CSSFile findByUuid(String uuid) {
        return entityManager.find(CSSFile.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CSSFile> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from CSSFile c order by c.priority asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CSSFile> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT c FROM CSSFile c ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.shop.dao.cssfile.CSSFileDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(c) from CSSFile c");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
