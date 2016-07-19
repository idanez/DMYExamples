package my.snippets.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreenSwingInTheCenter extends JWindow {

	/*
	Java Swing, 2nd Edition
	By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
	ISBN: 0-596-00408-7
	Publisher: O'Reilly 
	*/
	// SplashScreen.java
	//A simple application to show a title screen in the center of the screen
	//for the amount of time given in the constructor. This class includes
	//a sample main() method to test the splash screen, but it's meant for use
	//with other applications.
	//
	
	  private int duration;

	  public SplashScreenSwingInTheCenter(int d) {
	    duration = d;
	  }

	  // A simple little method to show a title screen in the center
	  // of the screen for the amount of time given in the constructor
	  public void showSplash() {
		  
	    JPanel content = (JPanel) getContentPane();
	    
	    content.setBackground(Color.white);

	    // Set the window's bounds, centering the window
	    int width = 450;
	    int height = 115;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width - width) / 2;
	    int y = (screen.height - height) / 2;
	    setBounds(x, y, width, height);

	    // Build the splash screen
	    JLabel label = new JLabel(new ImageIcon("images/1.jpg"));
	    JLabel copyrt = new JLabel("Copyright 2002, O'Reilly & Associates",
	        JLabel.CENTER);
	    JProgressBar progress = new JProgressBar(SwingConstants.HORIZONTAL);
	    progress.setIndeterminate(true);
	    copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
	    
	    content.add(label, BorderLayout.CENTER);
	    content.add(copyrt, BorderLayout.SOUTH);
	    content.add(progress, BorderLayout.SOUTH);
	    
	    Color oraRed = new Color(156, 20, 20, 255);
	    content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

	    // Display it
	    setVisible(true);

	    // Wait a little while, maybe while loading resources
	    try {
	      Thread.sleep(duration);
	    } catch (Exception e) {
	    }

	    setVisible(false);
	  }

	  public void showSplashAndExit() {
	    showSplash();
	    System.exit(0);
	  }

	  public static void main(String[] args) {
	    // Throw a nice little title page up on the screen first
		  SplashScreenSwingInTheCenter splash = new SplashScreenSwingInTheCenter(5000);
	    // Normally, we'd call splash.showSplash() and get on with the program.
	    // But, since this is only a test...
	    splash.showSplashAndExit();
	  }
	
	
}
