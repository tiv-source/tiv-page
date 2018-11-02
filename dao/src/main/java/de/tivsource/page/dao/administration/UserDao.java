/**
 * 
 */
package de.tivsource.page.dao.administration;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.administration.User;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class UserDao implements UserDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#save(de.tivsource.page.entity.administration.User)
     */
    @Override
    public void save(User user) {
        LOGGER.info("save(User user) aufgerufen");
        entityManager.persist(user);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#merge(de.tivsource.page.entity.administration.User)
     */
    @Override
    public void merge(User user) {
        LOGGER.info("merge(User user) aufgerufen");
        entityManager.merge(user);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#delete(de.tivsource.page.entity.administration.User)
     */
    @Override
    public void delete(User user) {
        entityManager.remove(entityManager.find(User.class, user.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#findByUsername(java.lang.String)
     */
    @Override
    public User findByUsername(String username) {
        LOGGER.info("findByUsername(String username) aufgerufen.");
        try {
            Query query = entityManager.createQuery("select u from User u where u.username = :username");
            query.setParameter("username", username);
            return (User)query.getSingleResult();
        } catch(NoResultException e) {
        	return null;
        }
    }

    @Override
    public User findByUuid(String uuid) {
        return entityManager.find(User.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from User u");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(Integer start, Integer max, String field,
            String order) {
        String queryString = "select u from User u order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(u) from User u");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
