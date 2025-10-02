/**
 * 
 */
package de.tivsource.page.dao.pdf;

import java.util.List;

import de.tivsource.page.entity.enumeration.PDFType;
import de.tivsource.page.entity.pdf.PDF;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PDFDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse PDF.
     * @param pdf - PDF Objekt das verändert werden soll
     */
    public void merge(PDF pdf);

    /**
     * Methode zum löschen eines Objektes der Klasse PDF.
     * @param pdf - zu löschendes PDF Objekt
     */
    public void delete(PDF pdf);

    public Boolean isPDF(String uuid);

    public PDF findByUuid(String uuid);

    public PDF findVisibleByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von PDF Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen PDF Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<PDF> - Liste von PDF Objekten
     */
    public List<PDF> findAll(Integer start, Integer max);

    public List<PDF> findAll(Integer start, Integer max, String field, String order);

    public List<PDF> findAllVisible(Integer start, Integer max);

    public List<PDF> findAllVisible(PDFType pdfType, Integer start, Integer max);

    /**
     * Methode die die Anzahl aller PDF Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der PDF Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

}// Ende interface
