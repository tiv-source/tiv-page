package de.tivsource.page.user.services;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.entity.course.Course;

/**
 * @author Marc Michele
 *
 */
@Service
public class CourseService {

    private static final Logger LOGGER = LogManager.getLogger(CourseService.class);

    @Autowired
    private CourseDaoLocal courseDaoLocal;

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

    public Boolean checkCourseUuid(String courseUuid) {
        if (courseUuid != null && Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", courseUuid)) {
            return courseDaoLocal.isCourseUuid(courseUuid);
        }
        return false;
    }

}// Ende class
