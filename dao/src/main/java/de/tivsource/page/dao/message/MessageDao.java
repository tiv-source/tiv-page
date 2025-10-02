/**
 * 
 */
package de.tivsource.page.dao.message;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.message.Message;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class MessageDao implements MessageDaoLocal {
    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MessageDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#save(de.tivsource.page.entity.message.Message)
     */
    @Override
    public void save(Message message) {
        LOGGER.info("save(Message message) aufgerufen");
        entityManager.persist(message);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#merge(de.tivsource.page.entity.message.Message)
     */
    @Override
    public void merge(Message message) {
        LOGGER.info("merge(Message message) aufgerufen");
        entityManager.merge(message);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#delete(de.tivsource.page.entity.message.Message)
     */
    @Override
    public void delete(Message message) {
        entityManager.remove(entityManager.find(Message.class, message.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Message findByUuid(String uuid) {
        return entityManager.find(Message.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Message m");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findAll(Integer start, Integer max, String field,
            String order) {
        String queryString = "select m from Message m order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.message.MessageDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(m) from Message m");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
