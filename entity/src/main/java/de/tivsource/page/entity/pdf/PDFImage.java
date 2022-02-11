/**
 * 
 */
package de.tivsource.page.entity.pdf;

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
public class PDFImage extends ImageUntouched implements Comparable<ImageUntouched>, Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6452959474421694520L;

    /**
     * UUID des PictureItemImage Objektes.
     */
    @Id
    @DocumentId
    @Column(name = "uuid", unique = true, length=42)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_uuid")
    private PDF pdf;

    public PDFImage() {
        super();
        uploadPath = "/srv/tiv-page/pdf-image/";
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

    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }

    public int compareTo(PDFImage image) {
        return image.getUuid().compareTo(this.uuid) * -1;
    }

}// Ende class
