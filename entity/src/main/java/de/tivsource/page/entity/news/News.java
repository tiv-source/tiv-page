/**
 * 
 */
package de.tivsource.page.entity.news;

import java.util.Date;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;
import org.hibernate.envers.Audited;

import de.tivsource.page.entity.contentitem.ContentItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 * 
 */
@Audited
@Entity
@SecondaryTable(name = News.TABLE)
@Table(appliesTo = News.TABLE, fetch = FetchMode.SELECT)
public class News extends ContentItem {

    public static final String TABLE = "News";

	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	@Column(table = TABLE)
	private Date releaseDate;

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

    /* (non-Javadoc)
     * @see de.tivsource.page.entity.contentitem.ContentItem#getUrl()
     */
    @Override
    public String getUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/");
        stringBuffer.append("news/");
        stringBuffer.append(this.getUuid());
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

