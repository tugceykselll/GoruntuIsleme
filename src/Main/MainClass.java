package Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainClass {

	public static void main(String args[]) {
		new InnerClass();

	}

}

class InnerClass {
	public InnerClass() {
		final InnerClass context = this;

		final JFrame jframe = new JFrame("Görüntü Ýþleme");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setSize(1020, 600);

		final JButton butonThreshold = new JButton("Threshold");
		jframe.add(butonThreshold, BorderLayout.NORTH);

		butonThreshold.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				butonThreshold.setVisible(false);
				ThresholdPanel thresholdObject = new ThresholdPanel(context);
				jframe.add(thresholdObject);

			}
		});
		jframe.setVisible(true);

	}

}