/**
 * 
 */
package de.tivsource.page.dao.news;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.news.News;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class NewsDao implements NewsDaoLocal {

    /*
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(NewsDao.class);

    /*
     * EntityManager der Dao Klasse.
     */
    @PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#save(de.tivsource.page.entity.news.News)
	 */
	@Override
	public void save(News news) {
        LOGGER.info("save(News news) aufgerufen");
        entityManager.persist(news);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#merge(de.tivsource.page.entity.news.News)
	 */
	@Override
	public void merge(News news) {
        LOGGER.info("merge(News news) aufgerufen");
        entityManager.merge(news);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#delete(de.tivsource.page.entity.news.News)
	 */
	@Override
	public void delete(News news) {
		entityManager.remove(entityManager.find(News.class, news.getUuid()));
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#isNewsUrl(java.lang.String)
	 */
	@Override
	public Boolean isNewsUrl(String uuid) {
        Query query = entityManager.createQuery("select n from News n where n.uuid = ?1 and p.visible = 'Y' order by p.uuid asc");
        query.setParameter("1", uuid);
        return (query.getResultList().size() > 0 ? true : false);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public News findByUuid(String uuid) {
		return entityManager.find(News.class, uuid);
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<News> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from News n");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<News> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT n FROM News n JOIN n.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(n) from News n");
        return Integer.parseInt(query.getSingleResult().toString());
	}

}// Ende class
