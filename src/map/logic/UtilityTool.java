package map.logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The UtilityTool class provides utility methods for image manipulation, 
 * such as scaling images to specified dimensions.
 */
public class UtilityTool {

	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		
		return scaledImage;
	}
}