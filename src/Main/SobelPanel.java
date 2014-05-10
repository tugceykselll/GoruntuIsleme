package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import Image.Image;
import ImageBasicProcess.ImageBasicProces;

public class SobelPanel extends JPanel {
	BufferedImage loadImage;
	BufferedImage convertImage;
	BufferedImage thresholdImage;
	BufferedImage beforeImage;
	BufferedImage gausImage;

	public SobelPanel(final InnerClass context) {
		// TODO Auto-generated constructor stub
		final JButton loadImageButton = new JButton("Resim Yükle");
		final JButton convertGrayButton = new JButton("Gray Scale Yap ");
		final JButton gausButton = new JButton("Gaus Uygula");
		final JButton sobelButton = new JButton("Sobel Uygula");
		final JButton beforeButton = new JButton("Geri Dön");
		String dir = System.getProperty("user.dir");
		try {
			loadImage = ImageIO.read(new File("open.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			convertImage = ImageIO.read(new File("donustur.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			thresholdImage = ImageIO.read(new File("derivative.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			beforeImage = ImageIO.read(new File("before.png"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			gausImage = ImageIO.read(new File("gaus.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		loadImageButton.setIcon(new ImageIcon(loadImage));
		loadImageButton.setVisible(true);

		convertGrayButton.setIcon(new ImageIcon(convertImage));
		convertGrayButton.setVisible(true);

		sobelButton.setIcon(new ImageIcon(thresholdImage));
		sobelButton.setVisible(true);

		beforeButton.setIcon(new ImageIcon(beforeImage));
		beforeButton.setVisible(true);

		gausButton.setIcon(new ImageIcon(gausImage));
		gausButton.setVisible(true);

		add(loadImageButton);
		add(convertGrayButton);
		add(gausButton);
		add(sobelButton);
		add(beforeButton);

		ActionListener listener = new ActionListener() {
			Image orginalImage = null;
			Image copyOrginalImage = null;
			JLabel grayPicLabel;
			JLabel picLabel = null;
			ImageBasicProces imgBasicPrcs = null;
			BufferedImage grayBuffered = null;
			BufferedImage thresholdPicBuffered = null;
			JLabel picThresholdLabel = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (e.getSource() == loadImageButton) {

					JFileChooser jfc = new JFileChooser();

					jfc.setFileFilter(new FileFilter() {
						@Override
						public boolean accept(File f) {
							if (f.isDirectory()) {
								return true;
							}
							String fileName = f.getName();
							int nokta = fileName.lastIndexOf('.');
							String ext = "";
							if (nokta > 0) {
								ext = fileName.substring(nokta + 1);
							}
							return ext.equalsIgnoreCase("jpg");
						}

						@Override
						public String getDescription() {
							return "JPEG Dosyalarý";
						}
					});

					int kullaniciSecimi = jfc.showOpenDialog(null);

					if (kullaniciSecimi == JFileChooser.APPROVE_OPTION) {
						// if (picLabel != null) {
						// remove(picLabel);
						// }
						// if (grayPicLabel != null) {
						// remove(grayPicLabel);
						//
						// }
						// if (picThresholdLabel != null) {
						// remove(picThresholdLabel);
						// }
						String dirImage = jfc.getSelectedFile().toString();
						BufferedImage pic = null;

						orginalImage = new Image(dirImage);
						orginalImage.saveImage();
						pic = orginalImage.getOriginalBufferedImage();
						picLabel = new JLabel(new ImageIcon(pic));
						picLabel.setBounds(25, 50, 500, 500);
						add(picLabel);
						repaint();
					}

				} else if (e.getSource() == convertGrayButton) {

					copyOrginalImage = new Image("srcImage.jpg");

					imgBasicPrcs = new ImageBasicProces(
							copyOrginalImage.getOriginalBufferedImage(),
							copyOrginalImage.getWidth(),
							copyOrginalImage.getHeight());
					grayBuffered = imgBasicPrcs.convertGrayScale();
					copyOrginalImage.saveGrayScale(grayBuffered);

					grayPicLabel = new JLabel(new ImageIcon(grayBuffered));
					grayPicLabel.setBounds(25, 50, 500, 500);
					remove(picLabel);
					add(grayPicLabel);
					repaint();

				}

				else if (e.getSource() == gausButton) {
					copyOrginalImage = new Image("grayImage.jpg");

					imgBasicPrcs = new ImageBasicProces(
							copyOrginalImage.getOriginalBufferedImage(),
							copyOrginalImage.getWidth(),
							copyOrginalImage.getHeight());
					copyOrginalImage.saveGrayScaleIntArray(imgBasicPrcs
							.Gaus(copyOrginalImage.getPixelData()));

					// grayPicLabel = new JLabel(new ImageIcon(grayBuffered));
					// grayPicLabel.setBounds(25, 50, 500, 500);
					// remove(picLabel);
					// add(grayPicLabel);
					// repaint();

				} else if (e.getSource() == sobelButton) {

				} else if (e.getSource() == beforeButton) {
					context.ThresholdBack(1);

				}

			}
		};

		gausButton.addActionListener(listener);
		loadImageButton.addActionListener(listener);
		convertGrayButton.addActionListener(listener);
		sobelButton.addActionListener(listener);
		beforeButton.addActionListener(listener);

	}

}
