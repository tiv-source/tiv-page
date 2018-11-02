/**
 * 
 */
package de.tivsource.page.dao.administration;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.administration.Role;
import de.tivsource.page.entity.administration.User;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class RoleDao implements RoleDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RoleDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#save(de.tivsource.page.entity.administration.Role)
     */
    @Override
    public void save(Role role) {
        LOGGER.info("save(Role role) aufgerufen");
        entityManager.persist(role);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#merge(de.tivsource.page.entity.administration.Role)
     */
    @Override
    public void merge(Role role) {
        LOGGER.info("merge(Role role) aufgerufen");
        entityManager.merge(role);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#delete(de.tivsource.page.entity.administration.Role)
     */
    @Override
    public void delete(Role role) {
        Role dbRole = entityManager.find(Role.class, role.getUuid());
        Iterator<User> userIterator = dbRole.getUsers().iterator();
        while(userIterator.hasNext()) {
            User next = userIterator.next();
            next.setRoles(null);
            entityManager.merge(next);
        }
        entityManager.remove(entityManager.find(Role.class, role.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Role findByTechnical(String technical) {
        Query query = entityManager.createQuery("select r from Role r where r.technical = :technical");
        query.setParameter("technical", technical);
        return (Role)query.getSingleResult();
    }

    @Override
    public Role findByUuid(String uuid) {
        return entityManager.find(Role.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Role r");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findAll(Integer start, Integer max, String field,
            String order) {
        String queryString = "select r from Role r order by ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Role r");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
