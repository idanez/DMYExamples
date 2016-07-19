package my.snippets.swing;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InvokeLaterDemo {




	private static void print(String msg) {
		String name = Thread.currentThread().getName();
		System.out.println(name + ": " + msg);
	}

	public static void main(String[] args) {
		final JLabel label = new JLabel("--------");

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(label);

		JFrame f = new JFrame();
		f.setContentPane(panel);
		f.setSize(300, 100);
		f.setVisible(true);

		try {
			print("sleeping for 3 seconds");
			Thread.sleep(3000);
		} catch (InterruptedException ix) {
			print("interrupted while sleeping");
		}

		print("creating code block for event thread");
		Runnable setTextRun = new Runnable() {
			public void run() {
				try {
					Thread.sleep(500);
					print("about to do setText()");
					label.setText("New text!");
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		};

		print("call invokeLater()");
		SwingUtilities.invokeLater(setTextRun);
		print("return from invokeLater()");
	}


}
