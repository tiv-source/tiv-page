/**
 * 
 */
package de.tivsource.page.helper.pdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import de.tivsource.page.entity.registration.Registration;

/**
 * @author Marc Michele
 *
 */
public class CreateConfirmationPDF {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CreateConfirmationPDF.class);

    private File file;

    /**
     * PDF Dokument
     */
    private PDDocument pdDocument;

    /**
     * Einzige Seite des Dokumentes
     */
    private PDPage pdPage;

    /**
     * Standard Fonts des Dokumentes
     */
    private PDFont pdFontArial;
    private PDFont pdFontArialBold;

    /**
     * ContentStream der den Gesamten Inhalt des Dokumentes enthält
     */
    private PDPageContentStream pdPageContentStream;

    /**
     * Variablen für das Logo Bild
     */
    private File logoFile;
    private PDImageXObject logoImage;
    private float logoScale = 0.38f;

    private File fontArial;
    private File fontArialBold;

    private Registration registration;

    /*
     * Metadaten
     */
    private Calendar pdfCreationDate = Calendar.getInstance();
    private Calendar pdfModificationDate = Calendar.getInstance();

    private NumberFormat germanFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
    // Zum DecimalFormat casten, um Nachkommastellen zu steuern
    DecimalFormat decimalFormat = (DecimalFormat) germanFormat;
    {
        // Nachkommastellen auf 2 setzen
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
    }

    private float lastLine = 516f;

    private String[] documentInformation;

    private String[] documentData;

    private String[][] documentFooter;

    private String[] bankData;

    public CreateConfirmationPDF(
            File file, 
            Registration registration,
            Calendar pdfCreationDate,
            Calendar pdfModificationDate,
            File logoFile, 
            File fontArial,
            File fontArialBold,
            String[] documentInformation,
            String[] documentData,
            String[][] documentFooter,
            String[] bankData
        ) throws IOException {
        super();
        this.file = file;
        this.registration = registration;
        this.pdfCreationDate = pdfCreationDate;
        this.pdfModificationDate = pdfModificationDate;
        this.logoFile = logoFile;
        this.fontArial = fontArial;
        this.fontArialBold = fontArialBold;
        this.documentInformation = documentInformation;
        this.documentData = documentData;
        this.documentFooter = documentFooter;
        this.bankData = bankData;
        init();
        generate();
        close();
    }

    private void init() throws IOException {
        LOGGER.debug("Die Methode init() aufgerufen.");
        // Erstelle das Dokument
        pdDocument = new PDDocument();

        pdDocument.getDocumentInformation().setAuthor(documentInformation[0]);
        pdDocument.getDocumentInformation().setCreationDate(pdfCreationDate);
        pdDocument.getDocumentInformation().setCreator(documentInformation[1]);
        pdDocument.getDocumentInformation().setKeywords(documentInformation[2] + ", " + registration.getOrderConfirmationNumber());
        pdDocument.getDocumentInformation().setModificationDate(pdfModificationDate);
        pdDocument.getDocumentInformation().setProducer(documentInformation[3]);
        pdDocument.getDocumentInformation().setSubject(documentInformation[4]);
        pdDocument.getDocumentInformation().setTitle(documentInformation[5]);

        // Erstelle die Seite
        pdPage = new PDPage(PDRectangle.A4);
        // Füge die Seite zum Dokument hinzu
        pdDocument.addPage(pdPage);
        // Erstelle Font Objekte
        pdFontArial = PDType0Font.load(pdDocument, fontArial);
        pdFontArialBold = PDType0Font.load(pdDocument, fontArialBold);

        // Erstelle das ContentStream Objekt
        pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);
    }

    private void generate() throws IOException {

        // Faltmarken einfügen
        foldingMarks();

        // Logo einfügen
        logo();

        // Metadaten des Dokumentes hinzufügen
        documentData();
        
        // Füge die Empfänger-Daten ein
        generateAddressField();

        // Für Überschrift ein
        heading();

        // Füge Überschriften ein
        headlines();

        // Füge Kurskosten ein
        course();

        if(registration.getListOfMaterials()) {
            // Füge Materialkosten ein
            material();
        }

        // Für Summen ein
        sum();

        // Füge Bankinformationen ein
        bankDetails();

        // Füge den Footer ein
        footer();

        // Schließe den ContentStream
        pdPageContentStream.close();
    }

    private void close() throws IOException {
        // Speicher das Dokument unter dem angegebenen Pfad und Namen
        pdDocument.save(file);
        // Schließe das Dokument
        pdDocument.close();
    }

    private void logo() throws IOException {
        // Füge Logo ein
        logoImage = PDImageXObject.createFromFile(
                logoFile.getAbsolutePath(),
                pdDocument
                );
        float logoWidth = logoImage.getWidth() * logoScale;
        float logoHeight = logoImage.getHeight() * logoScale;
        pdPageContentStream.drawImage(logoImage, 563.92f - logoWidth, 777 - logoHeight, logoWidth, logoHeight);
    }// Ende logo()

    private void documentData() throws IOException {
        // Größe der Schift
        int fontSize = 10;
        float leading = 1.5f * fontSize;
        float text_width;
        float nextLine = 680f;

        // Datum des Dokumentes Überschirft
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 400f, 680f );
        pdPageContentStream.showText(documentData[1]);
        pdPageContentStream.endText();

        // Datum des Dokumentes
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(documentData[2]) / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, 680f );
        pdPageContentStream.showText(documentData[2]);
        pdPageContentStream.endText();
        
        nextLine = nextLine - leading;

        // Nummer des Dokumentes Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 400f, nextLine );
        pdPageContentStream.showText(documentData[3]);
        pdPageContentStream.endText();

        // Nummer des Dokumentes
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(registration.getOrderConfirmationNumber()) / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, nextLine );
        pdPageContentStream.showText(registration.getOrderConfirmationNumber());
        pdPageContentStream.endText();

        nextLine = nextLine - leading;

        // Seitennummer Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 400f, nextLine );
        pdPageContentStream.showText(documentData[4]);
        pdPageContentStream.endText();

        // Seitennummer
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(documentData[5]) / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, nextLine );
        pdPageContentStream.showText(documentData[5]);
        pdPageContentStream.endText();
        
    }

    private void generateAddressField() throws IOException {
        // Größe der Schift
        int fontSize = 6;
        float text_width = 0;
        float space = 3;

        // Schriftware auswählen
        pdPageContentStream.setNonStrokingColor(Color.BLACK);

        // Header setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 713 );
        pdPageContentStream.showText(documentData[0]);
        pdPageContentStream.endText();

        // Setze Schriftgrösse
        fontSize = 10;

        if(!registration.getConsumer()) {
            // Firma setzen
            pdPageContentStream.beginText();
            pdPageContentStream.setFont( pdFontArial, fontSize );
            pdPageContentStream.newLineAtOffset( 45, 690 );
            pdPageContentStream.showText(registration.getFirm());
            pdPageContentStream.endText();
        }

        if(registration.getSalutation().getName("de").equals("Herr") || registration.getSalutation().getName("de").equals("Frau")) {
            // Anrede
            pdPageContentStream.beginText();
            pdPageContentStream.setFont( pdFontArial, fontSize );
            pdPageContentStream.newLineAtOffset( 45, 676 );
            pdPageContentStream.showText(registration.getSalutation().getName("de"));
            pdPageContentStream.endText();
            // Text Breite für nächsten Teil
            text_width = (pdFontArial.getStringWidth(registration.getSalutation().getName("de")) / 1000.0f) * fontSize;
            text_width = text_width + space;
        }

        // Vorname
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45 + text_width, 676 );
        pdPageContentStream.showText(registration.getFirstname());
        pdPageContentStream.endText();
        if(registration.getSalutation().getName("de").equals("Herr") || registration.getSalutation().getName("de").equals("Frau")) {
            // Text Breite für nächsten Teil
            text_width = (pdFontArial.getStringWidth(registration.getSalutation().getName("de") + registration.getFirstname()) / 1000.0f) * fontSize;
            text_width = text_width + space + space;
        } else {
         // Text Breite für nächsten Teil
            text_width = (pdFontArial.getStringWidth(registration.getFirstname()) / 1000.0f) * fontSize;
            text_width = text_width + space;
        }

        // Nachname
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45 + text_width, 676 );
        pdPageContentStream.showText(registration.getLastname());
        pdPageContentStream.endText();

        // Strasse
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 662 );
        pdPageContentStream.showText(registration.getStreet());
        pdPageContentStream.endText();

        // Postleitzahl
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 648 );
        pdPageContentStream.showText(registration.getZip());
        pdPageContentStream.endText();

        // Stadt
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(registration.getZip()) / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 45 + text_width + space, 648 );
        pdPageContentStream.showText(registration.getCity());
        pdPageContentStream.endText();

        // Land
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 633.5f );
        pdPageContentStream.showText(registration.getCountry().getName("de"));
        pdPageContentStream.endText();

    }// generateAddressField()

    private void foldingMarks() throws IOException {

        pdPageContentStream.setLineWidth(0.5f);
        pdPageContentStream.setStrokingColor(new Color(105,105,105));
        pdPageContentStream.moveTo(19, 544.5f);
        pdPageContentStream.lineTo(32, 544.5f);
        pdPageContentStream.stroke();

        pdPageContentStream.setLineWidth(0.5f);
        pdPageContentStream.setStrokingColor(new Color(105,105,105));
        pdPageContentStream.moveTo(19, 421f);
        pdPageContentStream.lineTo(28, 421f);
        pdPageContentStream.stroke();

        pdPageContentStream.setLineWidth(0.5f);
        pdPageContentStream.setStrokingColor(new Color(105,105,105));
        pdPageContentStream.moveTo(19, 246.5f);
        pdPageContentStream.lineTo(28, 246.5f);
        pdPageContentStream.stroke();

    }// Ende foldingMarks()

    private void heading() throws IOException {

        // Größe der Schift
        int fontSize = 14;

        // Schriftware auswählen
        pdPageContentStream.setNonStrokingColor(Color.BLACK);

        // Header setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 565.5f );
        pdPageContentStream.showText("Auftragsbestätigung");
        pdPageContentStream.endText();

    }// Ende heading()

    private void headlines() throws IOException {

        // Größe der Schift
        int fontSize = 8;
        float text_width;

        // Schriftware auswählen
        pdPageContentStream.setNonStrokingColor(Color.BLACK);

        // Position setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 539.5f );
        pdPageContentStream.showText("Pos.");
        pdPageContentStream.endText();

        // Menge setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 73.5f, 539.5f );
        pdPageContentStream.showText("Menge");
        pdPageContentStream.endText();

        // Bezeichnung setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 116f, 539.5f );
        pdPageContentStream.showText("Bezeichnung");
        pdPageContentStream.endText();

        // MwSt. setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 375.5f, 539.5f );
        pdPageContentStream.showText("% MwSt.");
        pdPageContentStream.endText();

        // Einzelpreis setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 437f, 539.5f );
        pdPageContentStream.showText("Einzelpreis");
        pdPageContentStream.endText();

        // Gesamtpreis setzen
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        text_width = (pdFontArialBold.getStringWidth("Gesamtpreis") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, 539.5f );
        pdPageContentStream.showText("Gesamtpreis");
        pdPageContentStream.endText();

    }// Ende headlines()

    private void course() throws IOException {
        // Größe der Schift
        int fontSize = 10;
        float leading = 1.5f * fontSize;
        float text_width;

        // Positionsnummer
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, 516f );
        pdPageContentStream.showText("1");
        pdPageContentStream.endText();

        // Menge
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth("2 x") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 99 - text_width, 516f );
        pdPageContentStream.showText("1 x");
        pdPageContentStream.endText();

        List<String> courseLines = splitText(
                registration.getCourse().getCourseDocumentDescription(),
                pdFontArial,
                fontSize,
                245f
            );

        lastLine = lastLine + leading;

        for(String line : courseLines) {
            lastLine = lastLine - leading;
            // Kurs
            pdPageContentStream.beginText();
            pdPageContentStream.setFont( pdFontArial, fontSize );
            pdPageContentStream.newLineAtOffset( 115, lastLine );
            pdPageContentStream.showText(line);
            pdPageContentStream.endText();
        }

        // MwSt Preis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth("19%") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 408 - text_width, 516f );
        pdPageContentStream.showText("19%");
        pdPageContentStream.endText();



        // Einzelpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(decimalFormat.format(registration.getCourse().getPrice()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 478.92f - text_width, 516f );
        pdPageContentStream.showText(decimalFormat.format(registration.getCourse().getPrice()) + " €");
        pdPageContentStream.endText();

        // Gesamtpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(decimalFormat.format(registration.getCourse().getPrice()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, 516f );
        pdPageContentStream.showText(decimalFormat.format(registration.getCourse().getPrice()) + " €");
        pdPageContentStream.endText();

    }// Ende course()

    private void material() throws IOException {
        // Größe der Schift
        int fontSize = 10;
        float leading = 1.5f * fontSize;
        float text_width;
        float firstLine = lastLine - leading - 5;

        // Positionsnummer
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, firstLine );
        pdPageContentStream.showText("2");
        pdPageContentStream.endText();

        // Menge
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth("2 x") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 99 - text_width, firstLine );
        pdPageContentStream.showText("1 x");
        pdPageContentStream.endText();

        List<String> courseLines = splitText(
                registration.getCourse().getMaterialDocumentDescription(),
                pdFontArial,
                fontSize,
                245f
            );
        
        lastLine = lastLine - 5;

        for(String line : courseLines) {
            lastLine = lastLine - leading;
            // Material
            pdPageContentStream.beginText();
            pdPageContentStream.setFont( pdFontArial, fontSize );
            pdPageContentStream.newLineAtOffset( 115, lastLine );
            pdPageContentStream.showText(line);
            pdPageContentStream.endText();
        }

        // MwSt Preis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth("19%") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 408 - text_width, firstLine );
        pdPageContentStream.showText("19%");
        pdPageContentStream.endText();

        // Einzelpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(decimalFormat.format(registration.getCourse().getMaterialPrice()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 478.92f - text_width, firstLine );
        pdPageContentStream.showText(decimalFormat.format(registration.getCourse().getMaterialPrice()) + " €");
        pdPageContentStream.endText();

        // Gesamtpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(decimalFormat.format(registration.getCourse().getMaterialPrice()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, firstLine );
        pdPageContentStream.showText(decimalFormat.format(registration.getCourse().getMaterialPrice()) + " €");
        pdPageContentStream.endText();

    }// Ende material()

    private void sum() throws IOException {

        // Größe der Schift
        int fontSize = 10;
        float leading = 1.5f * fontSize;
        float text_width;

        lastLine = lastLine - leading;

        pdPageContentStream.setLineWidth(2f);
        pdPageContentStream.setStrokingColor(new Color(105,105,105));
        pdPageContentStream.moveTo(42, lastLine);
        pdPageContentStream.lineTo(576.5f, lastLine);
        pdPageContentStream.stroke();

        lastLine = lastLine - leading - 2;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, lastLine);
        pdPageContentStream.showText("Nettobetrag");
        pdPageContentStream.endText();

        // Gesamtpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(decimalFormat.format(registration.netSum()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, lastLine );
        pdPageContentStream.showText(decimalFormat.format(registration.netSum()) + " €");
        pdPageContentStream.endText();

        lastLine = lastLine - leading - 3;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 45, lastLine);
        pdPageContentStream.showText("zzgl. MwSt.");
        pdPageContentStream.endText();

        // Gesamtpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        text_width = (pdFontArial.getStringWidth(decimalFormat.format(registration.tax()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, lastLine );
        pdPageContentStream.showText(decimalFormat.format(registration.tax()) + " €");
        pdPageContentStream.endText();

        lastLine = lastLine - 7;

        pdPageContentStream.setLineWidth(1f);
        pdPageContentStream.setStrokingColor(new Color(105,105,105));
        pdPageContentStream.moveTo(42, lastLine);
        pdPageContentStream.lineTo(576.5f, lastLine);
        pdPageContentStream.stroke();

        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 45, lastLine);
        pdPageContentStream.showText("Gesamtbetrag");
        pdPageContentStream.endText();

        // Gesamtpreis für den Kurs
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        text_width = (pdFontArialBold.getStringWidth(decimalFormat.format(registration.sum()) + " €") / 1000.0f) * fontSize;
        pdPageContentStream.newLineAtOffset( 563.92f - text_width, lastLine );
        pdPageContentStream.showText(decimalFormat.format(registration.sum()) + " €");
        pdPageContentStream.endText();

        lastLine = lastLine - 7;

        pdPageContentStream.setLineWidth(2.5f);
        pdPageContentStream.setStrokingColor(new Color(105,105,105));
        pdPageContentStream.moveTo(42, lastLine);
        pdPageContentStream.lineTo(576.5f, lastLine);
        pdPageContentStream.stroke();

    }// Ende sum()

    private void bankDetails() throws IOException {

        // Größe der Schift
        int fontSize = 10;
        float leading = 1.5f * fontSize;
        float firstLine = lastLine - leading - 30;

        lastLine = lastLine - leading - 30;

        pdPageContentStream.setLineWidth(2.5f);
        pdPageContentStream.setStrokingColor(new Color(255,0,0));
        pdPageContentStream.moveTo(100, lastLine);
        pdPageContentStream.lineTo(495.3f, lastLine);
        pdPageContentStream.stroke();

        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 115, lastLine);
        pdPageContentStream.showText(bankData[0]);
        pdPageContentStream.endText();

        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 115, lastLine);
        pdPageContentStream.showText(bankData[1]);
        pdPageContentStream.endText();

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 231, lastLine);
        pdPageContentStream.showText(bankData[2]);
        pdPageContentStream.endText();

        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 115, lastLine);
        pdPageContentStream.showText(bankData[3]);
        pdPageContentStream.endText();

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 231, lastLine);
        pdPageContentStream.showText(bankData[4]);
        pdPageContentStream.endText();

        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 115, lastLine);
        pdPageContentStream.showText(bankData[5]);
        pdPageContentStream.endText();

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 231, lastLine);
        pdPageContentStream.showText(bankData[6]);
        pdPageContentStream.endText();

        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 115, lastLine);
        pdPageContentStream.showText(bankData[7]);
        pdPageContentStream.endText();

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 231, lastLine);
        pdPageContentStream.showText(bankData[8]);
        pdPageContentStream.endText();
        
        lastLine = lastLine - leading;

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 115, lastLine);
        pdPageContentStream.showText(bankData[9]);
        pdPageContentStream.endText();

        // Bezeichung
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 231, lastLine);
        pdPageContentStream.showText(
                registration.getOrderConfirmationNumber() +
                " - " +
                registration.getFirstname() + 
                " " + 
                registration.getLastname()
            );
        pdPageContentStream.endText();

        lastLine = lastLine - leading + 7;

        pdPageContentStream.setLineWidth(2.5f);
        pdPageContentStream.setStrokingColor(new Color(255,0,0));
        pdPageContentStream.moveTo(100, lastLine);
        pdPageContentStream.lineTo(495.3f, lastLine);
        pdPageContentStream.stroke();


        pdPageContentStream.setLineWidth(2.5f);
        pdPageContentStream.setStrokingColor(new Color(255,0,0));
        pdPageContentStream.moveTo(101.2f, firstLine );
        pdPageContentStream.lineTo(101.2f, lastLine );
        pdPageContentStream.stroke();

        pdPageContentStream.setLineWidth(2.5f);
        pdPageContentStream.setStrokingColor(new Color(255,0,0));
        pdPageContentStream.moveTo(494f, firstLine );
        pdPageContentStream.lineTo(494f, lastLine );
        pdPageContentStream.stroke();

        
    }// Ende bankDetails()

    private void footer() throws IOException {
        // Größe der Schift
        int fontSize = 8;

        float beginBlockTwo = 150.5f;
        float beginBlockThree = 249.5f;
        float beginBlockFour = 370f;
        float beginBlockFive = 487.5f;

        // Schriftware auswählen
        pdPageContentStream.setNonStrokingColor(Color.BLACK);

        // Block 1 Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( 37f, 71.5f );
        pdPageContentStream.showText(documentFooter[0][0]);
        pdPageContentStream.endText();

        // Block 1 Zeile 1 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 37f, 62.5f );
        pdPageContentStream.showText(documentFooter[0][1]);
        pdPageContentStream.endText();

        // Block 1 Zeile 2 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 37f, 53.5f );
        pdPageContentStream.showText(documentFooter[0][2]);
        pdPageContentStream.endText();

        // Block 1 Zeile 3 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( 37f, 44.5f );
        pdPageContentStream.showText(documentFooter[0][3]);
        pdPageContentStream.endText();

        // Block 2 Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockTwo, 71.5f );
        pdPageContentStream.showText(documentFooter[1][0]);
        pdPageContentStream.endText();

        // Block 2 Zeile 1 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockTwo, 62.5f );
        pdPageContentStream.showText(documentFooter[1][1]);
        pdPageContentStream.endText();

        // Block 2 Zeile 2 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockTwo, 53.5f );
        pdPageContentStream.showText(documentFooter[1][2]);
        pdPageContentStream.endText();

        // Block 2 Zeile 3 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockTwo, 44.5f );
        pdPageContentStream.showText(documentFooter[1][3]);
        pdPageContentStream.endText();

        // Block 3 Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockThree, 71.5f );
        pdPageContentStream.showText(documentFooter[2][0]);
        pdPageContentStream.endText();

        // Block 3 Zeile 1 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockThree, 62.5f );
        pdPageContentStream.showText(documentFooter[2][1]);
        pdPageContentStream.endText();

        // Block 3 Zeile 2 Überschrift 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockThree, 53.5f );
        pdPageContentStream.showText(documentFooter[2][2]);
        pdPageContentStream.endText();

        // Block 3 Zeile 3 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockThree, 44.5f );
        pdPageContentStream.showText(documentFooter[2][3]);
        pdPageContentStream.endText();

        // Block 4 Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFour, 71.5f );
        pdPageContentStream.showText(documentFooter[3][0]);
        pdPageContentStream.endText();

        // Block 4 Zeile 1 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFour, 62.5f );
        pdPageContentStream.showText(documentFooter[3][1]);
        pdPageContentStream.endText();

        // Block 4 Zeile 2 Überschrift 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFour, 53.5f );
        pdPageContentStream.showText(documentFooter[3][2]);
        pdPageContentStream.endText();

        // Block 4 Zeile 3 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFour, 44.5f );
        pdPageContentStream.showText(documentFooter[3][3]);
        pdPageContentStream.endText();

        // Block 5 Überschrift
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFive, 71.5f );
        pdPageContentStream.showText(documentFooter[4][0]);
        pdPageContentStream.endText();

        // Block 5 Zeile 1 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFive, 62.5f );
        pdPageContentStream.showText(documentFooter[4][1]);
        pdPageContentStream.endText();

        // Block 5 Zeile 2 Überschrift 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArialBold, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFive, 53.5f );
        pdPageContentStream.showText(documentFooter[4][2]);
        pdPageContentStream.endText();

        // Block 5 Zeile 3 
        pdPageContentStream.beginText();
        pdPageContentStream.setFont( pdFontArial, fontSize );
        pdPageContentStream.newLineAtOffset( beginBlockFive, 44.5f );
        pdPageContentStream.showText(documentFooter[4][3]);
        pdPageContentStream.endText();

    }// Ende footer()

    public List<String> splitText(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            
            // Berechne Breite in Punkten: (Breite_Einheiten * Schriftgröße) / 1000
            float width = font.getStringWidth(testLine) / 1000 * fontSize;

            if (width > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                currentLine.append(currentLine.length() == 0 ? "" : " ").append(word);
            }
        }
        lines.add(currentLine.toString()); // Letzte Zeile hinzufügen
        return lines;
    }// Ende splitText(String text, PDFont font, float fontSize, float maxWidth)

}// Ende class
