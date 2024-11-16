/**
 * 
 */
package de.tivsource.page.dao.exhibition;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.entity.exhibition.Exhibition;
import de.tivsource.page.entity.property.Property;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Marc Michele
 *
 */
@Stateless
public class ExhibitionDao implements ExhibitionDaoLocal {

    private static final Logger LOGGER = LogManager.getLogger(ExhibitionDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    public ExhibitionDao() {
        super();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.exhibition.ExhibitionDaoLocal#save(de.czastka.entity.exhibition.Exhibition)
     */
    public void save(Exhibition exhibition) {
        LOGGER.info("save(Exhibition exhibition) aufgerufen");
        entityManager.persist(exhibition);
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.exhibition.ExhibitionDaoLocal#merge(de.czastka.entity.exhibition.Exhibition)
     */
    public void merge(Exhibition exhibition) {
        exhibition.setModified(new Date());
        entityManager.merge(exhibition);
    }

    /* (non-Javadoc)
     * @see de.czastka.dao.exhibition.ExhibitionDaoLocal#delete(de.czastka.entity.exhibition.Exhibition)
     */
    public void delete(Exhibition exhibition) {
        entityManager.remove(entityManager.find(Exhibition.class, exhibition.getUuid()));
    }

    @Override
    public Boolean isExhibitionTechnical(String technical) {
        LOGGER.info("isExhibitionTechnical(String technical) aufgerufen");
        Query query = entityManager.createQuery(
                "select exh from Exhibition exh "
                + "where exh.technical = :technical and exh.visible = :visible order by exh.uuid asc"
        );
        query.setParameter("technical", technical);
        query.setParameter("visible", true);
        return (query.getResultList().size() > 0 ? true : false);
    }

    @Override
    public Exhibition findByTechnical(String technical) {
        Query query = entityManager.createQuery(
                "select exh from Exhibition exh where exh.technical = :technical and exh.visible = :visible"
        );
        query.setParameter("visible", true);
        query.setParameter("technical", technical);
        Exhibition dbExhibition = (Exhibition) query.getSingleResult();
        // LOGGER.info("PictureMap Size: " + dbExhibition.getPictureMap().size());
        return dbExhibition;
    }

    @Override
    public Exhibition findByUuid(String uuid) {
        return entityManager.find(Exhibition.class, uuid);
    }

    @SuppressWarnings("unchecked")
    @RolesAllowed(value={"administrator"})
    public List<Exhibition> findAllAsc(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Exhibition e order by e.uuid asc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Exhibition> findAll(Integer start, Integer max) {
        Query query = entityManager.createQuery("from Exhibition e order by e.moment desc");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Exhibition> findAll(Integer start, Integer max, String field, String order) {
        String queryString = "SELECT DISTINCT e FROM Exhibition e JOIN e.descriptionMap dm WHERE dm.language = 'DE' ORDER BY ";
        queryString = queryString + field + " " + order;
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Exhibition> findAllVisible(Integer start, Integer max) {
        String queryString = "SELECT ex FROM Exhibition ex WHERE ex.end > :date AND ex.visible = :visible AND ex.visibleFrom < :date ORDER BY ex.start asc";
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(max);
        query.setParameter("visible", true);
        query.setParameter("date", Date.from(Instant.now().minusSeconds(86400)));
        return query.getResultList();
    }

    @Override
    public Integer pageByTechnical(String technical) {
        Query query = entityManager.createQuery("select exh from Exhibition exh where exh.technical = :technical");
        query.setParameter("technical", technical);
        Exhibition exhibition = (Exhibition)query.getSingleResult();

        Query querySize = entityManager.createQuery("select exh from Exhibition exh where exh.moment >= :moment order by exh.moment desc");
        querySize.setParameter("moment", exhibition.getMoment());

        int max = Integer.parseInt(entityManager.find(Property.class, "exhibition.on.page").getValue());
        int qSize = querySize.getResultList().size();

        LOGGER.info("Exhibition Anzahl: " + qSize);

        return (qSize % max == 0) ? qSize/max : (qSize/max)+1 ;
    }

    public Integer countAll() {
        Query query = entityManager.createQuery("Select Count(ex) from Exhibition ex");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer countAllVisible() {
        LOGGER.info("countAllVisible() aufgerufen");
        Query query = entityManager.createQuery("Select Count(ex) from Exhibition ex WHERE ex.end > :date AND ex.visibleFrom < :date AND ex.visible = :visible");
        query.setParameter("date", new Date());
        query.setParameter("visible", true);
        String result = query.getSingleResult().toString();
        LOGGER.info("Anzahl der sichtbaren Exhibition Objekte in der Datenbank: " + result);
        return Integer.parseInt(result);
    }

}// Ende class
