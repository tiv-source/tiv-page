/**
 * 
 */
package de.tivsource.page.dao.pdf;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.enumeration.PDFType;
import de.tivsource.page.entity.pdf.PDF;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class PDFDao implements PDFDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PDFDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#merge(de.tivsource.page.entity.pdf.PDF)
     */
    @Override
    public void merge(PDF pdf) {
        LOGGER.info("merge(PDF pdf) aufgerufen");
        entityManager.merge(pdf);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#delete(de.tivsource.page.entity.pdf.PDF)
     */
    @Override
    public void delete(PDF pdf) {
        entityManager.remove(entityManager.find(PDF.class, pdf.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#isPDF(java.lang.String)
     */
    @Override
    public Boolean isPDF(String uuid) {
        Query query = entityManager.createQuery("select p from PDF p where p.uuid = :uuid and p.visible = :visible order by p.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#findVisibleByUuid(java.lang.String)
     */
    @Override
    public PDF findVisibleByUuid(String uuid) {
        Query query = entityManager.createQuery("select p from PDF p where p.uuid = :uuid and p.visible = :visible order by p.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (PDF) (query.getResultList().size() > 0 && query.getResultList().size() < 2 ? query.getSingleResult() : null) ;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public PDF findByUuid(String uuid) {
        return entityManager.find(PDF.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PDF> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from PDF p order by p.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PDF> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT p FROM PDF p JOIN p.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PDF> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("from PDF p where p.visible = :visible order by p.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PDF> findAllVisible(PDFType pdfType, Integer start, Integer max) {
        Query query = entityManager.createQuery("from PDF p where p.visible = :visible and p.pdfType = :pdfType order by p.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        query.setParameter("pdfType", pdfType);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.pdf.PDFDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(p) from PDF p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(p) from PDF p WHERE p.visible = :visible");
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
