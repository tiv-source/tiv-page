package de.tivsource.page.admin.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.entity.lecturer.Lecturer;

/**
 * @author Marc Michele
 *
 */
@Service
public class LecturerService {

    private static final Logger LOGGER = LogManager.getLogger(LecturerService.class);

    @Autowired
    private LecturerDaoLocal lecturerDaoLocal;

    public List<Lecturer> getAllLecturer() {
        LOGGER.debug("getAllLecturer() aufrufen.");
        return lecturerDaoLocal.findAll(0, lecturerDaoLocal.countAll());
    }

}// Ende class
