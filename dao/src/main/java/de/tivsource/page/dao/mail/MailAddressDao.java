/**
 * 
 */
package de.tivsource.page.dao.mail;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.mail.MailAddress;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class MailAddressDao implements MailAddressDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MailAddressDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#merge(de.tivsource.page.entity.mail.MailAddress)
     */
    @Override
    public void merge(MailAddress mailAddress) {
        LOGGER.info("merge(MailAddress mailAddress) aufgerufen");
        entityManager.merge(mailAddress);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#delete(de.tivsource.page.entity.mail.MailAddress)
     */
    @Override
    public void delete(MailAddress mailAddress) {
        entityManager.remove(entityManager.find(MailAddress.class, mailAddress.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#mailAddressExist(java.lang.String)
     */
    @Override
    public Boolean mailAddressExist(String mailAddress) {
        Query query = entityManager.createQuery("select ma from MailAddress ma where ma.mail = :mail");
        query.setParameter("mail", mailAddress);
        return (query.getResultList().size() > 0 ? true : false);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public MailAddress findByUuid(String uuid) {
        return entityManager.find(MailAddress.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MailAddress> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("SELECT ma FROM MailAddress ma");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MailAddress> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT ma FROM MailAddress ma ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.mail.MailAddressDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(ma) from MailAddress ma");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
