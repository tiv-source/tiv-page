/**
 * 
 */
package de.tivsource.page.dao.mail;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.mail.MailAddressLockRequest;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class MailAddressLockRequestDao implements MailAddressLockRequestDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MailAddressLockRequestDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#merge(de.tivsource.page.entity.mail.MailAddressLockRequest)
     */
    @Override
    public void merge(MailAddressLockRequest mailAddressLockRequest) {
        LOGGER.info("merge(MailAddressLockRequest mailAddressLockRequest) aufgerufen");
        entityManager.merge(mailAddressLockRequest);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#delete(de.tivsource.page.entity.mail.MailAddressLockRequest)
     */
    @Override
    public void delete(MailAddressLockRequest mailAddressLockRequest) {
        entityManager.remove(entityManager.find(MailAddressLockRequest.class, mailAddressLockRequest.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public MailAddressLockRequest findByUuid(String uuid) {
        return entityManager.find(MailAddressLockRequest.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#existsALockRequest(java.lang.String)
     */
    @Override
    public Boolean existsALockRequest(String hash) {
        Query query = entityManager.createQuery("SELECT ma FROM MailAddressLockRequest ma WHERE ma.hash = :hash");
        query.setParameter("hash", hash);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MailAddressLockRequest> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("SELECT ma FROM MailAddressLockRequest ma");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MailAddressLockRequest> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT ma FROM MailAddressLockRequest ma ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(ma) from MailAddressLockRequest ma");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
