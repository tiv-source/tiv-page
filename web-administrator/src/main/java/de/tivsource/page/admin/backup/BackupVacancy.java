package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.vacancy.VacancyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.vacancy.Vacancy;

public class BackupVacancy {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupVacancy.class);

	private static VacancyDaoLocal vacancyDaoLocal;

	private static final int max = 1500;

	private List<File> backupFiles = new ArrayList<File>();

	public static void setVacancyDaoLocal(VacancyDaoLocal vacancyDaoLocal) {
        BackupVacancy.vacancyDaoLocal = vacancyDaoLocal;
    }

    public List<File> getBackupFiles() throws IOException {
	    LOGGER.info("getBackupFiles() aufgerufen.");

		// Datei Kram
		File file = new File("/tmp/vacancy.csv");
    	FileWriter fileWriter = new FileWriter(file);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    	// Format Definition 
    	bufferedWriter.write("[Format Definition] => uuid|uuid(de)|name(de)|description(de)|keywords(de)|content_uuid(de)|content(de)|content_created(de)|content_modified(de)|uuid(en)|name(en)|description(en)|keywords(en)|content_uuid(en)|content(en)|content_created(en)|content_modified(en)|visible|created|modified|modifiedBy|modifiedAddress|technical|beginning|workingTime|location|picture|");

    	Iterator<Vacancy> vacancyIterator = vacancyDaoLocal.findAll(0, max).iterator();
    	while(vacancyIterator.hasNext()) {
    	    Vacancy next = vacancyIterator.next();
    		bufferedWriter.write("\n");
    		bufferedWriter.write(convertToCsvLine(next));
    		generateContentFile(next, Language.DE);
    		generateContentFile(next, Language.EN);
    	}

    	bufferedWriter.close();
    	fileWriter.close();
    	backupFiles.add(file);

		return backupFiles;
	}

	private String convertToCsvLine(Vacancy next) {
	    LOGGER.info("convertToCsvLine(Page next) aufgerufen.");

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|content_uuid(de)|content(de)|content_created(de)|content_modified(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|content_uuid(en)|content(en)|content_created(en)|content_modified(en)|
	    // visible|created|modified|modifiedBy|modifiedAddress|technical|beginning|workingTime|location|picture|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getUuid());
		nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getUuid());
        nextString.append("|");

		nextString.append(next.getDescriptionObject(Language.DE).getName());
		nextString.append("|");

		nextString.append(next.getDescriptionObject(Language.DE).getDescription());
		nextString.append("|");

		nextString.append(next.getDescriptionObject(Language.DE).getKeywords());
		nextString.append("|");

        nextString.append(next.getContentObject(Language.DE).getUuid());
        nextString.append("|");

		nextString.append("vacancy_" + next.getUuid() + "_DE.txt");
		nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getContentObject(Language.DE).getCreated()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getContentObject(Language.DE).getModified()));
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescriptionObject(Language.EN).getName());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getDescription());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getKeywords());
        nextString.append("|");

        nextString.append(next.getContentObject(Language.EN).getUuid());
        nextString.append("|");

        nextString.append("vacancy_" + next.getUuid() + "_EN.txt");
        nextString.append("|");


        nextString.append(simpleDateFormat.format(next.getContentObject(Language.EN).getCreated()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getContentObject(Language.EN).getModified()));
        nextString.append("|");

		nextString.append(next.getVisible().toString());
		nextString.append("|");
		
		
		nextString.append(simpleDateFormat.format(next.getCreated()));
		nextString.append("|");

		nextString.append(simpleDateFormat.format(next.getModified()));
		nextString.append("|");

		nextString.append(next.getModifiedBy());
		nextString.append("|");

		nextString.append(next.getModifiedAddress());
		nextString.append("|");

        nextString.append(next.getTechnical());
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getBeginning()));
        nextString.append("|");

        nextString.append(next.getWorkingTime());
        nextString.append("|");

        nextString.append(next.getLocation().getUuid());
        nextString.append("|");

        nextString.append(next.getPicture().getUuid());
        nextString.append("|");

		return nextString.toString();
	}

	private void generateContentFile(Vacancy vacancy, Language language) throws IOException {
		File file = new File("/tmp/vacancy_" + vacancy.getUuid() + "_" + language.toString() +".txt");
		FileWriter fileWriter = new FileWriter(file);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    	bufferedWriter.write(vacancy.getContent(language));
    	bufferedWriter.close();
    	fileWriter.close();
    	backupFiles.add(file);
	}

}// Ende class
