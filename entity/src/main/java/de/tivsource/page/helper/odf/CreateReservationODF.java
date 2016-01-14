/**
 * 
 */
package de.tivsource.page.helper.odf;


import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.odftoolkit.odfdom.dom.element.style.StyleMasterPageElement;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStylePageLayout;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.PageLayoutProperties;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.PrintOrientation;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import de.tivsource.page.entity.event.Event;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.reservation.Reservation;

/**
 * @author Marc Michele
 *
 */
public class CreateReservationODF {
    
    private SpreadsheetDocument spreadsheetDocument;

    private Table table;

    private Cell cell;

    public SpreadsheetDocument create(List<Reservation> reservations) throws Exception {
        
        // Zum formatieren des Datums
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");

        // Erstelle neues Tabellen Dokument
        spreadsheetDocument = SpreadsheetDocument.newSpreadsheetDocument();

        // Formatiere Seite
        generatePageLayout(spreadsheetDocument);

        // Hole die erste Tabelle aus dem Dokument
        table = spreadsheetDocument.getTableList().get(0);

        // Hole Event aus den Daten
        Event event = reservations.get(0).getEvent();
        // Hole Location aus den Daten
        Location location = reservations.get(0).getEvent().getLocation();

        // Erzeuge die Überschrift der Tabelle
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Reservierungen für das ");
        stringBuilder.append(event.getName("de"));
        stringBuilder.append(" am ");
        stringBuilder.append(formatDate.format(event.getBeginning()));
        stringBuilder.append(" im ");
        stringBuilder.append(location.getName("de"));

        table.setTableName(stringBuilder.toString());
        

        // Setze die gloabe Überschrift in die Tabelle 
//        cell = table.getCellByPosition(0,0);
//        cell.setStringValue(stringBuilder.toString());
//        cell.setFont(new Font("Arial", FontStyle.BOLD, 12));
        // Verbinde die Zellen
//        CellRange cellRange = table.getCellRangeByPosition(0, 0, 9, 0);
//        cellRange.merge();
        

        // Erzeuge die Überschriften
        generateHeader();
        
        // Erzeuge Inhalt
        generateContent(reservations);
        
        return spreadsheetDocument;
    }

    private void generatePageLayout(SpreadsheetDocument spreadsheetDocument) {
        StyleMasterPageElement defaultPage = spreadsheetDocument.getOfficeMasterStyles().getMasterPage("Default");
        String pageLayoutName = defaultPage.getStylePageLayoutNameAttribute();
        OdfStylePageLayout pageLayoutStyle = defaultPage.getAutomaticStyles().getPageLayout(pageLayoutName);
        PageLayoutProperties pageLayoutProperties = PageLayoutProperties.getOrCreatePageLayoutProperties(pageLayoutStyle);
        pageLayoutProperties.setPageHeight(210);
        pageLayoutProperties.setPageWidth(297);
        pageLayoutProperties.setPrintOrientation(PrintOrientation.LANDSCAPE);
    }
    
    private void generateHeader() {
        // Erzeuge die Überschrift der ersten Spalte
        cell = table.getCellByPosition(0,0);
        cell.setStringValue("Anrede");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
        
        // Erzeuge die Überschrift der zweiten Spalte
        cell = table.getCellByPosition(1,0);
        cell.setStringValue("Vorname");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        cell = table.getCellByPosition(2,0);
        cell.setStringValue("Nachname");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        cell = table.getCellByPosition(3,0);
        cell.setStringValue("Telefon");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        cell = table.getCellByPosition(4,0);
        cell.setStringValue("Personen");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        cell = table.getCellByPosition(5,0);
        cell.setStringValue("Uhrzeit");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        cell = table.getCellByPosition(6,0);
        cell.setStringValue("Wünsche");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        /*
        cell = table.getCellByPosition(7,0);
        cell.setStringValue("Mail");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
        
        cell = table.getCellByPosition(8,0);
        cell.setStringValue("IP-Adresse");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));

        cell = table.getCellByPosition(9,0);
        cell.setStringValue("Erstellt am");
        cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
        */
    }

    private void generateContent(List<Reservation> reservations) {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Iterator<Reservation> iterator = reservations.iterator();
        Integer row = 1;
        while(iterator.hasNext()) {
            Reservation next = iterator.next();

            // Erzeuge die Überschrift der ersten Spalte
            cell = table.getCellByPosition(0,row);
            cell.setStringValue( next.getGender() ? "Frau" : "Herr" );

            // Erzeuge die Überschrift der zweiten Spalte
            cell = table.getCellByPosition(1,row);
            cell.setStringValue(next.getFirstname());

            cell = table.getCellByPosition(2,row);
            cell.setStringValue(next.getLastname());

            cell = table.getCellByPosition(3,row);
            cell.setStringValue(next.getTelephone());

            cell = table.getCellByPosition(4,row);
            cell.setDoubleValue(next.getQuantity().doubleValue());

            cell = table.getCellByPosition(5,row);
            cell.setStringValue(formatTime.format(next.getTime()));

            cell = table.getCellByPosition(6,row);
            cell.setStringValue(next.getWishes());

            /*
            cell = table.getCellByPosition(7,row);
            cell.setStringValue(next.getEmail());
            
            cell = table.getCellByPosition(8,row);
            cell.setStringValue(next.getIp());

            cell = table.getCellByPosition(9,row);
            cell.setStringValue(formatDate.format(next.getCreated()));
            */

            row++;
        }
    }
    
}// Ende class
