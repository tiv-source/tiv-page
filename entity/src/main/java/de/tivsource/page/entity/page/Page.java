/**
 * 
 */
package de.tivsource.page.entity.page;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;
import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;

/**
 * @author Marc Michele
 * 
 */
@Audited
@Indexed
@Entity
@SecondaryTable(name = Page.TABLE)
@Table(appliesTo = Page.TABLE, fetch = FetchMode.SELECT)
public class Page extends ContentItem {

    public static final String TABLE = "Page";

	@Basic
	@Convert(converter = org.hibernate.type.YesNoConverter.class)
	@Column(table = TABLE)
	private Boolean special;

	public Boolean getSpecial() {
		return special;
	}

	public void setSpecial(Boolean special) {
		this.special = special;
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
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
