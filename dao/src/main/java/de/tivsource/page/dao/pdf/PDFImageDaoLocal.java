/**
 * 
 */
package de.tivsource.page.dao.pdf;

import de.tivsource.page.entity.pdf.PDFImage;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PDFImageDaoLocal {

    public PDFImage findByUuid(String uuid);

}// Ende interface
