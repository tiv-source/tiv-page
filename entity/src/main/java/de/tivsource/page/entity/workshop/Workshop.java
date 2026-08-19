/**
 * 
 */
package de.tivsource.page.entity.workshop;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 *
 */
@SecondaryTable(name = Workshop.TABLE)
public class Workshop extends ContentItem {

    public static final String TABLE = "Workshop";

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("workshop/");
        stringBuffer.append(this.getTechnical());
        stringBuffer.append("/");
        stringBuffer.append("index.html");
        return stringBuffer.toString();
    }

}// Ende class
