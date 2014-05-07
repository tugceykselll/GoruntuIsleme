package ImageBasicProcess;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class ImageBasicProces {

	BufferedImage originalImage;
	BufferedImage grayImage;
	int width, height;

	public ImageBasicProces(BufferedImage originalImage, int width, int height) {
		// TODO Auto-generated constructor stub

		this.originalImage = originalImage;
		this.width = width;
		this.height = height;
	}

	public BufferedImage convertGrayScale() {

		ColorConvertOp colorConvert = new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		grayImage = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		colorConvert.filter(originalImage, grayImage);
		return grayImage;
	}

}
