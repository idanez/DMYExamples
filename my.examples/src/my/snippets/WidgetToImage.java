package my.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import my.components.PercentageBar;

public class WidgetToImage {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Widget");
		shell.setBounds(10, 10, 300, 300);

		final Table table = new Table(shell, SWT.MULTI);
		table.setLinesVisible(true);
		table.setBounds(10, 10, 100, 100);
		for (int i = 0; i < 9; i++) {
			new TableItem(table, SWT.NONE).setText("item" + i);
		}

		final PercentageBar bar = new PercentageBar(shell, SWT.NONE);
		bar.setBounds(10, 120, 100, 20);
		bar.updateBar(50, false);

		Button button = new Button(shell, SWT.PUSH);
		button.setText("Capture");
		button.setBounds(10, 200, 50, 20);
		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				Point tableSize = bar.getSize();
				GC gc = new GC(bar);
				final Image image = new Image(display, tableSize.x, tableSize.y);
				gc.copyArea(image, 0, 0);
				gc.dispose();

				Shell popup = new Shell(shell);
				popup.setText("Image");
				popup.setBounds(50, 150, 200, 200);
				popup.addListener(SWT.Close, new Listener() {
					@Override
					public void handleEvent(final Event e) {
						image.dispose();
					}
				});

				Canvas canvas = new Canvas(popup, SWT.NONE);
				canvas.setBounds(50, 50, 300, 300);
				canvas.addPaintListener(new PaintListener() {
					@Override
					public void paintControl(final PaintEvent e) {
						e.gc.drawImage(image, 0, 0);
					}
				});
				popup.open();
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
