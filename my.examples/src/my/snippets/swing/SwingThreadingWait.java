package my.snippets.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * @author Romain Guy
 */
public class SwingThreadingWait extends JFrame implements ActionListener {
	private JLabel counter;

	private long start = 0;

	public SwingThreadingWait() {
		super("Invoke & Wait");

		JButton freezer = new JButton("Open File");
		freezer.addActionListener(this);

		counter = new JLabel("Time elapsed: 0s");

		add(freezer, BorderLayout.CENTER);
		add(counter, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		start = System.currentTimeMillis();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}

					final int elapsed = (int) ((System.currentTimeMillis() - start) / 1000);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							counter.setText("Time elapsed: " + elapsed + "s");
						}
					});

					if (elapsed == 4) {
						try {
							final int[] answer = new int[1];
							SwingUtilities.invokeAndWait(new Runnable() {
								public void run() {
									answer[0] = JOptionPane.showConfirmDialog(SwingThreadingWait.this,
											"Abort long operation?", "Abort?", JOptionPane.YES_NO_OPTION);
								}
							});
							if (answer[0] == JOptionPane.YES_OPTION) {
								return;
							}
						} catch (InterruptedException e1) {
						} catch (InvocationTargetException e1) {
						}
					}
				}
			}
		}).start();
	}

	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SwingThreadingWait edt = new SwingThreadingWait();
				edt.setVisible(true);
			}
		});
	}
}



