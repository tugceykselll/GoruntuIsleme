package Main;

import java.awt.BorderLayout;
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

@SuppressWarnings("serial")
public class ThresholdPanel extends JPanel {
	BufferedImage loadImage;
	BufferedImage convertImage;
	BufferedImage thresholdImage;

	public ThresholdPanel(InnerClass context) {
		// TODO Auto-generated constructor stub

		final JButton loadImageButton = new JButton("Resim Yükle");
		final JButton convertGrayButton = new JButton("Gray Scale Yap ");
		final JButton thresholdButton = new JButton("Otsu Threshold Uygula");

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
			thresholdImage = ImageIO.read(new File("17_histogram.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadImageButton.setIcon(new ImageIcon(loadImage));
		loadImageButton.setVisible(true);
		loadImageButton.setBounds(10, 10, 80, 80);

		convertGrayButton.setIcon(new ImageIcon(convertImage));
		convertGrayButton.setVisible(true);
		convertGrayButton.setBounds(100, 10, 80, 80);

		thresholdButton.setIcon(new ImageIcon(thresholdImage));
		thresholdButton.setVisible(true);
		thresholdButton.setBounds(200, 10, 80, 80);

		add(loadImageButton, BorderLayout.NORTH);
		add(convertGrayButton, BorderLayout.NORTH);
		add(thresholdButton, BorderLayout.NORTH);

		ActionListener actionListener = new ActionListener() {
			Image orginalImage = null;
			Image copyOrginalImage = null;
			JLabel grayPicLabel;
			JLabel picLabel = null;
			ImageBasicProces imgBasicPrcs = null;
			BufferedImage grayBuffered = null;
			BufferedImage thresholdPicBuffered = null;

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
				}

				else if (e.getSource() == convertGrayButton) {

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

				else if (e.getSource() == thresholdButton) {

					copyOrginalImage
							.saveThreshold(imgBasicPrcs
									.OtsuThreshold(imgBasicPrcs
											.Histogram(grayBuffered)));
					try {
						thresholdPicBuffered = ImageIO.read(new File(
								"Threshold.jpg"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JLabel picThresholdLabel = new JLabel(new ImageIcon(
							thresholdPicBuffered));
					picThresholdLabel.setBounds(500, 50, 500, 500);
					add(picThresholdLabel);
					repaint();

				}
			}
		};

		thresholdButton.addActionListener(actionListener);
		convertGrayButton.addActionListener(actionListener);
		loadImageButton.addActionListener(actionListener);
	}
}
