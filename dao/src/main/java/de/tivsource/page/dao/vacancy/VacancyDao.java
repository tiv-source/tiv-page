/**
 * 
 */
package de.tivsource.page.dao.vacancy;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.vacancy.Vacancy;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class VacancyDao implements VacancyDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(VacancyDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#save(de.tivsource.page.entity.vacancy.Vacancy)
     */
    @Override
    public void save(Vacancy vacancy) {
        LOGGER.info("save(Vacancy vacancy) aufgerufen");
        entityManager.persist(vacancy);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#merge(de.tivsource.page.entity.vacancy.Vacancy)
     */
    @Override
    public void merge(Vacancy vacancy) {
        LOGGER.info("merge(Vacancy vacancy) aufgerufen");
        entityManager.merge(vacancy);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#delete(de.tivsource.page.entity.vacancy.Vacancy)
     */
    @Override
    public void delete(Vacancy vacancy) {
        entityManager.remove(entityManager.find(Vacancy.class, vacancy.getUuid()));
    }

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#isVacancy(java.lang.String)
	 */
	@Override
	public Boolean isVacancy(String uuid) {
        Query query = entityManager.createQuery("select v from Vacancy v where v.uuid = :uuid and v.visible = :visible order by v.uuid asc");
        query.setParameter("uuid", uuid);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#findByUuid(java.lang.String)
     */
    @Override
    public Vacancy findByUuid(String uuid) {
        return entityManager.find(Vacancy.class, uuid);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Vacancy> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Vacancy v");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#findAllVisible(java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Vacancy> findAllVisible(Integer start, Integer max) {
        String queryString = "SELECT v FROM Vacancy v WHERE v.visible = :visible ORDER BY  v.orderNumber DESC";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Vacancy> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT v FROM Vacancy v JOIN v.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#countAll()
     */
    @Override
    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(v) from Vacancy v");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.dao.vacancy.VacancyDaoLocal#countAllVisible()
     */
    @Override
    public Integer countAllVisible() {
        Query query = entityManager.createQuery("Select Count(v) from Vacancy v WHERE v.visible = :visible");
        query.setParameter("visible", true);
        return Integer.parseInt(query.getSingleResult().toString());
    }

}// Ende class
