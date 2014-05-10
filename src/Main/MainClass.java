package Main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass {

	public static void main(String args[]) {
		new InnerClass();

	}

}

class InnerClass {

	final JPanel mainPanel;
	ThresholdPanel thresholdObject;
	SobelPanel sobelObject;

	public InnerClass() {
		final InnerClass context = this;

		final JFrame jframe = new JFrame("Görüntü Ýþleme");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setSize(1020, 600);
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 2));

		final JButton butonThreshold = new JButton("Threshold");
		mainPanel.add(butonThreshold);

		final JButton butonSobel = new JButton("Sobel");

		mainPanel.add(butonSobel);
		JButton button2 = new JButton("Button 2");

		mainPanel.add(button2);
		jframe.add(mainPanel);

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == butonThreshold) {
					mainPanel.setVisible(false);
					thresholdObject = new ThresholdPanel(context);
					jframe.add(thresholdObject);
				}

				else if (e.getSource() == butonSobel) {
					mainPanel.setVisible(false);
					sobelObject = new SobelPanel(context);
					jframe.add(sobelObject);
				}
			}
		};

		butonThreshold.addActionListener(listener);
		butonSobel.addActionListener(listener);

		jframe.setVisible(true);

	}

	public void ThresholdBack(int i) {
		if (i == 0) {
			thresholdObject.setVisible(false);

			mainPanel.setVisible(true);
		} else if (i == 1) {
			sobelObject.setVisible(false);

			mainPanel.setVisible(true);
		}
	}
}