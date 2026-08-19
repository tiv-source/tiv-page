/**
 * 
 */
package de.tivsource.page.dao.page;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.jpa.AvailableHints;

import de.tivsource.page.entity.page.Page;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


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
        Query query = entityManager.createQuery("select p from Page p where p.technical = :urlName and p.visible = :visible order by p.uuid asc");
        query.setParameter("urlName", urlName);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#isPageUuidWithoutVisibleCheck(java.lang.String)
     */
    @Override
    public Boolean isPageUuidWithoutVisibleCheck(String uuid) {
        Query query = entityManager.createQuery("select p from Page p where p.uuid = :uuid order by p.uuid asc");
        query.setParameter("uuid", uuid);
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

    @Override
    public Boolean hasSubSumption(String uuid) {
        Page page = entityManager.find(Page.class, uuid);
        Query query = entityManager.createQuery("select s from Subsumption s where :contentItem MEMBER OF s.contentItems order by s.uuid asc");
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
        Page page = query.getResultList().isEmpty() ? null : (Page)query.setHint(AvailableHints.HINT_READ_ONLY, true).getSingleResult(); 
        if(page != null) {
            Hibernate.initialize(page.getDescriptionMap());
            Hibernate.initialize(page.getImage());
            Hibernate.initialize(page.getContentMap());
            Hibernate.initialize(page.getCssGroup());
            Hibernate.initialize(page.getCssGroup().getFiles());
            
            Hibernate.unproxy(page.getDescriptionMap());
            Hibernate.unproxy(page.getContentMap());
            Hibernate.unproxy(page.getCssGroup());
            Hibernate.unproxy(page.getCssGroup().getFiles());
        }
        return page;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.page.PageDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Page findByUuid(String uuid) {
        Page page = entityManager.find(Page.class, uuid); 
        Hibernate.initialize(page.getDescriptionMap());
        Hibernate.initialize(page.getImage());
        Hibernate.initialize(page.getContentMap());
        Hibernate.initialize(page.getCssGroup());
        Hibernate.initialize(page.getCssGroup().getFiles());
        return page;
    }

    @Override
    public Page findRevisionByUuid(String uuid, String rev) {
        LOGGER.info("Page Uuid: " + uuid);
        LOGGER.info("Page Rev: " + rev);
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        Page page = auditReader.find(Page.class, uuid, Integer.parseInt(rev));
        Hibernate.initialize(page.getContentMap());
        Hibernate.initialize(page.getCssGroup());
        //LOGGER.info("CssGroup: " + page.getCssGroup().getName());
        //Hibernate.initialize(page.getCssGroup().getFiles());
        //Hibernate.initialize(page.getCssGroup().getPictureItems());
        Hibernate.initialize(page.getDescriptionMap());
        Hibernate.initialize(page.getImage());
        LOGGER.info("Page (DB): " + page.getUuid());
        LOGGER.info("Page Name (DB): " + page.getName("de"));
        return page;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findRevisionsByUuid(String uuid) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Page.class, false, true);
        query.add(AuditEntity.id().eq(uuid));
        query.addOrder(AuditEntity.revisionNumber().desc());
        return query.getResultList();
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
        List<Page> pageList = query.getResultList();
        for (Page page : pageList) {
            Hibernate.initialize(page.getDescriptionMap());
            Hibernate.initialize(page.getImage());
        }
        return pageList;
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
        List<Page> pageList = query.getResultList();
        for (Page page : pageList) {
            Hibernate.initialize(page.getDescriptionMap());
            Hibernate.initialize(page.getImage());
        }
        return pageList;
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
