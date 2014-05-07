package Image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

	private BufferedImage srcImage = null;
	byte[] pixelDataByteArray = null;
	int[][] pixelDataInt = null;
	private final int width;
	private final int height;

	public Image(String path)

	{
		File imgFile = new File(path);

		try {
			srcImage = javax.imageio.ImageIO.read(imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		width = srcImage.getWidth();
		height = srcImage.getHeight();
	}

	public int getWidth() {
		return width;

	}

	public int getHeight() {
		return height;
	}

	public int[][] getPixelData() {

		Raster raster = srcImage.getData();
		DataBuffer buffer = raster.getDataBuffer();
		DataBufferByte byteBuffer = (DataBufferByte) buffer;

		pixelDataByteArray = new byte[width * height];
		pixelDataByteArray = byteBuffer.getData(0);

		int t = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixelDataInt[i][j] = 0xff & pixelDataByteArray[t];
				t++;
			}
		}

		return pixelDataInt;
	}

	public BufferedImage getOriginalBufferedImage() {
		return srcImage;
	}

	public void saveImage() {

		try {
			ImageIO.write(srcImage, "jpg", new File("srcImage.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveGrayScale(BufferedImage bufferedImage) {

		try {
			ImageIO.write(bufferedImage, "jpg", new File("grayImage.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveThreshold() {

	}
}
