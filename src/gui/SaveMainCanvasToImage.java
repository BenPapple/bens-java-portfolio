package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Class to help save images as PNG of the MainCanvasPanel to disk.
 *
 * @author BenGe47
 */
public class SaveMainCanvasToImage {

	/**
	 * Empty Constructor.
	 */
	public SaveMainCanvasToImage() {

	}

	/**
	 * Save input BufferedImage to disk with input name and add file type when
	 * necessary.
	 * 
	 * @param filename
	 * @param image
	 */
	public void writeBufferedImgToDisk(String filename, BufferedImage image) {
		try {
			// Add file type onto filename
			if (!filename.endsWith(".png")) {
				filename += ".png";
			}
			// save main canvas to disk
			ImageIO.write(image, "png", new File(filename));
		} catch (IOException ex) {
			Logger.getLogger(SaveMainCanvasToImage.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
