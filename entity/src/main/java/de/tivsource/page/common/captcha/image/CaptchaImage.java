/**
 * 
 */
package de.tivsource.page.common.captcha.image;

import java.io.Serializable;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.common.image.ImageUntouched;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * @author Marc Michele
 *
 */
@Entity
public class CaptchaImage extends ImageUntouched implements Comparable<ImageUntouched>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3363676443970121844L;

    /**
     * UUID des PictureItemImage Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "captcha_uuid")
    private Captcha captcha;

    public CaptchaImage() {
        super();
        uploadPath = "/srv/tiv-page/captcha/";
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the captcha
     */
    public Captcha getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    public int compareTo(CaptchaImage image) {
        return image.getUuid().compareTo(this.uuid) * -1;
    }

}// Ende class
