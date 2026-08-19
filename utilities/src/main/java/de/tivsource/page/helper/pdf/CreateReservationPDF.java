/**
 * 
 */
package de.tivsource.page.helper.pdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.helper.barcode.CreateBarcode;

/**
 * @author Marc Michele
 *
 */
public class CreateReservationPDF {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(CreateReservationPDF.class);

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
     * Standard Font des Dokumentes
     */
    private PDFont pdFont;

    /**
     * ContentStream der den Gesamten Inhalt des Dokumentes enthält
     */
    private PDPageContentStream pdPageContentStream;

    /**
     *  Reservierung 
     */
    private Reservation reservation;

    /**
     * Variablen für den PDF Barcode
     */
    private String pdfBarcodeFilePath;
    private File pdfBarcodeFile;
    private PDImageXObject pdfBarcodeImage;
    private float pdfBarcodeScale = 0.538f;

    /**
     * Variablen für das Title Bild
     */
    private PDImageXObject titleImage;
    private float titleScale = 0.858f;

    /**
     * Variablen für das Logo Bild
     */
    private File logoFile;
    private PDImageXObject logoImage;
    private float logoScale = 0.738f;

    /**
     * Variablen für das Location Bild
     */
    private PDImageXObject locationImage;
    private float locationScale = 0.35f;

    /**
     * Variablen für das Werbe Bild
     */
    private File adFile;
    private PDImageXObject adImage;
    private float adScale = 0.443f;

    private File fontFile;
    
    public CreateReservationPDF(File file, Reservation reservation, File logoFile, File adFile, File fontFile) throws IOException {
		super();
		this.file = file;
		this.reservation = reservation;
		this.logoFile = logoFile;
		this.adFile = adFile;
		this.fontFile = fontFile;
    	init();
    	generate();
    	close();
	}

