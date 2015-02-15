/**
 * 
 */
package de.tivsource.page.dao.administration;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.tivsource.page.entity.administration.User;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class UserDao implements UserDaoLocal {

    private static final Logger LOGGER_INFO = Logger.getLogger("INFO");
    private static final Logger LOGGER_TRACE = Logger.getLogger("TRACE");

    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#save(de.tivsource.page.entity.administration.User)
     */
    @Override
    public void save(User user) {
        LOGGER_INFO.info("save(User user) aufgerufen");
        entityManager.persist(user);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#merge(de.tivsource.page.entity.administration.User)
     */
    @Override
    public void merge(User user) {
        LOGGER_TRACE.info("merge(User user) aufgerufen");
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

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.UserDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Role r");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
