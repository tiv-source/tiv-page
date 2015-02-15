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

import de.tivsource.page.entity.administration.Role;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class RoleDao implements RoleDaoLocal {

    private static final Logger LOGGER_INFO = Logger.getLogger("INFO");
    private static final Logger LOGGER_TRACE = Logger.getLogger("TRACE");

    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#save(de.tivsource.page.entity.administration.Role)
     */
    @Override
    public void save(Role role) {
        LOGGER_INFO.info("save(Role role) aufgerufen");
        entityManager.persist(role);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#merge(de.tivsource.page.entity.administration.Role)
     */
    @Override
    public void merge(Role role) {
        LOGGER_TRACE.info("merge(Role role) aufgerufen");
        entityManager.merge(role);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#delete(de.tivsource.page.entity.administration.Role)
     */
    @Override
    public void delete(Role role) {
        entityManager.remove(entityManager.find(Role.class, role.getUuid()));
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#findByTechnical(java.lang.String)
     */
    @Override
    public Role findByTechnical(String technical) {
        Query query = entityManager.createQuery("select r from Role r where r.technical = ?1");
        query.setParameter("1", technical);
        return (Role)query.getSingleResult();
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

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.administration.RoleDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Role r");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