    private void init() throws IOException {
    	// Erstelle das Dokument
        pdDocument = new PDDocument();
        // Erstelle die Seite
        pdPage = new PDPage(PDRectangle.A4);
        // Füge die Seite zum Dokument hinzu
        pdDocument.addPage(pdPage);
        // Erstelle Font Objekt
        //pdFont = PDTrueTypeFont.loadTTF(pdDocument, fontFile);
        pdFont = PDType0Font.load(pdDocument, fontFile);

        // Erstelle Pfad fuer Barcode Bild
        pdfBarcodeFilePath = "/tmp/" + reservation.getUuid() + ".png";
        // Erstelle Barcode Bild
        pdfBarcodeFile = new File(pdfBarcodeFilePath);
        new CreateBarcode(pdfBarcodeFile, reservation.getUuid());
        // Lade das bild in das Document
        pdfBarcodeImage = PDImageXObject.createFromFile(pdfBarcodeFilePath, pdDocument);

        LOGGER.info("UUID des Events: " + reservation.getEvent().getUuid());
        LOGGER.info("UUID der Location: " + reservation.getEvent().getLocation().getUuid());
        
        // Füge Event Bild ein
        titleImage = PDImageXObject.createFromFile(
                reservation.getEvent().getImage().getNormal(),
                pdDocument
                );

        // Füge Location Bild ein
        locationImage = PDImageXObject.createFromFile(
                reservation.getEvent().getLocation().getImage().getNormal(),
                pdDocument
                );

        // Füge Logo ein
        logoImage = PDImageXObject.createFromFile(
                logoFile.getAbsolutePath(),
                pdDocument
                );

        // Füge Werbung ein
        adImage = PDImageXObject.createFromFile(
                adFile.getAbsolutePath(),
                pdDocument
                );

        // Erstelle das ContentStream Objekt
        pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);

    }

    private void close() throws IOException {
    	// Speicher das Dokument unter dem angegebenen Pfad und Namen
    	pdDocument.save(file);
    	// Schließe das Dokument
    	pdDocument.close();
    	pdfBarcodeFile.delete();
    }

    private void generate() throws IOException {

    	pdPageContentStream.drawImage(logoImage, 40, 713, logoImage.getWidth() * logoScale, logoImage.getHeight() * logoScale);
    	
    	generateHeader();

    	pdPageContentStream.drawImage(titleImage, 40, 452, titleImage.getWidth() * titleScale, titleImage.getHeight() * titleScale);

    	generatePerson();
    	
    	generateDetails();

    	generateLocation();

    	generateRectangle();

    	pdPageContentStream.drawImage(adImage, 39, 140, adImage.getWidth() * adScale, adImage.getHeight() * adScale);

    	pdPageContentStream.drawImage(locationImage, 345, 140, locationImage.getWidth() * locationScale, locationImage.getHeight() * locationScale);

        pdPageContentStream.drawImage(pdfBarcodeImage, 20, 20, pdfBarcodeImage.getWidth() * pdfBarcodeScale, pdfBarcodeImage.getHeight() * pdfBarcodeScale);

        generateUuid();
        
        // Schleiße den ContentStream
    	pdPageContentStream.close();
    }

	private void generateRectangle() throws IOException {
		pdPageContentStream.setLineWidth(2f);
    	pdPageContentStream.setStrokingColor(new Color(239,128,21));
    	//pdPageContentStream.drawLine(20, 822, 576, 822);
    	pdPageContentStream.moveTo(20, 822);
    	pdPageContentStream.lineTo(576, 822);
    	pdPageContentStream.stroke();

    	//pdPageContentStream.drawLine(20, 120, 20, 822);
        pdPageContentStream.moveTo(20, 120);
        pdPageContentStream.lineTo(20, 822);
        pdPageContentStream.stroke();

    	//pdPageContentStream.drawLine(20, 120, 576, 120);
        pdPageContentStream.moveTo(20, 120);
        pdPageContentStream.lineTo(576, 120);
        pdPageContentStream.stroke();

    	//pdPageContentStream.drawLine(576, 822, 576, 120);
        pdPageContentStream.moveTo(576, 822);
        pdPageContentStream.lineTo(576, 120);
        pdPageContentStream.stroke();

    	pdPageContentStream.setLineWidth(1f);
	}

	private void generateLocation() throws IOException {
		int fontSize = 10;
		float text_width;
		
		pdPageContentStream.setLineWidth(2f);
    	pdPageContentStream.setStrokingColor(new Color(239,128,21));
    	//pdPageContentStream.drawLine(325, 130, 325, 440);
        pdPageContentStream.moveTo(325, 130);
        pdPageContentStream.lineTo(325, 440);
        pdPageContentStream.stroke();


		pdPageContentStream.setNonStrokingColor(Color.BLACK);

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getName("de")) / 1000.0f) * fontSize;
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 315 );
    	pdPageContentStream.showText(reservation.getEvent().getLocation().getName("de"));
    	pdPageContentStream.endText();
    	
    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getAddress().getStreet()) / 1000.0f) * fontSize;
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 300 );
    	pdPageContentStream.showText(reservation.getEvent().getLocation().getAddress().getStreet());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getAddress().getZip() + " " + reservation.getEvent().getLocation().getAddress().getCity()) / 1000.0f) * fontSize;
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 285 );
    	pdPageContentStream.showText(reservation.getEvent().getLocation().getAddress().getZip() + " " + reservation.getEvent().getLocation().getAddress().getCity());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Tel.: " + reservation.getEvent().getLocation().getContactDetails().getTelephone()) / 1000.0f) * fontSize;
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 265 );
    	pdPageContentStream.showText("Tel.: " + reservation.getEvent().getLocation().getContactDetails().getTelephone());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Fax: -"));
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 250 );
    	pdPageContentStream.showText("Fax: -");
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("E-Mail: " + reservation.getEvent().getLocation().getContactDetails().getEmail()) / 1000.0f) * fontSize;
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 235 );
    	pdPageContentStream.showText("E-Mail: " + reservation.getEvent().getLocation().getContactDetails().getEmail());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getContactDetails().getHomepage()) / 1000.0f) * fontSize;
    	pdPageContentStream.newLineAtOffset( 556 - text_width, 220 );
    	pdPageContentStream.showText(reservation.getEvent().getLocation().getContactDetails().getHomepage());
    	pdPageContentStream.endText();

	}

	private void generatePerson() throws IOException {
		pdPageContentStream.addRect(40, 335, 264, 95);

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 10 );
    	pdPageContentStream.newLineAtOffset( 50, 415 );
    	pdPageContentStream.showText( "Reservierung für:" );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 20 );
    	pdPageContentStream.newLineAtOffset( 50, 395 );
    	pdPageContentStream.showText( reservation.getGender() ? "Frau" : "Herrn" );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 20 );
    	pdPageContentStream.newLineAtOffset( 50, 370 );
    	pdPageContentStream.showText( reservation.getFirstname() );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 20 );
    	pdPageContentStream.newLineAtOffset( 50, 345 );
    	pdPageContentStream.showText( reservation.getLastname() );
    	pdPageContentStream.endText();
	}
	
	private void generateDetails() throws IOException {
		int fontSize = 22;
		float text_width;
		float text_position;

		pdPageContentStream.addRect(345, 340, 100, 90);

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("- " + reservation.getQuantity() + " -") / 1000.0f) * fontSize;
    	text_position = (100 - text_width) / 2;
    	pdPageContentStream.newLineAtOffset( 345 + text_position , 395 );
    	pdPageContentStream.showText( "- " + reservation.getQuantity() + " -" );
    	pdPageContentStream.endText();

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Pers.") / 1000.0f) * fontSize;
    	text_position = (100 - text_width) / 2;
    	pdPageContentStream.newLineAtOffset( 345 + text_position , 365 );
    	pdPageContentStream.showText( "Pers." );
    	pdPageContentStream.endText();

    	pdPageContentStream.addRect(465, 340, 90, 90);

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    	String time = simpleDateFormat.format(reservation.getTime());
    	text_width = (pdFont.getStringWidth(time) / 1000.0f) * fontSize;
    	text_position = (90 - text_width) / 2;
    	pdPageContentStream.newLineAtOffset( 465 + text_position , 395 );
    	pdPageContentStream.showText(time);
    	pdPageContentStream.endText();

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Uhr") / 1000.0f) * fontSize;
    	text_position = (90 - text_width) / 2;
    	pdPageContentStream.newLineAtOffset( 465 + text_position , 365 );
    	pdPageContentStream.showText( "Uhr" );
    	pdPageContentStream.endText();
	}

	private void generateHeader() throws IOException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String date = simpleDateFormat.format(reservation.getEvent().getBeginning());

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 25 );
    	pdPageContentStream.newLineAtOffset( 40, 680 );
    	pdPageContentStream.showText( "Reservierungsbestätigung" );
    	pdPageContentStream.endText();

    	
    	pdPageContentStream.setNonStrokingColor(new Color(239,128,21));
    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 25 );
    	pdPageContentStream.newLineAtOffset( 40, 650 );
    	pdPageContentStream.showText( reservation.getEvent().getName("de") + " am " + date );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 25 );
    	pdPageContentStream.newLineAtOffset( 40, 620 );
    	pdPageContentStream.showText( "im " + reservation.getEvent().getLocation().getName("de") );
    	pdPageContentStream.endText();
	}

	private void generateUuid() throws IOException {
		int fontSize = 8;
		float text_width;
		float text_position;
		text_width = (pdFont.getStringWidth(reservation.getUuid()) / 1000.0f) * fontSize;
		text_position = (556 - text_width) / 2;
    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	pdPageContentStream.newLineAtOffset( 20 + text_position , 14 );
    	pdPageContentStream.showText(reservation.getUuid());
    	pdPageContentStream.endText();
	}

}// Ende class
