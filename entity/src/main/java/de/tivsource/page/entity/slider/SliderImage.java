/**
 * 
 */
package de.tivsource.page.entity.slider;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.common.image.ImageUntouched;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class SliderImage extends ImageUntouched implements Comparable<ImageUntouched>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 734687924779098003L;

    /**
     * UUID des SliderImage Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slider_uuid")
    private Slider slider;

    public SliderImage() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public int compareTo(SliderImage image) {
        return image.getUuid().compareTo(this.uuid) * -1;
    }

}// Ende class
