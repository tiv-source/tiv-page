/**
 * 
 */
package de.tivsource.page.dao.mail;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.mail.MailAddressLock;
import de.tivsource.page.helper.Passwords;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class MailAddressLockDao implements MailAddressLockDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MailAddressLockDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#merge(de.tivsource.page.entity.mail.MailAddressLock)
     */
    @Override
    public void merge(MailAddressLock mailAddressLock) {
        LOGGER.info("merge(MailAddressLock mailAddressLock) aufgerufen");
        entityManager.merge(mailAddressLock);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#delete(de.tivsource.page.entity.mail.MailAddressLock)
     */
    @Override
    public void delete(MailAddressLock mailAddressLock) {
        entityManager.remove(entityManager.find(MailAddressLock.class, mailAddressLock.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#existMailAddressLock(java.lang.String)
     */
    @Override
    public Boolean existMailAddressLock(String mailAddress) {
        byte[] hashBytes = Passwords.hash(mailAddress.toCharArray(), "UltrageheimerSaltfürMailAdressen2022!".getBytes());
        String hash = Base64.encodeBase64String(hashBytes);
        Query query = entityManager.createQuery("select ma from MailAddressLock ma where ma.hash = :hash");
        query.setParameter("hash", hash);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public MailAddressLock findByUuid(String uuid) {
        return entityManager.find(MailAddressLock.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MailAddressLock> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("SELECT ma FROM MailAddressLock ma");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MailAddressLock> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT ma FROM MailAddressLock ma ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressLockDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(ma) from MailAddressLock ma");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
