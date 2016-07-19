package my.snippets.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SwingSplashScreenWithProgressBar {

	public static void main(String args[]) throws Exception{
						
		SplashScreen splash = SplashScreen.getSplashScreen();
		Graphics2D g = (Graphics2D) splash.createGraphics();
		Dimension dim = splash.getSize();
		for (int i = 0; i < 100; i++) {
			g.setColor(Color.RED);
			g.fillRect(50, 50, dim.width - 100, dim.height - 100);
			splash.update();
			try {
				Thread.sleep(250);
			} catch (InterruptedException ignored) {
			}
		}
		JFrame frame = new JFrame("Splash Me2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("Hello, Splash", JLabel.CENTER);
		frame.add(label, BorderLayout.CENTER);
		frame.setSize(300, 95);
		frame.setVisible(true);
	}
	
	
}

