/**
 * 
 */
package de.tivsource.page.helper.pdf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.helper.barcode.CreateBarcode;

/**
 * @author Marc Michele
 *
 */
public class CreateReservationPDF {

	private static final String HTDOCS = "/srv/www/htdocs";

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
    private File pdfBarcodeFile;
    private PDXObjectImage pdfBarcodeImage;
    private float pdfBarcodeScale = 0.538f;

    /**
     * Variablen für das Title Bild
     */
    private PDXObjectImage titleImage;
    private float titleScale = 0.858f;

    /**
     * Variablen für das Logo Bild
     */
    private File logoFile;
    private PDXObjectImage logoImage;
    private float logoScale = 0.738f;

    /**
     * Variablen für das Location Bild
     */
    private PDXObjectImage locationImage;
    private float locationScale = 0.35f;

    /**
     * Variablen für das Werbe Bild
     */
    private File adFile;
    private PDXObjectImage adImage;
    private float adScale = 0.443f;

    private File fontFile;
    
    public CreateReservationPDF(File file, Reservation reservation, File logoFile, File adFile, File fontFile) throws IOException, COSVisitorException {
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
        pdPage = new PDPage(PDPage.PAGE_SIZE_A4);
        // Füge die Seite zum Dokument hinzu
        pdDocument.addPage(pdPage);
        // Erstelle Font Objekt
        pdFont = PDTrueTypeFont.loadTTF(pdDocument, fontFile);

        // Erstelle Barcode Bild
        pdfBarcodeFile = new File("/tmp/" + reservation.getUuid() + ".png");
        new CreateBarcode(pdfBarcodeFile, reservation.getUuid());
        BufferedImage pdfBarcodeAwtImage = ImageIO.read(pdfBarcodeFile);
        pdfBarcodeImage = new PDPixelMap(pdDocument, pdfBarcodeAwtImage);

        // Erstelle Event Bild
		BufferedImage titleAwtImage = ImageIO.read(new File(HTDOCS
				+ reservation.getEvent().getPicture().getPictureUrl("NORMAL")));
        titleImage = new PDPixelMap(pdDocument, titleAwtImage);

        // Erstelle Location Bild
		BufferedImage locationAwtImage = ImageIO.read(new File(HTDOCS
				+ reservation.getEvent().getLocation().getPicture()
						.getPictureUrl("NORMAL")));
        locationImage = new PDPixelMap(pdDocument, locationAwtImage);

        // Erstelle Bild
        BufferedImage logoAwtImage = ImageIO.read(logoFile);
        logoImage = new PDPixelMap(pdDocument, logoAwtImage);

        // Erstelle Bild
        BufferedImage adAwtImage = ImageIO.read(adFile);
        adImage = new PDPixelMap(pdDocument, adAwtImage);

        // Erstelle das ContentStream Objekt
        pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);

    }

    private void close() throws IOException, COSVisitorException {
    	// Speicher das Dokument unter dem angegebenen Pfad und Namen
    	pdDocument.save(file);
    	// Schließe das Dokument
    	pdDocument.close();
    	pdfBarcodeFile.delete();
    }

    private void generate() throws IOException {

    	pdPageContentStream.drawXObject(logoImage, 40, 713, logoImage.getWidth() * logoScale, logoImage.getHeight() * logoScale);
    	
    	generateHeader();

    	pdPageContentStream.drawXObject(titleImage, 40, 452, titleImage.getWidth() * titleScale, titleImage.getHeight() * titleScale);

    	generatePerson();
    	
    	generateDetails();

    	generateLocation();

    	generateRectangle();

    	pdPageContentStream.drawXObject(adImage, 39, 140, adImage.getWidth() * adScale, adImage.getHeight() * adScale);

    	pdPageContentStream.drawXObject(locationImage, 345, 140, locationImage.getWidth() * locationScale, locationImage.getHeight() * locationScale);

        pdPageContentStream.drawXObject(pdfBarcodeImage, 20, 20, pdfBarcodeImage.getWidth() * pdfBarcodeScale, pdfBarcodeImage.getHeight() * pdfBarcodeScale);

        generateUuid();
        
        // Schleiße den ContentStream
    	pdPageContentStream.close();
    }

	private void generateRectangle() throws IOException {
		pdPageContentStream.setLineWidth(2f);
    	pdPageContentStream.setStrokingColor(new Color(239,128,21));
    	pdPageContentStream.drawLine(20, 822, 576, 822);
    	pdPageContentStream.drawLine(20, 120, 20, 822);
    	pdPageContentStream.drawLine(20, 120, 576, 120);
    	pdPageContentStream.drawLine(576, 822, 576, 120);
    	pdPageContentStream.setLineWidth(1f);
	}

	private void generateLocation() throws IOException {
		int fontSize = 10;
		float text_width;
		
		pdPageContentStream.setLineWidth(2f);
    	pdPageContentStream.setStrokingColor(new Color(239,128,21));
    	pdPageContentStream.drawLine(325, 130, 325, 440);

		pdPageContentStream.setNonStrokingColor(Color.BLACK);

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getName("de")) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 315 );
    	pdPageContentStream.drawString(reservation.getEvent().getLocation().getName("de"));
    	pdPageContentStream.endText();
    	
    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getAddress().getStreet()) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 300 );
    	pdPageContentStream.drawString(reservation.getEvent().getLocation().getAddress().getStreet());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getAddress().getZip() + " " + reservation.getEvent().getLocation().getAddress().getCity()) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 285 );
    	pdPageContentStream.drawString(reservation.getEvent().getLocation().getAddress().getZip() + " " + reservation.getEvent().getLocation().getAddress().getCity());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Tel.: " + reservation.getEvent().getLocation().getContactDetails().getTelephone()) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 265 );
    	pdPageContentStream.drawString("Tel.: " + reservation.getEvent().getLocation().getContactDetails().getTelephone());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Fax: " + reservation.getEvent().getLocation().getContactDetails().getFax()) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 250 );
    	pdPageContentStream.drawString("Fax: " + reservation.getEvent().getLocation().getContactDetails().getFax());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("E-Mail: " + reservation.getEvent().getLocation().getContactDetails().getEmail()) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 235 );
    	pdPageContentStream.drawString("E-Mail: " + reservation.getEvent().getLocation().getContactDetails().getEmail());
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth(reservation.getEvent().getLocation().getContactDetails().getHomepage()) / 1000.0f) * fontSize;
    	pdPageContentStream.moveTextPositionByAmount( 556 - text_width, 220 );
    	pdPageContentStream.drawString(reservation.getEvent().getLocation().getContactDetails().getHomepage());
    	pdPageContentStream.endText();
    	
	}

	private void generatePerson() throws IOException {
		pdPageContentStream.addRect(40, 335, 264, 95);

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 10 );
    	pdPageContentStream.moveTextPositionByAmount( 50, 415 );
    	pdPageContentStream.drawString( "Reservierung für:" );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 20 );
    	pdPageContentStream.moveTextPositionByAmount( 50, 395 );
    	pdPageContentStream.drawString( reservation.getGender() ? "Frau" : "Herrn" );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 20 );
    	pdPageContentStream.moveTextPositionByAmount( 50, 370 );
    	pdPageContentStream.drawString( reservation.getFirstname() );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 20 );
    	pdPageContentStream.moveTextPositionByAmount( 50, 345 );
    	pdPageContentStream.drawString( reservation.getLastname() );
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
    	pdPageContentStream.moveTextPositionByAmount( 345 + text_position , 395 );
    	pdPageContentStream.drawString( "- " + reservation.getQuantity() + " -" );
    	pdPageContentStream.endText();

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Pers.") / 1000.0f) * fontSize;
    	text_position = (100 - text_width) / 2;
    	pdPageContentStream.moveTextPositionByAmount( 345 + text_position , 365 );
    	pdPageContentStream.drawString( "Pers." );
    	pdPageContentStream.endText();

    	pdPageContentStream.addRect(465, 340, 90, 90);

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    	String time = simpleDateFormat.format(reservation.getTime());
    	text_width = (pdFont.getStringWidth(time) / 1000.0f) * fontSize;
    	text_position = (90 - text_width) / 2;
    	pdPageContentStream.moveTextPositionByAmount( 465 + text_position , 395 );
    	pdPageContentStream.drawString(time);
    	pdPageContentStream.endText();

		pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, fontSize );
    	text_width = (pdFont.getStringWidth("Uhr") / 1000.0f) * fontSize;
    	text_position = (90 - text_width) / 2;
    	pdPageContentStream.moveTextPositionByAmount( 465 + text_position , 365 );
    	pdPageContentStream.drawString( "Uhr" );
    	pdPageContentStream.endText();
	}

	private void generateHeader() throws IOException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String date = simpleDateFormat.format(reservation.getEvent().getBeginning());

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 25 );
    	pdPageContentStream.moveTextPositionByAmount( 40, 680 );
    	pdPageContentStream.drawString( "Reservierungsbestätigung" );
    	pdPageContentStream.endText();

    	
    	pdPageContentStream.setNonStrokingColor(new Color(239,128,21));
    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 25 );
    	pdPageContentStream.moveTextPositionByAmount( 40, 650 );
    	pdPageContentStream.drawString( reservation.getEvent().getName("de") + " am " + date );
    	pdPageContentStream.endText();

    	pdPageContentStream.beginText();
    	pdPageContentStream.setFont( pdFont, 25 );
    	pdPageContentStream.moveTextPositionByAmount( 40, 620 );
    	pdPageContentStream.drawString( "im " + reservation.getEvent().getLocation().getName("de") );
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
    	pdPageContentStream.moveTextPositionByAmount( 20 + text_position , 14 );
    	pdPageContentStream.drawString(reservation.getUuid());
    	pdPageContentStream.endText();
	}
}// Ende class
