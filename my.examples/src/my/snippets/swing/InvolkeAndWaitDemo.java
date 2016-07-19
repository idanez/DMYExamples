package my.snippets.swing;

import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class InvolkeAndWaitDemo {

	private static void print(String msg) {
		String name = Thread.currentThread().getName();
		System.out.println(name + ": " + msg);
	}

	public static void main(String[] args) {
		final JLabel label = new JLabel("--------");

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(label);

		JFrame f = new JFrame("InvokeAndWaitDemo");
		f.setContentPane(panel);
		f.setSize(300, 100);
		f.setVisible(true);

		try {
			print("sleeping for 3 seconds");
			Thread.sleep(3000);

			print("creating code block for event thread");
			Runnable setTextRun = new Runnable() {
				public void run() {
					print("about to do setText()");
					label.setText("New text!");
				}
			};
			print("about to invokeAndWait()");
			SwingUtilities.invokeAndWait(setTextRun);
			print("back from invokeAndWait()");
		} catch (InterruptedException ix) {
			print("interrupted while waiting on invokeAndWait()");
		} catch (InvocationTargetException x) {
			print("exception thrown from run()");
		}
	}
}


