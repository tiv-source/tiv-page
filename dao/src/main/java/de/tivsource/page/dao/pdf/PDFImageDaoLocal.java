/**
 * 
 */
package de.tivsource.page.dao.pdf;

import javax.ejb.Local;

import de.tivsource.page.entity.pdf.PDFImage;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PDFImageDaoLocal {

    public PDFImage findByUuid(String uuid);

}// Ende interface
