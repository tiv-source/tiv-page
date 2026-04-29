package de.tivsource.page.entity.material;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.course.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = Course.TABLE)
public class Material extends ContentItem {

    public static final String TABLE = "Material";

    @Column(table = TABLE)
    private String amazonUrl;

    /**
     * @return the amazonUrl
     */
    public String getAmazonUrl() {
        return amazonUrl;
    }

    /**
     * @param amazonUrl the amazonUrl to set
     */
    public void setAmazonUrl(String amazonUrl) {
        this.amazonUrl = amazonUrl;
    }

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("material/");
        stringBuffer.append(this.getUuid());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

}// Ende class
