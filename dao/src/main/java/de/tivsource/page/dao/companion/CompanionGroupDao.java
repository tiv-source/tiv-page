/**
 * 
 */
package de.tivsource.page.dao.companion;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.companion.CompanionGroup;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class CompanionGroupDao implements CompanionGroupDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CompanionGroupDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#save(de.tivsource.page.entity.companion.CompanionGroup)
     */
    @Override
    public void save(CompanionGroup companionGroup) {
        LOGGER.info("save(CompanionGroup companionGroup) aufgerufen");
        entityManager.persist(companionGroup);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#merge(de.tivsource.page.entity.companion.CompanionGroup)
     */
    @Override
    public void merge(CompanionGroup companionGroup) {
        LOGGER.info("merge(CompanionGroup companionGroup) aufgerufen");
        entityManager.merge(companionGroup);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#delete(de.tivsource.page.entity.companion.CompanionGroup)
     */
    @Override
    public void delete(CompanionGroup companionGroup) {
        entityManager.remove(entityManager.find(CompanionGroup.class, companionGroup.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#isCompanionGroup(java.lang.String)
     */
    @Override
    public Boolean isCompanionGroup(String uuid) {
        Query query = entityManager.createQuery("select cg from CompanionGroup cg where cg.uuid = :uuid and cg.visible = :visible order by cg.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#isCompanionGroupTechnical(java.lang.String)
     */
    @Override
    public Boolean isCompanionGroupTechnical(String technical) {
        Query query = entityManager.createQuery("select cg from CompanionGroup cg where cg.technical = :technical and cg.visible = :visible order by cg.uuid asc");
        query.setParameter("technical", technical);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#hasReferences(java.lang.String)
     */
    @Override
    public Boolean hasReferences(String uuid) {
        Query query = entityManager.createQuery("select c from Companion c where c.group.uuid = :uuid order by c.uuid asc");
        query.setParameter("uuid", uuid);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public CompanionGroup findByUuid(String uuid) {
        return entityManager.find(CompanionGroup.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public CompanionGroup findByTechnical(String technical) {
        Query query = entityManager.createQuery("select cg from CompanionGroup cg where cg.technical = :technical and cg.visible = :visible order by cg.uuid asc");
        query.setParameter("technical", technical);
        query.setParameter("visible", true);
        return (CompanionGroup) query.getSingleResult();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CompanionGroup> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from CompanionGroup cg order by cg.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CompanionGroup> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT cg FROM CompanionGroup cg JOIN cg.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CompanionGroup> findAllVisible(Integer start, Integer max) {
        Query query = entityManager.createQuery("from CompanionGroup cg where cg.visible = :visible order by cg.orderNumber asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(cg) from CompanionGroup cg");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.companion.CompanionGroupDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(cg) from CompanionGroup cg where cg.visible = :visible");
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
