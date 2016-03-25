/**
 * 
 */
package de.tivsource.page.dao.news;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(NewsDao.class);

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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#delete(de.tivsource.page.entity.news.News)
	 */
	@Override
	public void delete(News news) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#isNewsUrl(java.lang.String)
	 */
	@Override
	public Boolean isNewsUrl(String urlName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#findByUuid(java.lang.String)
	 */
	@Override
	public News findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<News> findAll(Integer start, Integer max) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<News> findAll(Integer start, Integer max, String field,
			String order) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.page.dao.news.NewsDaoLocal#countAll()
	 */
	@Override
	public Integer countAll() {
		// TODO Auto-generated method stub
		return null;
	}

}// Ende class
