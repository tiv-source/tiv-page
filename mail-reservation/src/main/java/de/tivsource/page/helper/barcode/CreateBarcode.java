/**
 * 
 */
package de.tivsource.page.helper.barcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoderRegistry;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * @author Marc Michele
 *
 */
public class CreateBarcode {

	/**
	 * Auflösung in Dots per Inch
	 */
	private final int dpi = 200;

	private final boolean antiAlias = false;
	private final int orientation = 0;
	
	private File file;

	private String uuid;

	public CreateBarcode(File file, String uuid) throws IOException {
		super();
		this.file = file;
		this.uuid = uuid;
		generate();
	}

	private void generate() throws IOException {
        // Erstelle PDF417 Barcode
        PDF417Bean bean = new PDF417Bean();

        // Setze das Fehlerkorekturlevel  
        bean.setErrorCorrectionLevel(2);

        // Nur zwei Spalten mit Inhalt
        bean.setColumns(2);

        bean.setModuleWidth(UnitConv.in2mm(10.0f / dpi));
        bean.doQuietZone(false);

        BitmapCanvasProvider bitmapCanvasProvider = new BitmapCanvasProvider(
                dpi, BufferedImage.TYPE_BYTE_BINARY, antiAlias, orientation);

        // Erstelle den Barcode
        bean.generateBarcode(bitmapCanvasProvider, uuid);

        // Beende die Barcode Generierung
        bitmapCanvasProvider.finish();

        // Hole das BufferedImage
        BufferedImage bufferedImage = bitmapCanvasProvider.getBufferedImage();


        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        //Add padding
        int padding = 2;
        width += 2 * padding;
        height += 3 * padding;

        BufferedImage bitmap = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = (Graphics2D)bitmap.getGraphics();
        g2d.setBackground(Color.white);
        g2d.setColor(Color.black);
        g2d.clearRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        

        //Place the barcode symbol
        AffineTransform symbolPlacement = new AffineTransform();
        symbolPlacement.translate(padding, padding);
        g2d.drawRenderedImage(bufferedImage, symbolPlacement);

        //Add text lines (or anything else you might want to add)

        g2d.dispose();

        //Encode bitmap as file
        String mime = "image/png";
        OutputStream out = new FileOutputStream(file);
        try {
            final BitmapEncoder encoder = BitmapEncoderRegistry.getInstance(mime);
            encoder.encode(bitmap, out, mime, dpi);
        } finally {
            out.close();
        }
        
	}// Ende generate()

}// Ende class
