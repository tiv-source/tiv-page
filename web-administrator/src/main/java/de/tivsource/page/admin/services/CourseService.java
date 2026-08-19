/**
 * 
 */
package de.tivsource.page.admin.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.dao.course.CourseDaoLocal;

/**
 * @author Marc Michele
 *
 */
@Service
public class CourseService {

    private static final Logger LOGGER = LogManager.getLogger(PropertyService.class);

    @Autowired
    private CourseDaoLocal courseDaoLocal;

}// Ende class
