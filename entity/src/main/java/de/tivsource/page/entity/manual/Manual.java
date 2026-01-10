/**
 * 
 */
package de.tivsource.page.entity.manual;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@SecondaryTable(name = Manual.TABLE)
public class Manual extends ContentItem {

    public static final String TABLE = "Manual";

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("manual/");
        stringBuffer.append(this.getTechnical());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

    @Override
    public int compareTo(ContentItem o) {
        if (o.getCreated().after(this.getCreated())) {
            return 1;
        } else if (o.getCreated().before(this.getCreated())) {
            return -1;
        } else {
            return o.getUuid().compareTo(this.getUuid());
        }
    }

}// Ende class
