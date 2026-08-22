/**
 * 
 */
package de.tivsource.page.entity.course;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.course.unit.CourseUnit;
import de.tivsource.page.entity.lecturer.Lecturer;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.registration.Registration;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = Course.TABLE)
public class Course extends ContentItem {

    public static final String TABLE = "Course";

    /**
     * Statischer Logger der Klasse Course für die Ausgabe mit log4j.
     */
    private static final Logger LOGGER = LogManager.getLogger(Course.class);

    /**
     * Nummer des Kurses
     */
    @Column(table = TABLE, unique = true)
    private String number;

    /**
     * Datum an dem der Kurs das erste mal statt findet.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    @Column(table = TABLE)
    private Date startDate;

    /**
     * Mindestalter
     */
    @Column(table = TABLE)
    private Integer minimumAge;

    /**
     * Einheiten/Stunden an denen der Kurs statt findet.
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @SortNatural
    private SortedSet<CourseUnit> units = new TreeSet<CourseUnit>();

    /**
     * Dozenten die den Kurs geben. 
     */
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private SortedSet<Lecturer> lecturers;

    /**
     * Anmeldungen die zu dem Kurs gehören.
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @SortNatural
    private SortedSet<Registration> registrations = new TreeSet<Registration>();

    /**
     * Beschreibung des Kurses die in PDF-Dokumenten benutzt wird.
     */
    @Column(table = TABLE)
    private String courseDocumentDescription;

    @Column(table = TABLE)
    private BigDecimal price;

    @Column(table = TABLE)
    private BigDecimal priceTax;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean material;
    
    @Column(table = TABLE)
    private String materialDocumentDescription;

    @Column(table = TABLE)
    private BigDecimal materialPrice;

    @Column(table = TABLE)
    private BigDecimal materialTax;

    @Column(table = TABLE)
    private Integer maxStudents;

    /**
     * Location in der der Kurs stattfindet.
     */
    @ManyToOne
    @JoinColumn(table = TABLE, name = "location_uuid")
    private Location location;

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the minimumAge
     */
    public Integer getMinimumAge() {
        return minimumAge;
    }

    /**
     * @param minimumAge the minimumAge to set
     */
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    /**
     * @return the units
     */
    public SortedSet<CourseUnit> getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public void setUnits(SortedSet<CourseUnit> units) {
        this.units = units;
    }

    /**
     * @return the lecturers
     */
    public SortedSet<Lecturer> getLecturers() {
        return lecturers;
    }

    /**
     * @param lecturers the lecturers to set
     */
    public void setLecturers(SortedSet<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    /**
     * @return the registrations
     */
    public SortedSet<Registration> getRegistrations() {
        return registrations;
    }

    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(SortedSet<Registration> registrations) {
        this.registrations = registrations;
    }

    /**
     * @return the courseDocumentDescription
     */
    public String getCourseDocumentDescription() {
        return courseDocumentDescription;
    }

    /**
     * @param courseDocumentDescription the courseDocumentDescription to set
     */
    public void setCourseDocumentDescription(String courseDocumentDescription) {
        this.courseDocumentDescription = courseDocumentDescription;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the priceTax
     */
    public BigDecimal getPriceTax() {
        return priceTax;
    }

    /**
     * @param priceTax the priceTax to set
     */
    public void setPriceTax(BigDecimal priceTax) {
        this.priceTax = priceTax;
    }

    /**
     * @return the material
     */
    public Boolean getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Boolean material) {
        this.material = material;
    }

    /**
     * @return the materialDocumentDescription
     */
    public String getMaterialDocumentDescription() {
        return materialDocumentDescription;
    }

    /**
     * @param materialDocumentDescription the materialDocumentDescription to set
     */
    public void setMaterialDocumentDescription(String materialDocumentDescription) {
        this.materialDocumentDescription = materialDocumentDescription;
    }

    /**
     * @return the materialPrice
     */
    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    /**
     * @param materialPrice the materialPrice to set
     */
    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    /**
     * @return the materialTax
     */
    public BigDecimal getMaterialTax() {
        return materialTax;
    }

    /**
     * @param materialTax the materialTax to set
     */
    public void setMaterialTax(BigDecimal materialTax) {
        this.materialTax = materialTax;
    }

    /**
     * @return the maxStudents
     */
    public Integer getMaxStudents() {
        return maxStudents;
    }

    /**
     * @param maxStudents the maxStudents to set
     */
    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    // Helper-Methode zum Hinzufügen
   public void addLecturer(Lecturer lecturer) {
       if (lecturer == null) {
           return;
       }
       
       // Initialisiere das SortedSet, falls es noch null ist
       if (this.lecturers == null) {
           this.lecturers = new TreeSet<>(); // Wichtig: TreeSet für SortedSet verwenden!
       }
       
       // Dopple-Check: Nur hinzufügen, wenn noch nicht vorhanden (verhindert Endlosschleifen)
       if (this.lecturers.add(lecturer)) {
           // WICHTIG: Die Rückbeziehung auf der Owner-Seite (Lecturer) setzen
           lecturer.getCourses().add(this);
       }
   }

   // Helper-Methode zum Entfernen
   public void removeLecturer(Lecturer lecturer) {
       if (lecturer == null || this.lecturers == null) {
           return;
       }
       
       // 1. Aus der eigenen Collection entfernen
       if (this.lecturers.remove(lecturer)) {
           // 2. WICHTIG: Aus der Collection der Gegenseite entfernen
           lecturer.getCourses().remove(this);
       }
   }
   
    
    /**
     * Methode die true liefert wenn der Kurs in der Vergangenheit liegt
     * und false wenn der Kurs in der Zukunft liegt.
     *
     * @return true - Wenn der Kurs in der Vergangenhiet liegt.
     */
    public Boolean isInPast() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime check = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if(now.isAfter(check)) {
            return true;
        }
        return false;
    }

