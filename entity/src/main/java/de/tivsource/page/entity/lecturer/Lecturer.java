/**
 * 
 */
package de.tivsource.page.entity.lecturer;

import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.course.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = Lecturer.TABLE)
public class Lecturer extends ContentItem {

    public static final String TABLE = "Lecturer";

    @Column(table = TABLE)
    private String firstname;

    @Column(table = TABLE)
    private String lastname;

    @Column(table = TABLE)
    private String mailaddress;

    @Column(table = TABLE)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "Lecturer_Course",
        joinColumns = @JoinColumn(name = "lecturer_uuid"),
        inverseJoinColumns = @JoinColumn(name = "course_uuid")
    )
    @SortNatural
    private SortedSet<Course> courses = new TreeSet<Course>();

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the mailaddress
     */
    public String getMailaddress() {
        return mailaddress;
    }

    /**
     * @param mailaddress the mailaddress to set
     */
    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    /**
     * @return the phonennumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phonennumber the phonennumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the courses
     */
    public SortedSet<Course> getCourses() {
        return courses;
    }

    /**
     * @param courses the courses to set
     */
    public void setCourses(SortedSet<Course> courses) {
        this.courses = courses;
    }

    // Helper-Methode zum Hinzufügen eines Kurses
    public void addCourse(Course course) {
        if (course == null) {
            return;
        }
        
        // 1. Initialisiere das SortedSet mit einem TreeSet, falls es null sein sollte
        if (this.courses == null) {
            this.courses = new TreeSet<>();
        }
        
        // 2. Füge den Kurs hinzu. Wenn er noch nicht drin war, pflege die Rückbeziehung
        if (this.courses.add(course)) {
            // 3. WICHTIG: Auf der Gegenseite (Course) ebenfalls im Set registrieren
            if (course.getLecturers() == null) {
                course.setLecturers(new TreeSet<>());
            }
            course.getLecturers().add(this);
        }
    }

    // Helper-Methode zum Entfernen eines Kurses
    public void removeCourse(Course course) {
        if (course == null || this.courses == null) {
            return;
        }
        
        // 1. Aus der eigenen Collection (Owner-Seite) entfernen
        if (this.courses.remove(course)) {
            // 2. WICHTIG: Aus der Collection der Gegenseite entfernen
            if (course.getLecturers() != null) {
                course.getLecturers().remove(this);
            }
        }
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("lecturer/");
        stringBuffer.append(this.getTechnical());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

    @Override
    public int compareTo(ContentItem o) {
        if (this == o) return 0;
        
        if (o instanceof Lecturer) {
            Lecturer lecturer = (Lecturer) o;
            
            // Nach Nachname vergleichen (this vs. lecturer)
            int lastNameCompare = this.lastname.compareTo(lecturer.getLastname());
            if (lastNameCompare != 0) {
                return lastNameCompare;
            }
            
            // Nach Vorname vergleichen (this vs. lecturer)
            int firstNameCompare = this.firstname.compareTo(lecturer.getFirstname());
            if (firstNameCompare != 0) {
                return firstNameCompare;
            }
            
            // Nach UUID vergleichen (this vs. o)
            if (this.getUuid() != null && o.getUuid() != null) {
                return this.getUuid().compareTo(o.getUuid());
            }
        }
        
        // Fallback auf den Basis-Vergleich, falls es ein anderes ContentItem ist
        return (this.getUuid() != null && o.getUuid() != null) 
                ? this.getUuid().compareTo(o.getUuid()) 
                : 0;
    }


}// Ende class
