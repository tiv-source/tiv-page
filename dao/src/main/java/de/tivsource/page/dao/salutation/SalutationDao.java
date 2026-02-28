/**
 * 
 */
package de.tivsource.page.dao.salutation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.salutation.Salutation;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 */
@Stateless
public class SalutationDao implements SalutationDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(SalutationDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#save(dev.systemisch.protrust.common.salutation.Salutation)
	 */
	@Override
	public void save(Salutation salutation) {
        LOGGER.info("save(Salutation salutation) aufgerufen");
        entityManager.persist(salutation);
	}

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#merge(dev.systemisch.protrust.common.salutation.Salutation)
	 */
	@Override
	public void merge(Salutation salutation) {
        LOGGER.info("merge(Salutation salutation) aufgerufen");
        entityManager.merge(salutation);
	}

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#delete(dev.systemisch.protrust.common.salutation.Salutation)
	 */
	@Override
	public void delete(Salutation salutation) {
		entityManager.remove(entityManager.find(Salutation.class, salutation.getUuid()));
	}

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public Salutation findByUuid(String uuid) {
		return entityManager.find(Salutation.class, uuid);
	}

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Salutation> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Salutation s");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Salutation> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT s FROM Salutation s JOIN s.descriptionMap dm WHERE dm.locale = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see dev.systemisch.protrust.dao.salutation.SalutationDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(s) from Salutation s");
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class
