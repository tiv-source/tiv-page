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
 * Klassen zum Testen ob die BArcode Erstellung koreckt funktioniert.
 * @author Marc Michele
 *
 */
public class TestBarcode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
        	TestBarcode app = new TestBarcode();
            File outputFile = new File("target/out.png");
            app.generate(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
	}

	

    private void generate(File outputFile) throws IOException {
        String msg = "7125f3be-8d8d-4a78-809e-4ae236b825d1222";

        //Create the barcode bean
        PDF417Bean bean = new PDF417Bean();

        bean.setErrorCorrectionLevel(2);
        bean.setColumns(2);

        final int dpi = 200;

        //Configure the barcode generator
        bean.setModuleWidth(UnitConv.in2mm(10.0f / dpi)); //makes a dot/module exactly eight pixels
        bean.doQuietZone(false);
       // bean.setShape(SymbolShapeHint.FORCE_RECTANGLE);

        boolean antiAlias = false;
        int orientation = 0;
        //Set up the canvas provider to create a monochrome bitmap
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                dpi, BufferedImage.TYPE_BYTE_BINARY, antiAlias, orientation);

        //Generate the barcode
        bean.generateBarcode(canvas, msg);

        //Signal end of generation
        canvas.finish();

        //Get generated bitmap
        BufferedImage symbol = canvas.getBufferedImage();

        int width = symbol.getWidth();
        int height = symbol.getHeight();

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
        g2d.drawRenderedImage(symbol, symbolPlacement);

        //Add text lines (or anything else you might want to add)

        g2d.dispose();

        //Encode bitmap as file
        String mime = "image/png";
        OutputStream out = new FileOutputStream(outputFile);
        try {
            final BitmapEncoder encoder = BitmapEncoderRegistry.getInstance(mime);
            encoder.encode(bitmap, out, mime, dpi);
        } finally {
            out.close();
        }
    }
	
}
