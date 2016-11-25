package my.smallProjects;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

public class SVGApplication {

	public static void main(final String[] args) {
		// Create a new JFrame.
		JFrame f = new JFrame("Batik");
		SVGApplication app = new SVGApplication(f);

		// Add components to the frame.
		f.getContentPane().add(app.createComponents());

		// Display the frame.
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});
		f.setSize(400, 400);
		f.setVisible(true);
	}

	// The frame.
	protected JFrame frame;

	// The "Load" button, which displays up a file chooser upon clicking.
	protected JButton button = new JButton("Load...");

	// The status label.
	protected JLabel label = new JLabel();

	// The SVG canvas.
	protected JSVGCanvas svgCanvas = new JSVGCanvas();

	public SVGApplication(final JFrame f) {
		frame = f;
	}

	public JComponent createComponents() {
		// Create a panel and add the button, status label and the SVG canvas.
		final JPanel panel = new JPanel(new BorderLayout());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(button);
		p.add(label);

		panel.add("North", p);
		panel.add("Center", svgCanvas);

		// Set the button action.
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				JFileChooser fc = new JFileChooser(".");
				int choice = fc.showOpenDialog(panel);
				if (choice == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					try {
						svgCanvas.setURI(f.toURL().toString());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		// Set the JSVGCanvas listeners.
		svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			public void documentLoadingStarted(final SVGDocumentLoaderEvent e) {
				label.setText("Document Loading...");
			}

			public void documentLoadingCompleted(final SVGDocumentLoaderEvent e) {
				label.setText("Document Loaded.");
			}
		});

		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
			public void gvtBuildStarted(final GVTTreeBuilderEvent e) {
				label.setText("Build Started...");
			}

			public void gvtBuildCompleted(final GVTTreeBuilderEvent e) {
				label.setText("Build Done.");
				frame.pack();
			}
		});

		svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			public void gvtRenderingPrepare(final GVTTreeRendererEvent e) {
				label.setText("Rendering Started...");
			}

			public void gvtRenderingCompleted(final GVTTreeRendererEvent e) {
				label.setText("");
			}
		});

		return panel;
	}
}