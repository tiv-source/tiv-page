package de.tivsource.page.admin.services;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.common.file.UploadFile;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.course.unit.CourseUnit;
import de.tivsource.page.entity.enumeration.Language;

/**
 * @author Marc Michele
 *
 */
@Service
public class CourseService {

    private static final Logger LOGGER = LogManager.getLogger(CourseService.class);

    // Statische und unveränderliche Map definieren
    private static final Map<String, String> FIELD_MAP;

    // Den Standard-Fallback-Wert festlegen
    private static final String DEFAULT_FIELD = "l.uuid";

    // Statischer Initialisierungsblock für die Map
    static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("uuid", "c.uuid");
        tempMap.put("number", "c.number");
        tempMap.put("startDate", "c.startDate");
        tempMap.put("pictureOnPage", "c.pictureOnPage");
        tempMap.put("name", "dm.name");
        tempMap.put("location", "ldm.name");
        tempMap.put("visible", "c.visible");
        tempMap.put("created", "c.created");
        tempMap.put("modified", "c.modified");
        tempMap.put("modifiedBy", "c.modifiedBy");
        tempMap.put("technical", "c.technical");
        // Macht die Map schreibgeschützt (Thread-safe für parallele Service-Aufrufe)
        FIELD_MAP = Collections.unmodifiableMap(tempMap);
    }

    @Autowired
    private CourseDaoLocal courseDaoLocal;

    public Boolean createCourse(Course course) {
        LOGGER.debug("createCourse(Course course) aufgerufen.");
        if(course != null) {
            String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

            // Setze die nötigen Metadaten on das Kurs-Objekt
            course.setUuid(UUID.randomUUID().toString());
            course.setModified(new Date());
            course.setCreated(new Date());
            course.setModifiedBy(remoteUser);
            course.setModifiedAddress(remoteAddress);
            course.setTechnical("Course_" + course.getUuid());

            // Setze die nötigen Metadaten in die deutsche Beschreibung
            course.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
            course.getDescriptionMap().get(Language.DE).setNamingItem(course);
            course.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
            String noLineBreaks = course.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
            course.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

            // Setze die nötigen Metadaten in den deutschen Inhalt
            course.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
            course.getContentMap().get(Language.DE).setContentItem(course);
            course.getContentMap().get(Language.DE).setLanguage(Language.DE);
            course.getContentMap().get(Language.DE).setCreated(new Date());
            course.getContentMap().get(Language.DE).setModified(new Date());

            // Setze die nötigen Metadaten in die englische Beschreibung
            course.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            course.getDescriptionMap().get(Language.EN).setNamingItem(course);
            course.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
            course.getDescriptionMap().get(Language.EN).setDescription(course.getDescriptionMap().get(Language.DE).getDescription());
            course.getDescriptionMap().get(Language.EN).setName(course.getDescriptionMap().get(Language.DE).getName());
            course.getDescriptionMap().get(Language.EN).setKeywords(course.getDescriptionMap().get(Language.DE).getKeywords());

            // Setze die nötigen Metadaten in den englischen Inhalt
            course.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            course.getContentMap().get(Language.EN).setContentItem(course);
            course.getContentMap().get(Language.EN).setLanguage(Language.EN);
            course.getContentMap().get(Language.EN).setCreated(new Date());
            course.getContentMap().get(Language.EN).setModified(new Date());
            course.getContentMap().get(Language.EN).setContent(course.getContentMap().get(Language.DE).getContent());

            // Setze die nötigen Metadaten in das Bild
            course.getImage().setUuid(UUID.randomUUID().toString());
            course.getImage().generate();
            course.getImage().setCreated(new Date());
            course.getImage().setModified(new Date());
            course.getImage().setModifiedAddress(remoteAddress);
            course.getImage().setModifiedBy(remoteUser);

            courseDaoLocal.saveOrUpdate(course);
            return true;
        }
        // Wenn kein Objekt übergeben wurde
        return false;
    }

    public Boolean updateCourse(Course course, String lang) {
        LOGGER.debug("updateCourse(Course course, String lang) aufgerufen.");
        if(course != null) {
            // Benutzer-Daten aus dem Request holen
            String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

            LOGGER.info("UUID des übergebenen Kurses: " + course.getUuid());
            LOGGER.info("Anzahl der Dozenten im übergebenen Kurse: " + course.getLecturers().size());
            // Laden den vorhanden Kurs aus der Datenbank
            Optional<Course> optionalCourse = courseDaoLocal.findByUuid(course.getUuid());
            if(optionalCourse.isPresent()) {
                Course dbCourse = optionalCourse.get();
                if(lang.contentEquals(new StringBuffer("EN"))) {
                    course.getContentMap().put(Language.DE, dbCourse.getContentObject(Language.DE));
                    dbCourse.getContentMap().get(Language.EN).setContent(course.getContent(Language.EN));
                    dbCourse.getContentMap().get(Language.EN).setModified(new Date());

                    course.getDescriptionMap().put(Language.DE, dbCourse.getDescriptionObject(Language.DE));
                    String noLineBreaks = course.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                    dbCourse.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                    dbCourse.getDescriptionMap().get(Language.EN).setKeywords(course.getKeywords(Language.EN));
                    dbCourse.getDescriptionMap().get(Language.EN).setName(course.getName(Language.EN));
                } else {
                    dbCourse.getContentMap().get(Language.DE).setContent(course.getContent(Language.DE));
                    dbCourse.getContentMap().get(Language.DE).setModified(new Date());

                    String noLineBreaks = course.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                    dbCourse.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                    dbCourse.getDescriptionMap().get(Language.DE).setKeywords(course.getKeywords(Language.DE));;
                    dbCourse.getDescriptionMap().get(Language.DE).setName(course.getName(Language.DE));
                }

                dbCourse.setCourseDocumentDescription(course.getCourseDocumentDescription());
                dbCourse.setPrice(course.getPrice());
                dbCourse.setPriceTax(course.getPriceTax());
                dbCourse.setStartDate(course.getStartDate());
                dbCourse.setMaterial(course.getMaterial());
                dbCourse.setMaterialDocumentDescription(course.getMaterialDocumentDescription());
                dbCourse.setMaterialPrice(course.getMaterialPrice());
                dbCourse.setMaterialTax(course.getMaterialTax());
                dbCourse.setMaxStudents(course.getMaxStudents());
                dbCourse.setMinimumAge(course.getMinimumAge());
                dbCourse.setNumber(course.getNumber());

                dbCourse.setOrderNumber(course.getOrderNumber());
                dbCourse.setModifiedAddress(remoteAddress);
                dbCourse.setModified(new Date());
                dbCourse.setModifiedBy(remoteUser);
                dbCourse.setVisible(course.getVisible());
                dbCourse.setPictureOnPage(course.getPictureOnPage());
                dbCourse.setCssGroup(course.getCssGroup());
                dbCourse.setLecturers(course.getLecturers());
                
                courseDaoLocal.saveOrUpdate(dbCourse);
                return true;
            }
        }
        return false;
    }// Ende updateCourse(Course course, String lang) 

    public Boolean deleteCourse(Course course) {
        LOGGER.debug("deleteCourse(Course course) aufgerufen.");
        if(course != null) {
            Optional<Course> dbCourse = courseDaoLocal.findByUuid(course.getUuid());
            if(dbCourse.isPresent()) {
                courseDaoLocal.delete(dbCourse.get());
                return true;    
            }
        }
        return false;
    }

    public Course getCourseWithAllRelations(String courseUuid) {
        LOGGER.debug("getCourseWithAllRelations(String courseUuid) aufgerufen.");
        if(courseUuid != null) {
            Optional<Course> dbCourse = courseDaoLocal.findByUuidWithAllRelations(courseUuid);
            if(dbCourse.isPresent()) {
                return dbCourse.get();
            }
        }
        return new Course();
    }

    public Course getCourse(String courseUuid) {
        LOGGER.debug("getCourse(String courseUuid) aufgerufen.");
        if(courseUuid != null) {
            Optional<Course> dbCourse = courseDaoLocal.findByUuid(courseUuid);
            if(dbCourse.isPresent()) {
                return dbCourse.get();
            }
        }
        return new Course();
    }

    public Boolean updateCourseImage(Course course) {
        LOGGER.debug("updateCourseImage(Course course) aufgerufen.");
        if(course != null) {
            Optional<Course> optionalCourse = courseDaoLocal.findByUuidWithAllRelations(course.getUuid());
            if(optionalCourse.isPresent()) {
                String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
                String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
                Course dbCourse = optionalCourse.get();
                dbCourse.setModified(new Date());
                dbCourse.setModifiedBy(remoteUser);
                dbCourse.setModifiedAddress(remoteAddress);
                dbCourse.getImage().setUploadFile(course.getImage().getUploadFile());
                dbCourse.getImage().generate();
                dbCourse.getImage().setModified(new Date());
                dbCourse.getImage().setModifiedAddress(remoteAddress);
                dbCourse.getImage().setModifiedBy(remoteUser);
                courseDaoLocal.merge(dbCourse);
                return true;
            }
        }
        return false;
    }

    public Boolean addCourseUnit(String courseUuid, CourseUnit courseUnit) {
        LOGGER.debug("updateCourseImage(Course course) aufgerufen.");
        if(courseUuid != null && courseUnit != null) {
            Optional<Course> optionalCourse = courseDaoLocal.findByUuidWithAllRelations(courseUuid);
            if(optionalCourse.isPresent()) {
                String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
                String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
                Course dbCourse = optionalCourse.get();

                courseUnit.setUuid(UUID.randomUUID().toString());
                courseUnit.setModified(new Date());
                courseUnit.setCreated(new Date());
                courseUnit.setModifiedBy(remoteUser);
                courseUnit.setModifiedAddress(remoteAddress);

                courseUnit.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
                courseUnit.getDescriptionMap().get(Language.DE).setNamingItem(courseUnit);
                courseUnit.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
                String noLineBreaks = courseUnit.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                courseUnit.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

                courseUnit.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
                courseUnit.getContentMap().get(Language.DE).setContentItem(courseUnit);
                courseUnit.getContentMap().get(Language.DE).setLanguage(Language.DE);
                courseUnit.getContentMap().get(Language.DE).setCreated(new Date());
                courseUnit.getContentMap().get(Language.DE).setModified(new Date());

                courseUnit.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
                courseUnit.getDescriptionMap().get(Language.EN).setNamingItem(courseUnit);
                courseUnit.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);
                courseUnit.getDescriptionMap().get(Language.EN).setDescription(courseUnit.getDescriptionMap().get(Language.DE).getDescription());
                courseUnit.getDescriptionMap().get(Language.EN).setName(courseUnit.getDescriptionMap().get(Language.DE).getName());
                courseUnit.getDescriptionMap().get(Language.EN).setKeywords(courseUnit.getDescriptionMap().get(Language.DE).getKeywords());

                courseUnit.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
                courseUnit.getContentMap().get(Language.EN).setContentItem(courseUnit);
                courseUnit.getContentMap().get(Language.EN).setLanguage(Language.EN);
                courseUnit.getContentMap().get(Language.EN).setCreated(new Date());
                courseUnit.getContentMap().get(Language.EN).setModified(new Date());
                courseUnit.getContentMap().get(Language.EN).setContent(courseUnit.getContentMap().get(Language.DE).getContent());

                courseUnit.getImage().setUuid(UUID.randomUUID().toString());
                courseUnit.getImage().generate();
                courseUnit.getImage().setCreated(new Date());
                courseUnit.getImage().setModified(new Date());
                courseUnit.getImage().setModifiedAddress(remoteAddress);
                courseUnit.getImage().setModifiedBy(remoteUser);

                courseUnit.setOrderNumber(1);
                courseUnit.setPictureOnPage(true);
                courseUnit.setVisible(true);
                courseUnit.setTechnical("CourseUnit_" + courseUnit.getUuid());
                courseUnit.setCourse(dbCourse);

                // Setze die Einheit in das SortedSet
                dbCourse.getUnits().add(courseUnit);
                // Hole das Datum der ersten Einheit aus dem SortedSet 
                Date firstUnitDate = dbCourse.getUnits().first().getPointInTime();
                LOGGER.info("Datum der ersten Einheit: " + firstUnitDate);
                // Setze das Startdatum auf das Datum der ersten Einheit
                dbCourse.setStartDate(firstUnitDate);
                courseDaoLocal.merge(dbCourse);
                return true;
            }
        }
        return false;
    }

    public Boolean updateCourseUnit(String courseUuid, CourseUnit courseUnit, String lang) {
        LOGGER.debug("updateCourseImage(Course course) aufgerufen.");
        if(courseUuid != null && courseUnit != null) {
            Optional<Course> optionalCourse = courseDaoLocal.findByUuidWithAllRelations(courseUuid);
            if(optionalCourse.isPresent()) {
                String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
                String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
                Course dbCourse = optionalCourse.get();
                // Durchlaufe alle Einheiten des Kurses
                for(CourseUnit dbCourseUnit : dbCourse.getUnits()) {
                    // Wenn die Einheit der gesuchten entspricht
                    if(dbCourseUnit.getUuid().compareTo(courseUnit.getUuid()) == 0) {
                        if(lang.contentEquals(new StringBuffer("EN"))) {
                            courseUnit.getContentMap().put(Language.DE, dbCourseUnit.getContentObject(Language.DE));
                            dbCourseUnit.getContentMap().get(Language.EN).setContent(courseUnit.getContent(Language.EN));
                            dbCourseUnit.getContentMap().get(Language.EN).setModified(new Date());

                            courseUnit.getDescriptionMap().put(Language.DE, dbCourseUnit.getDescriptionObject(Language.DE));
                            String noLineBreaks = courseUnit.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                            dbCourseUnit.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                            dbCourseUnit.getDescriptionMap().get(Language.EN).setKeywords(courseUnit.getKeywords(Language.EN));
                            dbCourseUnit.getDescriptionMap().get(Language.EN).setName(courseUnit.getName(Language.EN));
                        } else {
                            dbCourseUnit.getContentMap().get(Language.DE).setContent(courseUnit.getContent(Language.DE));
                            dbCourseUnit.getContentMap().get(Language.DE).setModified(new Date());

                            String noLineBreaks = courseUnit.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                            dbCourseUnit.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                            dbCourseUnit.getDescriptionMap().get(Language.DE).setKeywords(courseUnit.getKeywords(Language.DE));;
                            dbCourseUnit.getDescriptionMap().get(Language.DE).setName(courseUnit.getName(Language.DE));
                        }

                        dbCourseUnit.setDuration(courseUnit.getDuration());
                        dbCourseUnit.setPointInTime(courseUnit.getPointInTime());
                        dbCourseUnit.setModifiedAddress(remoteAddress);
                        dbCourseUnit.setModified(new Date());
                        dbCourseUnit.setModifiedBy(remoteUser);
                        dbCourseUnit.setCssGroup(courseUnit.getCssGroup());

                        // Hole das Datum der ersten Einheit aus dem SortedSet 
                        Date firstUnit = dbCourse.getUnits().first().getPointInTime();
                        LOGGER.info("Datum der ersten Einheit: " + firstUnit);
                        // Setze das Startdatum auf das Datum der ersten Einheit
                        dbCourse.setStartDate(firstUnit);

                        courseDaoLocal.merge(dbCourse);
                        return true;
                    }// Ende if
                }// Ende for
            }// Ende if present
        }// Ende if null
        return false;
    }// Ende updateCourseUnit(String courseUuid, CourseUnit courseUnit, String lang) {

    public Boolean deleteCourseUnit(String courseUuid, String courseUnitUuid) {
        LOGGER.debug("deleteCourseUnit(String courseUuid, String courseUnitUuid) aufgerufen.");
        Optional<Course> optionalCourse = courseDaoLocal.findByUuid(courseUuid);
        if(optionalCourse.isPresent()) {
            String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
            Course dbCourse = optionalCourse.get();
            dbCourse.setModifiedBy(remoteUser);
            dbCourse.setModifiedAddress(remoteAddress);
            courseDaoLocal.removeCourseUnit(dbCourse, courseUnitUuid);
            return true;
        }
        return false;
    }

    public Boolean updateCourseUnitImage(String courseUuid, String courseUnitUuid, UploadFile uploadFile) {
        LOGGER.debug("updateCourseUnitImage(String courseUuid, String courseUnit, UploadFile uploadFile) aufgerufen.");
        if(courseUuid != null && courseUnitUuid != null) {
            LOGGER.info("UUID des Course: " + courseUuid);
            Optional<Course> optionalCourse = courseDaoLocal.findByUuidWithAllRelations(courseUuid);
            if(optionalCourse.isPresent()) {
                String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
                String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
                Course dbCourse = optionalCourse.get();
                // Durchlaufe alle Einheiten des Kurses
                for(CourseUnit dbCourseUnit : dbCourse.getUnits()) {
                    // Wenn die Einheit der gesuchten entspricht
                    if(dbCourseUnit.getUuid().compareTo(courseUnitUuid) == 0) {
                        dbCourseUnit.getImage().setUploadFile(uploadFile);
                        dbCourseUnit.getImage().generate();
                        dbCourseUnit.getImage().setModified(new Date());
                        dbCourseUnit.getImage().setModifiedAddress(remoteAddress);
                        dbCourseUnit.getImage().setModifiedBy(remoteUser);
                        dbCourse.setModifiedAddress(remoteAddress);
                        dbCourse.setModified(new Date());
                        dbCourse.setModifiedBy(remoteUser);
                        courseDaoLocal.merge(dbCourse);
                        return true;
                    }// Ende if()
                }// Ende for()
            }// Ende if()
        }// Ende if()
        return false;
    }

    public List<Course> getAllCourses(Integer start, Integer max, String field, String order) {
        LOGGER.debug("getAllCourses(Integer start, Integer max, String field, String order) aufgerufen.");
        return courseDaoLocal.findAll(start, max, getField(field), order);
    }

    public Integer countAllCourses() {
        LOGGER.debug("countAllCourses() aufgerufen.");
        return courseDaoLocal.countAll();
    }

    private String getField(String input) {
        LOGGER.debug("getField(String input) aufgerufen.");
        if (input == null) {
            return DEFAULT_FIELD;
        }
        // getOrDefault macht genau das: Sucht den Key, liefert sonst den Fallback
        return FIELD_MAP.getOrDefault(input, DEFAULT_FIELD);
    }

}// Ende class
