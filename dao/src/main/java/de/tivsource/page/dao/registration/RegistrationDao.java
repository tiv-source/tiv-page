/**
 * 
 */
package de.tivsource.page.dao.registration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.registration.Registration;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class RegistrationDao implements RegistrationDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RegistrationDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#save(de.tivsource.page.entity.registration.Registration)
	 */
	@Override
	public void save(Registration registration) {
        LOGGER.info("save(Registration registration) aufgerufen");
        entityManager.persist(registration);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#merge(de.tivsource.page.entity.registration.Registration)
	 */
	@Override
	public void merge(Registration registration) {
        LOGGER.info("merge(Registration registration) aufgerufen");
        entityManager.merge(registration);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#delete(de.tivsource.page.entity.registration.Registration)
	 */
	@Override
	public void delete(Registration registration) {
		entityManager.remove(entityManager.find(Registration.class, registration.getUuid()));
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public Registration findByUuid(String uuid) {
	    Registration registration = entityManager.find(Registration.class, uuid);
	    Hibernate.initialize(registration.getCourse());
	    Hibernate.initialize(registration.getCourse().getImage());
	    Hibernate.initialize(registration.getCourse().getDescriptionMap());
	    Hibernate.initialize(registration.getSalutation());
	    Hibernate.initialize(registration.getSalutation().getDescriptionMap());
        return registration;
	}

    @Override
    public Registration findUserToken(String userToken) {
        Query query = entityManager.createQuery("SELECT r FROM Registration r WHERE r.userToken = :userToken");
        query.setParameter("userToken", userToken);
        try {
            Registration registration = (Registration)query.getSingleResult();
            Hibernate.initialize(registration.getCourse());
            Hibernate.initialize(registration.getCourse().getImage());
            Hibernate.initialize(registration.getCourse().getDescriptionMap());
            Hibernate.initialize(registration.getSalutation());
            Hibernate.initialize(registration.getSalutation().getDescriptionMap());
            return registration;
        } catch (Exception exception) {
            return null;
        }
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Registration> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Registration r");
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<Registration> registrationList = query.getResultList();
        for (Registration registration : registrationList) {
            Hibernate.initialize(registration.getSalutation());
            Hibernate.initialize(registration.getSalutation().getDescriptionMap());
            Hibernate.initialize(registration.getCourse());
            Hibernate.initialize(registration.getCourse().getImage());
            Hibernate.initialize(registration.getCourse().getDescriptionMap());
            Hibernate.initialize(registration.getCourse().getRegistrations());
        }
        return registrationList;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Registration> findAll(RegistrationStatus status, Integer start,
            Integer max) {
        Query query = entityManager.createQuery("SELECT r FROM Registration r WHERE r.status = :status");
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("status", status);
        List<Registration> registrationList = query.getResultList();
        for (Registration registration : registrationList) {
            Hibernate.initialize(registration.getSalutation());
            Hibernate.initialize(registration.getSalutation().getDescriptionMap());
            Hibernate.initialize(registration.getCourse());
            Hibernate.initialize(registration.getCourse().getImage());
            Hibernate.initialize(registration.getCourse().getDescriptionMap());
            Hibernate.initialize(registration.getCourse().getRegistrations());
        }
        return registrationList;
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Registration> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT r FROM Registration r "
                + "JOIN r.course rc "
                + "JOIN rc.descriptionMap rcdm "
                + "JOIN r.salutation rs "
                + "JOIN rs.descriptionMap rsdm "
                + "WHERE rcdm.language = 'DE' AND rsdm.locale = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<Registration> registrationList = query.getResultList();
        for (Registration registration : registrationList) {
            Hibernate.initialize(registration.getSalutation());
            Hibernate.initialize(registration.getSalutation().getDescriptionMap());
            Hibernate.initialize(registration.getCourse());
            Hibernate.initialize(registration.getCourse().getImage());
            Hibernate.initialize(registration.getCourse().getDescriptionMap());
            Hibernate.initialize(registration.getCourse().getRegistrations());
        }
        return registrationList;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Registration> findAll(RegistrationStatus status, Integer start,
            Integer max, String field, String order) {
        String queryString = "SELECT r FROM Registration r "
                + "JOIN r.course rc "
                + "JOIN rc.descriptionMap rcdm "
                + "JOIN r.salutation rs "
                + "JOIN rs.descriptionMap rsdm "
                + "WHERE r.status = :status AND "
                + "rcdm.language = 'DE' AND "
                + "rsdm.locale = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("status", status);
        List<Registration> registrationList = query.getResultList();
        for (Registration registration : registrationList) {
            Hibernate.initialize(registration.getSalutation());
            Hibernate.initialize(registration.getSalutation().getDescriptionMap());
            Hibernate.initialize(registration.getCourse());
            Hibernate.initialize(registration.getCourse().getImage());
            Hibernate.initialize(registration.getCourse().getDescriptionMap());
            Hibernate.initialize(registration.getCourse().getRegistrations());
        }
        return registrationList;
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.registration.RegistrationDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(r) from Registration r");
        return Integer.parseInt(query.getSingleResult().toString());
	}

    @Override
    public Integer countAll(RegistrationStatus status) {
        Query query = entityManager.createQuery("Select Count(r) from Registration r WHERE r.status = :status");
        query.setParameter("status", status);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
