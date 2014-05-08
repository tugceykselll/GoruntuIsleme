package ImageBasicProcess;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class ImageBasicProces {

	BufferedImage originalImage;
	BufferedImage grayImage;
	BufferedImage thresholImage;
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

	public List<Integer> Histogram(BufferedImage grayBuffered) {
		Raster raster = grayBuffered.getData();
		DataBuffer buffer = raster.getDataBuffer();
		DataBufferByte byteBuffer = (DataBufferByte) buffer;

		byte[] pixelDataByteArray = new byte[width * height];
		pixelDataByteArray = byteBuffer.getData(0);

		List<Integer> histogram = new ArrayList<Integer>();

		for (int i = 0; i < 256; i++) {
			histogram.add(0);
		}

		for (int i = 0; i < pixelDataByteArray.length; i++) {
			int r = 0xFF & pixelDataByteArray[i];
			int a = histogram.get(r);
			histogram.set(r, a + 1);
		}

		return histogram;
	}

	public BufferedImage OtsuThreshold(List<Integer> histogram) {
		List<Float> Varyans_List = new ArrayList<Float>();

		for (int i = 0; i < 256; i++)

		{

			int agirlik1 = 0, agirlik2 = 0;
			int y_toplam_1 = 0, y_toplam_2 = 0;
			float mean_pay_1 = 0, mean_pay_2 = 0;
			float mean_1 = 0, mean_2 = 0;
			float sýnýf_arasi_varyans = 0;

			for (int j = 0; j <= i; j++) {
				y_toplam_1 = y_toplam_1 + histogram.get(j);
				mean_pay_1 = mean_pay_1 + histogram.get(j) * j;

			}

			for (int k = (i + 1); k < 256; k++)

			{
				y_toplam_2 = y_toplam_2 + histogram.get(k);
				mean_pay_2 = mean_pay_2 + histogram.get(k) * k;

			}

			agirlik1 = y_toplam_1;
			agirlik2 = y_toplam_2;

			if (y_toplam_1 == 0) {
				mean_1 = 0;
			} else {
				mean_1 = (mean_pay_1 / y_toplam_1);
			}

			if (y_toplam_2 == 0) {
				mean_2 = 0;
			} else {
				mean_2 = (mean_pay_2 / y_toplam_2);
			}

			sýnýf_arasi_varyans = (((float) agirlik1 * (float) agirlik2) * ((mean_1 - mean_2) * (mean_1 - mean_2)));
			Varyans_List.add(sýnýf_arasi_varyans);

		}

		int Threshold_index = 0;
		float Threshold_deger = 0;

		for (int i = 0; i < Varyans_List.size(); i++) {
			if (Threshold_deger < Varyans_List.get(i)) {
				Threshold_index = i;
				Threshold_deger = Varyans_List.get(i);
			}
		}
		Raster rasterGray = grayImage.getData();
		DataBuffer buffer = rasterGray.getDataBuffer();
		DataBufferByte byteBuffer = (DataBufferByte) buffer;

		byte[] bs = new byte[width * height];
		bs = byteBuffer.getData(0);

		int ptr;
		byte[] monoData = new byte[bs.length];

		if (monoData != null) {
			ptr = 0;
			while (ptr < bs.length) {
				monoData[ptr] = ((0xFF & bs[ptr]) >= Threshold_index) ? (byte) 255
						: 0;
				ptr++;
			}
		}
		thresholImage = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = thresholImage.getRaster();

		int t = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				raster.setSample(j, i, 0, (0xFF & monoData[t]));
				t++;
			}
		}
		return thresholImage;

	}
}
