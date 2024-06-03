import java.awt.Color;
import java.awt.image.BufferedImage;

public class GrayscaleConverterThread extends Thread {

	private BufferedImage image;
	private int startY;
	private int endY;


	public GrayscaleConverterThread(BufferedImage image, int startY, int endY) {
		this.image = image;
		this.startY = startY;
		this.endY = endY;
	}

	@Override
	public void run() {
		for (int y = startY; y < endY; y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				Color color = new Color(rgb, true);

				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				int gray = (red + green + blue) / 3;
				Color newColor = new Color(gray, gray, gray, color.getAlpha());
				
				image.setRGB(x, y, newColor.getRGB());
			}
		}
	}
}