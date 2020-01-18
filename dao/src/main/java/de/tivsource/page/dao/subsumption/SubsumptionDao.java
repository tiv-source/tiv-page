/**
 * 
 */
package de.tivsource.page.dao.subsumption;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.subsumption.Subsumption;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class SubsumptionDao implements SubsumptionDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SubsumptionDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#merge(de.tivsource.page.entity.subsumption.Subsumption)
     */
    @Override
    public void merge(Subsumption subsumption) {
        LOGGER.info("merge(Subsumption subsumption) aufgerufen");
        LOGGER.info("Anzahl der ContentItems: " + subsumption.getContentItems().size());
        entityManager.merge(subsumption);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#delete(de.tivsource.page.entity.subsumption.Subsumption)
     */
    @Override
    public void delete(Subsumption subsumption) {
        entityManager.remove(entityManager.find(Subsumption.class, subsumption.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#isSubsumptionUrl(java.lang.String)
     */
    @Override
    public Boolean isSubsumptionUrl(String urlName) {
        Query query = entityManager.createQuery("select s from Subsumption s where s.technical = :urlName and s.visible = 'Y' order by s.uuid asc");
        query.setParameter("urlName", urlName);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#hasMenuEntry(java.lang.String)
     */
    @Override
    public Boolean hasMenuEntry(String uuid) {
        Subsumption subsumption = entityManager.find(Subsumption.class, uuid);
        Query query = entityManager.createQuery("select ce from ContentEntry ce where ce.contentItem = :contentItem order by ce.uuid asc");
        query.setParameter("contentItem", subsumption);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Subsumption findByTechnical(String technical) {
        LOGGER.info("findByTechnical(String technical) aufgerufen.");
        Query query = entityManager.createQuery("select s from Subsumption s where s.technical = :technical");
        query.setParameter("technical", technical);
        return (Subsumption)query.getSingleResult();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Subsumption findByUuid(String uuid) {
        return entityManager.find(Subsumption.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Subsumption> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Subsumption s");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Subsumption> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT s FROM Subsumption s JOIN s.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.subsumption.SubsumptionDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(s) from Subsumption s");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
