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
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
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

}// Ende class