    public String status() {
        LOGGER.debug("Anzahl aller Registrierungen: " + registrations.size());
        List<Registration> filteredList = new ArrayList<Registration>();
        List<String> statusFilter = Arrays.asList("PREDECLARED", "DECLARED", "CONFIRMED", "PAID", "WAITING");
        for (Registration registration : registrations) {
            for (String status : statusFilter) {
                if (registration.getStatus().name().equals(status)) {
                    filteredList.add(registration);
                }
            }
        }
        // Wenn es weniger Teilnehmer als 50% Auslastung gibt 
        if(maxStudents / 2 >= filteredList.size()) {
            return "GREEN";
        }
        // Wenn es mehr Teilnehmer als 50% Auslastung sind aber weniger als 100% 
        else if(maxStudents / 2 < filteredList.size() && maxStudents > filteredList.size()) {
            return "ORANGE";
        }
        // In allen anderen Fällen
        else {
            return "RED";
        }
    }// Ende status()

    public BigDecimal sum() {
        if(material) {
            return price.add(materialPrice);
        }
        return price;
    }// Ende sum()

    public Boolean getConvertToDeclard() {
        List<Registration> filteredList = new ArrayList<Registration>();
        List<String> statusFilter = Arrays.asList("DECLARED", "CONFIRMED", "PAID");
        for (Registration registration : registrations) {
            for (String status : statusFilter) {
                if (registration.getStatus().name().equals(status)) {
                    filteredList.add(registration);
                }
            }
        }
        Integer result = maxStudents -  filteredList.size();
        LOGGER.debug("Freie Plätze: " + result);
        if(result > 0) {
            return true;
        }
        return false;
    }// Ende getConvertToDeclard()

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("course/");
        stringBuffer.append(this.getUuid());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }// Ende getUrl()

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#compareTo(de.tivsource.page.entity.contentitem.ContentItem)
     */
    @Override
    public int compareTo(ContentItem o) {
        if (this == o) return 0;
        Course course = (Course) o;
        
        if (this.startDate != null && course.getStartDate() != null) {
            if (this.startDate.before(course.getStartDate())) {
                return -1;
            } else if (this.startDate.after(course.getStartDate())) {
                return 1;
            }
        }
        
        // UUID-Vergleich korrigieren: Immer von "this" zu "o" vergleichen!
        if (this.getUuid() != null && o.getUuid() != null) {
            return this.getUuid().compareTo(o.getUuid());
        }
        
        return 0;
    }// Ende compareTo(ContentItem o)

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.pictureitem.PictureItem#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        // Selbst Test
        if (this == o) return true;
        // NULL Test
        if (o == null) return false;
        // type check and cast
        if (getClass() != o.getClass()) return false;
        Course course = (Course) o;
        // field comparison
        return Objects.equals(getUuid(), course.getUuid());
    }// Ende equals(Object o)

}// Ende class
