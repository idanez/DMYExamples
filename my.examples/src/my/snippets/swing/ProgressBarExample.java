package my.snippets.swing;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class ProgressBarExample {

	public static void main(String args[]) throws InterruptedException {
	    JFrame f = new JFrame("JProgressBar Sample");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container content = f.getContentPane();
	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setValue(25);
	    progressBar.setStringPainted(true);
	    Border border = BorderFactory.createTitledBorder("Reading...");
	    progressBar.setBorder(border);
	    content.add(progressBar, BorderLayout.NORTH);
	    f.setSize(300, 100);
	    f.setVisible(true);
	    
	    Thread.sleep(2000);
	    
	    progressBar.setValue(50);
	    border = BorderFactory.createTitledBorder("Still reading...");
	    progressBar.setBorder(border);
	    
	    Thread.sleep(2000);
	    
	    progressBar.setValue(60);
	    border = BorderFactory.createTitledBorder("reading again...");
	    progressBar.setBorder(border);
	    
	    Thread.sleep(2000);
	    
	    int count = 61;
	    
	    while(count < 100) {
	    	 Thread.sleep(100);
	    	
	    	 progressBar.setValue(count);
	 	     border = BorderFactory.createTitledBorder("Quase la: "+count+" %");
	 	     progressBar.setBorder(border);
	 	     count++;
	    }
	    
	    progressBar.setValue(100);
	    border = BorderFactory.createTitledBorder("Acabei!!");
	    progressBar.setBorder(border);
	     	    
	    	    
	  }
	
}
