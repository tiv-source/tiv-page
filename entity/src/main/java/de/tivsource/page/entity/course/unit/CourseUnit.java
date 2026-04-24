package de.tivsource.page.entity.course.unit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.course.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = CourseUnit.TABLE)
public class CourseUnit extends ContentItem {

    public static final String TABLE = "CourseUnit";

    /**
     * Datum an dem die Kurseinheit statt findet.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    @Column(table = TABLE)
    private Date pointInTime;

    /**
     * Länge der Kurseinheit in Minuten.
     */
    @Column(table = TABLE)
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = TABLE, name = "course_uuid")
    private Course course;

    /**
     * @return the pointInTime
     */
    public Date getPointInTime() {
        return pointInTime;
    }

    /**
     * @param pointInTime the pointInTime to set
     */
    public void setPointInTime(Date pointInTime) {
        this.pointInTime = pointInTime;
    }

    /**
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getEndDate() {
        LocalDateTime endDate = pointInTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime newDate = endDate.plusMinutes(duration);
        return java.sql.Timestamp.valueOf(newDate);
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("courseunit/");
        stringBuffer.append(this.getUuid());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#compareTo(de.tivsource.page.entity.contentitem.ContentItem)
     */
    @Override
    public int compareTo(ContentItem o) {
        if(o instanceof CourseUnit) {
            CourseUnit courseUnit = (CourseUnit)o;
            if (courseUnit.getPointInTime().after(this.getPointInTime())) {
                return -1;
            } else if (courseUnit.getPointInTime().before(this.getPointInTime())) {
                return 1;
            } else {
                return o.getUuid().compareTo(this.getUuid());
            }            
        } else {
            return o.compareTo(this);
        }
    }

}// Ende class
